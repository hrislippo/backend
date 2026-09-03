package lippo.hris.system.ocrengine.service;

import lippo.hris.system.ocrengine.response.KTPData;
import lippo.hris.system.ocrengine.response.OCRItem;
import lippo.hris.system.ocrengine.response.OCRResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KtpService {

    @Autowired
    OcrService ocrService;

    public KTPData processKTP(MultipartFile file, String url) throws Exception {

        // Initial preprocessing
        ByteArrayResource resource = ocrService.preprocessingOCR(ocrService.toByteArrayResource(file));
        OCRResponse result = ocrService.performOCR(resource);
        return parseKTP(result.getResults(), url);
    }

    private KTPData parseKTP(List<OCRItem> items, String url) throws IOException {
        KTPData ktpData = new KTPData();
//        ktpData.setNik(parseNIK(items));
//        ktpData.setNama(parseNama(items));
        ktpData.setAgama(parseAgama(items));
//        ktpData.setTempatLahir(parseTempatLahir(items));
//        ktpData.setTanggalLahir(parseTanggalLahir(items));
//        ktpData.setNikPath(url);
        return ktpData;
    }

    private String parseNIK(List<OCRItem> items) {
        Pattern nikPattern = Pattern.compile("\\d{16}");

        for (OCRItem item : items) {
            String text = item.getText();
            Matcher matcher = nikPattern.matcher(text);

            if (matcher.find()) {
                return matcher.group();
            }
        }

        return null;
    }

    private String parseNama(List<OCRItem> items) {

        Pattern DATE_PATTERN = Pattern.compile(
                "(\\d{2})[-/.](\\d{2})[-/.](\\d{4})"
        );

        OCRItem namaLabel = findLabel(items, "NAMA");

        if (namaLabel == null) {
            return null;
        }

        List<Integer> labelBox = namaLabel.getBox();

        if (labelBox == null || labelBox.size() < 4) {
            return null;
        }

        int labelY1 = labelBox.get(1);
        int labelY2 = labelBox.get(3);
        int labelX2 = labelBox.get(2);

        double labelCenterY = (labelY1 + labelY2) / 2.0;
        int labelHeight = labelY2 - labelY1;

        OCRItem firstCandidate = null;
        double bestScore = Double.MAX_VALUE;

        for (OCRItem candidate : items) {

            if (candidate == namaLabel) {
                continue;
            }

            if (candidate.getText() == null ||
                    candidate.getText().isBlank()) {
                continue;
            }

            String candidateText = candidate.getText().trim();

            /*
             * IMPORTANT:
             * Don't select OCR text containing a date.
             *
             * Example:
             * "JAKARTA,24-07-2004"
             */
            if (DATE_PATTERN.matcher(candidateText).find()) {
                continue;
            }

            /*
             * Don't select another KTP label.
             */
            if (isKtpLabel(candidateText)) {
                continue;
            }

            List<Integer> box = candidate.getBox();

            if (box == null || box.size() < 4) {
                continue;
            }

            int candidateX1 = box.get(0);
            int candidateY1 = box.get(1);
            int candidateY2 = box.get(3);

            double candidateCenterY =
                    (candidateY1 + candidateY2) / 2.0;

            /*
             * Name should be approximately
             * on the same row as "NAMA".
             */
            double verticalDistance =
                    Math.abs(candidateCenterY - labelCenterY);

            /*
             * Candidate should be to the RIGHT of NAMA.
             */
            if (candidateX1 < labelX2) {
                continue;
            }

            /*
             * Don't select something too far vertically.
             */
            if (verticalDistance > labelHeight * 1.5) {
                continue;
            }

            double horizontalDistance =
                    candidateX1 - labelX2;

            /*
             * Smaller score = better candidate.
             */
            double score =
                    horizontalDistance
                            + verticalDistance * 3;

            if (score < bestScore) {
                bestScore = score;
                firstCandidate = candidate;
            }
        }

        if (firstCandidate == null) {
            return null;
        }

        /*
         * Find the next KTP label below NAMA.
         */
        double nextLabelY = Double.MAX_VALUE;

        for (OCRItem candidate : items) {

            if (candidate == namaLabel) {
                continue;
            }

            if (candidate.getText() == null ||
                    candidate.getText().isBlank()) {
                continue;
            }

            String candidateText = candidate.getText().trim();

            /*
             * Don't process date-containing text.
             */
            if (DATE_PATTERN.matcher(candidateText).find()) {
                continue;
            }

            /*
             * Only interested in KTP labels.
             */
            if (!isKtpLabel(candidateText)) {
                continue;
            }

            List<Integer> box = candidate.getBox();

            if (box == null || box.size() < 4) {
                continue;
            }

            int candidateY1 = box.get(1);
            int candidateY2 = box.get(3);

            double candidateCenterY =
                    (candidateY1 + candidateY2) / 2.0;

            /*
             * Only labels BELOW NAMA.
             */
            if (candidateCenterY <= labelCenterY) {
                continue;
            }

            /*
             * Find the closest label below NAMA.
             */
            if (candidateCenterY < nextLabelY) {
                nextLabelY = candidateCenterY;
            }
        }

        List<OCRItem> nameLines = new ArrayList<>();

        nameLines.add(firstCandidate);

        List<Integer> firstBox = firstCandidate.getBox();

        int firstX1 = firstBox.get(0);
        int firstY1 = firstBox.get(1);
        int firstY2 = firstBox.get(3);

        int firstHeight = firstY2 - firstY1;

        double previousCenterY =
                (firstY1 + firstY2) / 2.0;

        /*
         * Find possible continuation lines.
         */
        for (OCRItem candidate : items) {

            if (candidate == namaLabel ||
                    candidate == firstCandidate) {
                continue;
            }

            if (candidate.getText() == null ||
                    candidate.getText().isBlank()) {
                continue;
            }

            String candidateText = candidate.getText().trim();

            /*
             * IMPORTANT:
             *
             * Never include text containing a date
             * in the name.
             *
             * Example:
             * "JAKARTA,24-07-2004"
             */
            if (DATE_PATTERN.matcher(candidateText).find()) {
                continue;
            }

            /*
             * Don't include another KTP label.
             */
            if (isKtpLabel(candidateText)) {
                continue;
            }

            List<Integer> box = candidate.getBox();

            if (box == null || box.size() < 4) {
                continue;
            }

            int candidateX1 = box.get(0);
            int candidateY1 = box.get(1);
            int candidateY2 = box.get(3);

            double candidateCenterY =
                    (candidateY1 + candidateY2) / 2.0;

            /*
             * Must be BELOW the previous name line.
             */
            if (candidateCenterY <= previousCenterY) {
                continue;
            }

            /*
             * Never cross the next KTP label.
             */
            if (candidateCenterY >= nextLabelY) {
                continue;
            }

            /*
             * Don't allow the next line to be
             * too far away.
             */
            double verticalDistance =
                    candidateCenterY - previousCenterY;

            if (verticalDistance > firstHeight * 2.0) {
                continue;
            }

            /*
             * Continuation line should start approximately
             * at the same X position as the first name line.
             */
            int xTolerance =
                    Math.max(50, firstHeight * 3);

            if (Math.abs(candidateX1 - firstX1) > xTolerance) {
                continue;
            }

            /*
             * This looks like a continuation of the name.
             */
            nameLines.add(candidate);

            previousCenterY = candidateCenterY;
        }

        /*
         * Sort name lines from top to bottom.
         */
        nameLines.sort(
                Comparator.comparingDouble(item -> {

                    List<Integer> box = item.getBox();

                    return (box.get(1) + box.get(3)) / 2.0;
                })
        );

        StringBuilder name = new StringBuilder();

        for (OCRItem line : nameLines) {

            String text = line.getText();

            if (text == null || text.isBlank()) {
                continue;
            }

            /*
             * Extra safety:
             * Don't append date-containing text.
             */
            if (DATE_PATTERN.matcher(text).find()) {
                continue;
            }

            if (!name.isEmpty()) {
                name.append(" ");
            }

            name.append(text.trim());
        }

        return name.isEmpty()
                ? null
                : normalizeLabel(name.toString());
    }

    private String parseAgama(List<OCRItem> items) {

        OCRItem namaLabel = findLabel(items, "AGAMA");

        if (namaLabel == null) {
            return null;
        }

        List<Integer> labelBox = namaLabel.getBox();

        int labelX1 = labelBox.get(0);
        int labelY1 = labelBox.get(1);
        int labelX2 = labelBox.get(2);
        int labelY2 = labelBox.get(3);

        double labelCenterY = (labelY1 + labelY2) / 2.0;

        OCRItem bestCandidate = null;
        double bestScore = Double.MAX_VALUE;

        for (OCRItem candidate : items) {

            if (candidate == namaLabel) {
                continue;
            }

            if (candidate.getText() == null ||
                    candidate.getText().isBlank()) {
                continue;
            }

            // Don't select another KTP label
            if (isKtpLabel(candidate.getText())) {
                continue;
            }

            List<Integer> box = candidate.getBox();

            if (box == null || box.size() < 4) {
                continue;
            }

            int candidateX1 = box.get(0);
            int candidateY1 = box.get(1);
            int candidateX2 = box.get(2);
            int candidateY2 = box.get(3);

            double candidateCenterY =
                    (candidateY1 + candidateY2) / 2.0;

            /*
             * Candidate should be to the RIGHT of "Nama".
             */
            if (candidateX1 < labelX2) {
                continue;
            }

            double verticalDistance =
                    Math.abs(candidateCenterY - labelCenterY);

            /*
             * Candidate should be approximately
             * on the same horizontal line.
             */
            int labelHeight = labelY2 - labelY1;

            if (verticalDistance > labelHeight * 1.5) {
                continue;
            }

            double horizontalDistance =
                    candidateX1 - labelX2;

            /*
             * Smaller score = better candidate.
             *
             * Vertical distance is weighted more heavily
             * because the name should be on the same row.
             */
            double score =
                    horizontalDistance
                            + verticalDistance * 3;

            if (score < bestScore) {
                bestScore = score;
                bestCandidate = candidate;
            }
        }

        return bestCandidate != null
                ? normalizeLabel(bestCandidate.getText().trim())
                : null;
    }

    private String parseTempatLahir(List<OCRItem> items) {

        Pattern DATE_PATTERN = Pattern.compile(
                "(\\d{2})[-/.](\\d{2})[-/.](\\d{4})"
        );

        Pattern LABEL_PATTERN = Pattern.compile(
                "TG[LI]\\s*LAHIR\\s*:?",
                Pattern.CASE_INSENSITIVE
        );

        Pattern UPPERCASE_PLACE_PATTERN = Pattern.compile(
                "^[A-Z][A-Z\\s.,'-]*$"
        );

        for (OCRItem item : items) {

            if (item.getText() == null || item.getText().isBlank()) {
                continue;
            }

            String text = item.getText().trim();

            Matcher labelMatcher = LABEL_PATTERN.matcher(text);

            if (!labelMatcher.find()) {
                continue;
            }

            /*
             * Case 1:
             *
             * TEMPAT/TGL LAHIR JAKARTA,24-07-2004
             */
            Matcher dateMatcher = DATE_PATTERN.matcher(text);

            if (dateMatcher.find()) {

                String tempat = text.substring(
                        labelMatcher.end(),
                        dateMatcher.start()
                );

                tempat = tempat
                        .replaceAll("^[\\s,:./-]+", "")
                        .replaceAll("[\\s,:./-]+$", "")
                        .trim();

                if (UPPERCASE_PLACE_PATTERN.matcher(tempat).matches()) {
                    return normalizeLabel(tempat);
                }
            }

            /*
             * Case 2:
             *
             * TEMPAT/TGL LAHIR
             *
             * JAKARTA,24-07-2004
             */
            List<Integer> labelBox = item.getBox();

            if (labelBox == null || labelBox.size() < 4) {
                continue;
            }

            int labelX2 = labelBox.get(2);

            int labelY1 = labelBox.get(1);
            int labelY2 = labelBox.get(3);

            double labelCenterY =
                    (labelY1 + labelY2) / 2.0;

            OCRItem bestCandidate = null;
            Matcher bestDateMatcher = null;

            double bestScore = Double.MAX_VALUE;

            for (OCRItem candidate : items) {

                if (candidate == item) {
                    continue;
                }

                if (candidate.getText() == null ||
                        candidate.getText().isBlank()) {
                    continue;
                }

                String candidateText =
                        candidate.getText().trim();

                /*
                 * Candidate MUST contain a date.
                 */
                Matcher candidateDateMatcher =
                        DATE_PATTERN.matcher(candidateText);

                if (!candidateDateMatcher.find()) {
                    continue;
                }

                /*
                 * Don't select another KTP label.
                 */
                if (isKtpLabel(candidateText)) {
                    continue;
                }

                /*
                 * Extract the text before the date.
                 */
                String tempat = candidateText.substring(
                        0,
                        candidateDateMatcher.start()
                );

                tempat = tempat
                        .replaceAll("^[\\s,:./-]+", "")
                        .replaceAll("[\\s,:./-]+$", "")
                        .trim();

                /*
                 * IMPORTANT:
                 * Only accept uppercase city/place names.
                 */
                if (!UPPERCASE_PLACE_PATTERN
                        .matcher(tempat)
                        .matches()) {
                    continue;
                }

                List<Integer> box = candidate.getBox();

                if (box == null || box.size() < 4) {
                    continue;
                }

                int candidateX1 = box.get(0);
                int candidateY1 = box.get(1);
                int candidateY2 = box.get(3);

                double candidateCenterY =
                        (candidateY1 + candidateY2) / 2.0;

                /*
                 * Candidate should be on approximately
                 * the same horizontal line.
                 */
                double verticalDistance =
                        Math.abs(
                                candidateCenterY
                                        - labelCenterY
                        );

                double horizontalDistance =
                        Math.max(
                                0,
                                candidateX1 - labelX2
                        );

                /*
                 * Smaller score = better candidate.
                 */
                double score =
                        horizontalDistance
                                + verticalDistance * 3;

                if (score < bestScore) {

                    bestScore = score;
                    bestCandidate = candidate;
                    bestDateMatcher = candidateDateMatcher;
                }
            }

            if (bestCandidate != null &&
                    bestDateMatcher != null) {

                String tempat =
                        bestCandidate.getText()
                                .substring(
                                        0,
                                        bestDateMatcher.start()
                                );

                if (UPPERCASE_PLACE_PATTERN
                        .matcher(tempat)
                        .matches()) {

                    return normalizeLabel(tempat);
                }
            }
        }

        return null;
    }

    private String parseTanggalLahir(List<OCRItem> items) {

        Pattern DATE_PATTERN = Pattern.compile(
                "(\\d{2})[-/.](\\d{2})[-/.](\\d{4})"
        );

        Pattern LABEL_PATTERN = Pattern.compile(
                "TG[LI]\\s*LAHIR\\s*:?",
                Pattern.CASE_INSENSITIVE
        );

        for (OCRItem item : items) {

            if (item.getText() == null || item.getText().isBlank()) {
                continue;
            }

            String text = item.getText().trim();

            Matcher labelMatcher = LABEL_PATTERN.matcher(text);

            if (!labelMatcher.find()) {
                continue;
            }

            /*
             * Case 1:
             * Label and date are in the same OCR item.
             *
             * Example:
             * "TEMPAT/TGL LAHIR JAKARTA,24-07-2004"
             */
            Matcher dateMatcher = DATE_PATTERN.matcher(text);

            if (dateMatcher.find()) {
                return dateMatcher.group(1) + "-"
                        + dateMatcher.group(2) + "-"
                        + dateMatcher.group(3);
            }

            List<Integer> labelBox = item.getBox();

            if (labelBox == null || labelBox.size() < 4) {
                continue;
            }

            int labelX1 = labelBox.get(0);
            int labelY1 = labelBox.get(1);
            int labelX2 = labelBox.get(2);
            int labelY2 = labelBox.get(3);

            double labelCenterY = (labelY1 + labelY2) / 2.0;
            int labelHeight = labelY2 - labelY1;

            OCRItem bestCandidate = null;
            Matcher bestDateMatcher = null;
            double bestScore = Double.MAX_VALUE;

            for (OCRItem candidate : items) {

                if (candidate == item) {
                    continue;
                }

                if (candidate.getText() == null ||
                        candidate.getText().isBlank()) {
                    continue;
                }

                String candidateText = candidate.getText().trim();

                /*
                 * IMPORTANT:
                 * Only consider candidates that actually contain
                 * a date pattern.
                 */
                Matcher candidateDateMatcher =
                        DATE_PATTERN.matcher(candidateText);

                if (!candidateDateMatcher.find()) {
                    continue;
                }

                /*
                 * Don't select another KTP label.
                 */
                if (isKtpLabel(candidateText)) {
                    continue;
                }

                List<Integer> box = candidate.getBox();

                if (box == null || box.size() < 4) {
                    continue;
                }

                int candidateX1 = box.get(0);
                int candidateY1 = box.get(1);
                int candidateX2 = box.get(2);
                int candidateY2 = box.get(3);

                double candidateCenterY =
                        (candidateY1 + candidateY2) / 2.0;

                double verticalDistance =
                        Math.abs(candidateCenterY - labelCenterY);

                double horizontalDistance =
                        candidateX1 - labelX2;

                /*
                 * Smaller score = better candidate.
                 */
                double score =
                        horizontalDistance
                                + verticalDistance * 3;

                if (score < bestScore) {
                    bestScore = score;
                    bestCandidate = candidate;
                    bestDateMatcher = candidateDateMatcher;
                }
            }

            /*
             * Return the date from the BEST candidate
             * that contains a valid date pattern.
             */
            if (bestCandidate != null && bestDateMatcher != null) {

                return bestDateMatcher.group(1) + "-"
                        + bestDateMatcher.group(2) + "-"
                        + bestDateMatcher.group(3);
            }
        }

        return null;
    }

    private OCRItem findLabel(List<OCRItem> items, String targetLabel) {
        for (OCRItem item : items) {
            if (item.getText() == null) {
                continue;
            }

            String normalized = normalizeLabel(item.getText());

            if (normalized.equals(normalizeLabel(targetLabel))) {
                return item;
            }
        }
        return null;
    }

    private String normalizeLabel(String text) {
        if (text == null) {
            return "";
        }

        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private boolean isKtpLabel(String text) {

        String normalized = normalizeLabel(text);

        return normalized.equals("NIK")
                || normalized.equals("NAMA")
                || normalized.equals("TEMPATTGLLAHIR")
                || normalized.equals("TEMPATTGILAHIR")
                || normalized.equals("JENISKELAMIN")
                || normalized.equals("ALAMAT")
                || normalized.equals("RTRW")
                || normalized.equals("KELDESA")
                || normalized.equals("KECAMATAN")
                || normalized.equals("AGAMA")
                || normalized.equals("STATUSPERKAWINAN")
                || normalized.equals("PEKERJAAN")
                || normalized.equals("KEWARGANEGARAAN")
                || normalized.equals("BERLAKUSEUMURHIDUP");
    }
}
