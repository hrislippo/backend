package lippo.hris.system.ocrengine.util;

import lippo.hris.system.ocrengine.response.SkillsCoordinateResponse;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SkillsExtractor {

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

                if(word.toString().toLowerCase().contains("skills")){
                    Integer startIndex = word.toString().toLowerCase().indexOf("key skills");
                    startIndex = startIndex >= 0 ? startIndex : word.toString().toLowerCase().indexOf("skills");
                    startPositions.add(wordPositions.get(startIndex));

                    Integer endIndex;
                    if(word.toString().toLowerCase().contains("key skills")){
                        endIndex = startIndex + "key skills".length() - 1;
                    } else{
                        endIndex = startIndex + "skills".length() - 1;
                    }
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
