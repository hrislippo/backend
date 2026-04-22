package lippo.hris.system.ocrengine.service;

import lippo.hris.system.ocrengine.response.Education;
import lippo.hris.system.ocrengine.response.Experience;
import lippo.hris.system.ocrengine.response.Language;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LinkedinExtractService {

    public String getLinkedInAddress(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() < 200)
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        StringBuilder result = new StringBuilder();
        Boolean flag = false;

        for (Integer y : sortedY) {
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            StringBuilder word = new StringBuilder();
            for(TextPosition position : line){
                word.append(position.getUnicode());
            }

            if(word.toString().contains("(Mobile)") ||
                word.toString().contains("@") ||
                word.toString().contains("linkedin")){
                return result.isEmpty() ? null : result.toString().trim();
            }

            if(flag){
                result.append(word).append(" ");
            }

            if(word.toString().contains("Contact") || word.toString().contains("Hubungi")){
                flag = true;
            }
        }
        return result.isEmpty() ? null : result.toString().trim();
    }

    public String getLinkedInMobilePhone(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() < 200)
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();

        for (Integer y : sortedY) {
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            StringBuilder word = new StringBuilder();
            for(TextPosition position : line){
                word.append(position.getUnicode());
            }

            if(word.toString().contains("(Mobile)")){
                return word.isEmpty() ? null : word.toString().replace("(Mobile)", "")
                                                                .trim();
            }
        }
        return null;
    }

    public String getLinkedInLink(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() < 200)
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        StringBuilder result = new StringBuilder();

        for (Integer y : sortedY) {
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            StringBuilder word = new StringBuilder();
            for(TextPosition position : line){
                word.append(position.getUnicode());
            }

            if(word.toString().contains("linkedin") || word.toString().contains("LinkedIn")){
                result.append(word.toString().replace("(LinkedIn)", ""));
            }
        }
        return result.isEmpty() ? null : result.toString().trim();
    }

    public String getLinkedInEmail(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() < 200)
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        StringBuilder result = new StringBuilder();
        Boolean flag = false;

        for (Integer y : sortedY) {
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            StringBuilder word = new StringBuilder();
            for(TextPosition position : line){
                word.append(position.getUnicode());
            }

            if(flag){
                if(word.toString().contains("linkedin")){
                    return result.isEmpty() ? null : result.toString().trim();
                }
                result.append(word);
            }

            if(word.toString().contains("@")){
                result.append(word);
                flag = true;
            }
        }
        return result.isEmpty() ? null : result.toString().trim();
    }

    public List<String> getLinkedInTopSkills(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() < 200)
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        List<String> skills = new ArrayList<>();
        Boolean flag = false;

        for (Integer y : sortedY) {
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            StringBuilder word = new StringBuilder();
            for(TextPosition position : line){
                word.append(position.getUnicode());
                if(flag && position.getFontSize() == 13){
                    return skills;
                }
            }

            if(flag && !word.toString().equals("\u00A0")){
                skills.add(word.toString());
            }

            if(word.toString().contains("Top Skills") || word.toString().contains("Keahlian Teratas")){
                flag = true;
            }
        }
        return skills;
    }

    public List<Language> getLinkedInLanguages(List<List<TextPosition>> positionList){
        List<Language> result = new ArrayList<>();
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() < 200)
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        Boolean flag = false;
        String languageProficiency = "";

        for (Integer y : sortedY) {
            Integer nextY = sortedY.indexOf(y) + 1 >= sortedY.size() ? null : sortedY.get(sortedY.indexOf(y) + 1);
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            StringBuilder word = new StringBuilder();
            for(TextPosition position : line){
                word.append(position.getUnicode());
                if(flag && position.getFontSize() == 13){
                    return result;
                }
            }

            if(flag && !word.toString().equals("\u00A0")){
                languageProficiency = languageProficiency + " " + word;
                if(nextY != null && nextY - y > 15){
                    Language language = new Language();
                    language.setLanguageName(languageProficiency.substring(0, languageProficiency.indexOf('(')).trim());
                    language.setProficiency(languageProficiency.substring(languageProficiency.indexOf('(')).trim());
                    result.add(language);
                    languageProficiency = "";
                }
            }

            if(word.toString().contains("Languages")){
                flag = true;
            }
        }

        return result;
    }

    public String getLinkedInName(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() > 200)
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        StringBuilder result = new StringBuilder();

        for (Integer y : sortedY) {
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            for(TextPosition position : line){
                if(position.getFontSize() == 26){
                    result.append(position.getUnicode());
                }
            }
        }
        return result.isEmpty() ? null : result.toString().trim();
    }

    public List<String> getLinkedInCertifications(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() < 200)
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        List<String> certifications = new ArrayList<>();
        Boolean flag = false;

        for (Integer y : sortedY) {
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            StringBuilder word = new StringBuilder();
            for(TextPosition position : line){
                word.append(position.getUnicode());
                if(flag && position.getFontSize() == 13){
                    return certifications;
                }
            }

            if(flag && !word.toString().equals("\u00A0")){
                certifications.add(word.toString());
            }

            if(word.toString().contains("Certifications")){
                flag = true;
            }
        }
        return certifications;
    }

    public List<String> getLinkedInAchievements(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() < 200)
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        List<String> honors = new ArrayList<>();
        Boolean flag = false;
        String achievement = "";

        for (Integer y : sortedY) {
            Integer nextY = sortedY.indexOf(y) + 1 >= sortedY.size() ? null : sortedY.get(sortedY.indexOf(y) + 1);
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            StringBuilder word = new StringBuilder();
            for(TextPosition position : line){
                word.append(position.getUnicode());
                if(flag && position.getFontSize() == 13){
                    return honors;
                }
            }

            if(flag && !word.toString().equals("\u00A0")){
                achievement = achievement + " " + word;
                if(nextY != null && nextY - y > 15){
                    honors.add(achievement.trim());
                    achievement = "";
                }
            }

            if(word.toString().contains("Honors")){
                flag = true;
            }
        }
        return honors;
    }

    public List<String> getLinkedInPublications(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() < 200)
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        List<String> publications = new ArrayList<>();
        Boolean flag = false;
        String publication = "";

        for (Integer y : sortedY) {
            Integer nextY = sortedY.indexOf(y) + 1 >= sortedY.size() ? null : sortedY.get(sortedY.indexOf(y) + 1);
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            StringBuilder word = new StringBuilder();
            for(TextPosition position : line){
                word.append(position.getUnicode());
                if(flag && position.getFontSize() == 13){
                    return publications;
                }
            }

            if(flag && !word.toString().equals("\u00A0")){
                publication = publication + " " + word;
                if(nextY != null && nextY - y > 15){
                    publications.add(publication.trim());
                    publication = "";
                }
            }

            if(word.toString().contains("Publications")){
                flag = true;
            }
        }
        return publications;
    }

    public List<Experience> getLinkedInExperience(List<List<TextPosition>> positionList){
        List<Experience> result = new ArrayList<>();
        Experience experience = new Experience();
        StringBuilder experiencePosition = new StringBuilder();
        StringBuilder experienceDescription = new StringBuilder();

        String company = "";
        Float font = 0f;
        Boolean flag = false;

        for(List<TextPosition> textPositions : positionList){
            Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() > 200)
                    .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
            List<Integer> sortedY = lines.keySet().stream().sorted().toList();

            for (Integer y : sortedY) {
                experienceDescription.append("\n");
                Integer prevY = sortedY.indexOf(y) == 0 ? null : sortedY.get(sortedY.indexOf(y) - 1);
                List<TextPosition> line = lines.get(y);
                line.sort(Comparator.comparing(TextPosition::getXDirAdj));

                StringBuilder word = new StringBuilder();
                StringBuilder experienceCompany = new StringBuilder();
                StringBuilder experienceDetail = new StringBuilder();
                for(TextPosition position : line){
                    word.append(position.getUnicode());
                    if(flag && position.getFontSize() == 15.75){
                        if(experience.getPositionName() != null){
                            String description = experienceDescription.toString().replace(experience.getLocationName() == null ? "" : experience.getLocationName(), "").trim();
                            experience.setDescription(description.isEmpty() ? null : description);
                            experience.setCompanyName(company);
                            result.add(experience);
                        }
                        return result;
                    }
                    if(flag && position.getFontSize() == 12){
                        experienceCompany.append(position.getUnicode());
                    }
                    if(position.getFontSize() == 10.5){
                        experienceDetail.append(position.getUnicode());
                    }
                    font = position.getFontSize();
                }

                if(flag && !word.isEmpty() && !word.toString().equals("\u00A0")
                        && experience.getPositionName() != null && experience.getDuration() != null && (font == 12 || font == 11.5)){
                    String description = experienceDescription.toString().replace(experience.getLocationName() == null ? "" : experience.getLocationName(), "").trim();
                    experience.setDescription(description.isEmpty() ? null : description);
                    experience.setCompanyName(company);
                    result.add(experience);

                    experienceDescription = new StringBuilder();
                    experiencePosition = new StringBuilder();
                    experience = new Experience();
                }

                if(flag && !experienceCompany.isEmpty() && !experienceCompany.toString().equals("\u00A0")
                        && !experienceCompany.toString().contains("Page")){
                    company = experienceCompany.toString();
                }

                if(flag && experience.getDuration() != null && !word.toString().contains("Page")){
                    experienceDescription.append(word).append(" ");
                }

                if(flag && font == 11.5){
                    experiencePosition.append(word).append(" ");
                }

                if(flag && !experiencePosition.isEmpty() && !experiencePosition.toString().equals("\u00A0")
                        && !experiencePosition.toString().contains("Page")){
                    experience.setPositionName(experiencePosition.toString().trim());
                }

                if(flag && !experienceDetail.isEmpty() && !experienceDetail.toString().equals("\u00A0")
                        && !word.toString().contains("Page") && experience.getDuration() != null
                        && experience.getLocationName() == null && y - prevY <= 15){
                    experience.setLocationName(experienceDetail.toString());
                }

                if(flag && !experienceDetail.isEmpty() && !experienceDetail.toString().equals("\u00A0") &&
                        (experienceDetail.toString().contains("tahun") || experienceDetail.toString().contains("year")
                                || experienceDetail.toString().contains("bulan") || experienceDetail.toString().contains("month"))
                        && experienceDetail.toString().contains("(") && !word.toString().contains("Page")){
                    experience.setDuration(experienceDetail.toString());
                }

                if(word.toString().equals("Experience") || word.toString().equals("Pengalaman")) {
                    flag = true;
                }
            }
        }
        return result;
    }

    public List<Education> getLinkedInEducation(List<List<TextPosition>> positionList){
        List<Education> result = new ArrayList<>();
        Education education = new Education();
        StringBuilder instituteName =  new StringBuilder();
        StringBuilder degreeName =  new StringBuilder();
        Boolean flag = false;

        for(List<TextPosition> textPositions : positionList){
            Map<Integer, List<TextPosition>> lines = textPositions.stream().filter(t -> t.getXDirAdj() > 200)
                    .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
            List<Integer> sortedY = lines.keySet().stream().sorted().toList();

            for (Integer y : sortedY) {
                List<TextPosition> line = lines.get(y);
                line.sort(Comparator.comparing(TextPosition::getXDirAdj));

                StringBuilder word = new StringBuilder();
                for(TextPosition position : line){
                    word.append(position.getUnicode());
                    if(flag && position.getFontSize() == 12){
                        if(!degreeName.toString().isEmpty()){
                            education.setInstitutionName(instituteName.toString().trim());
                            education.setDegreeName(degreeName.toString().substring(0, degreeName.toString().indexOf('·')).trim());
                            education.setDuration(degreeName.toString().substring(degreeName.toString().indexOf('·') + 1).trim());
                            result.add(education);

                            education = new Education();
                            instituteName = new StringBuilder();
                            degreeName = new StringBuilder();
                        }
                        instituteName.append(position.getUnicode());
                    }
                    if(flag && position.getFontSize() == 10.5 && !position.getUnicode().equals("\u00A0")){
                        degreeName.append(position.getUnicode());
                    }
                }

                if(word.toString().equals("Education") || word.toString().equals("Pendidikan")){
                    flag = true;
                }

                if(!degreeName.isEmpty()){
                    degreeName.append(" ");
                }
            }
        }
        if(!degreeName.toString().isEmpty()){
            education.setInstitutionName(instituteName.toString().trim());
            education.setDegreeName(degreeName.toString().substring(0, degreeName.toString().indexOf('·')).trim());
            education.setDuration(degreeName.toString().substring(degreeName.toString().indexOf('·') + 1).trim());
            result.add(education);
        }
        return result;
    }

    public void rightTest(List<List<TextPosition>> positionList){
        for(List<TextPosition> textPositions : positionList){
            Map<Integer, List<TextPosition>> lines = textPositions.stream()
                    .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
            List<Integer> sortedY = lines.keySet().stream().sorted().toList();

            for (Integer y : sortedY) {
                List<TextPosition> line = lines.get(y);
                line.sort(Comparator.comparing(TextPosition::getXDirAdj));

                StringBuilder word = new StringBuilder();
                for(TextPosition position : line){
                    word.append(position.getUnicode());
                    System.out.println("Letter : "+position.getUnicode()
                            +" X Coordinates : "+position.getXDirAdj()
                            +" Y Coordinates : "+position.getYDirAdj()
                            +" Font : " + position.getFont().getFontDescriptor().getFontBoundingBox()
                            +" Font Size : " + position.getFontSize()
                            +" Height : " + position.getHeight());
                }
            }
        }
    }
}
