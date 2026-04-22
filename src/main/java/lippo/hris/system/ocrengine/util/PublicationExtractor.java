package lippo.hris.system.ocrengine.util;

import lippo.hris.system.ocrengine.response.SkillsCoordinateResponse;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PublicationExtractor {

    public SkillsCoordinateResponse coordinate(List<List<TextPosition>> positionList) {
        List<TextPosition> startPositions = new ArrayList<>();
        List<TextPosition> endPositions = new ArrayList<>();
        List<TextPosition> positions = new ArrayList<>();
        List<Integer> pages = new ArrayList<>();
        Integer countPage = 0;

        for(List<TextPosition> textPositions : positionList) {
            Map<Integer, List<TextPosition>> lines = textPositions.stream()
                    .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
            List<Integer> sortedY = lines.keySet().stream().sorted().toList();

            for (Integer y : sortedY) {
                StringBuilder word = new StringBuilder();
                List<TextPosition> wordPositions = new ArrayList<>();

                List<TextPosition> line = lines.get(y);
                line.sort(Comparator.comparing(TextPosition::getXDirAdj));

                for (TextPosition position : line) {
                    word.append(position.getUnicode());
                    wordPositions.add(position);
                }

                if(word.toString().toLowerCase().contains("publication")){
                    Integer startIndex = word.toString().toLowerCase().indexOf("publication");
                    startPositions.add(wordPositions.get(startIndex));

                    Integer endIndex = startIndex + "publication".length() - 1;
                    endPositions.add(wordPositions.get(endIndex));
                    pages.add(countPage);
                }
            }
            countPage++;
        }
        if(startPositions.isEmpty() && endPositions.isEmpty()){
            return null;
        }
        positions.add(startPositions.stream().sorted(Comparator.comparing(TextPosition::getFontSize).reversed()).findFirst().get());
        positions.add(endPositions.stream().sorted(Comparator.comparing(TextPosition::getFontSize).reversed()).findFirst().get());
        Integer page = pages.get(startPositions.indexOf(positions.getFirst()));

        SkillsCoordinateResponse skillsCoordinateResponse = new SkillsCoordinateResponse();
        skillsCoordinateResponse.setPage(page);
        skillsCoordinateResponse.setPositionList(positions);
        skillsCoordinateResponse.setFontSize(positions.getFirst().getFontSize());
        skillsCoordinateResponse.setFont(positions.getFirst().getFont());
        return skillsCoordinateResponse;
    }
}
