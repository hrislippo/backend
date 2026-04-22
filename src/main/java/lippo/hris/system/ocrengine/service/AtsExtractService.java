package lippo.hris.system.ocrengine.service;

import lippo.hris.system.ocrengine.response.SkillsCoordinateResponse;
import lippo.hris.system.ocrengine.util.*;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AtsExtractService {

    @Autowired
    SkillsExtractor skillsExtractor;

    @Autowired
    CertificationExtractor certificationExtractor;

    @Autowired
    AchievementExtractor achievementExtractor;

    @Autowired
    PublicationExtractor publicationExtractor;

    public String getATSName(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream()
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        StringBuilder result = new StringBuilder();
        List<TextPosition> line = new ArrayList<>();

        for(Integer y : sortedY){
            line = lines.get(y);
            if(line != null && line.size() > 1){
                break;
            }
        }

        line.sort(Comparator.comparing(TextPosition::getXDirAdj));
        for(TextPosition position : line){
            result.append(position.getUnicode());
        }

        return result.isEmpty() ? null : result.toString().trim();
    }

    public String getATSMobilePhone(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream()
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        String lineResult = "";

        for (Integer y : sortedY) {
            StringBuilder result = new StringBuilder();
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            for(TextPosition position : line){
                result.append(position.getUnicode());
            }

            if(result.chars().filter(Character::isDigit).count() >= 9){
                lineResult = result.toString();
                break;
            }
        }

        if(!lineResult.isEmpty()){
            String[] stringList = lineResult.split("[ /]+");
            for(String word : stringList){
                if(word.chars().filter(Character::isDigit).count() >= 9){
                    lineResult = word;
                }
            }
        }

        char[] chars = lineResult.toCharArray();
        StringBuilder result = new StringBuilder();
        for(char character : chars){
            if(Character.isLetter(character)){
                break;
            }
            result.append(character);
        }
        return result.toString().trim();
    }

    public String getATSEmail(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.getFirst();
        Map<Integer, List<TextPosition>> lines = textPositions.stream()
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();
        String lineResult = "";

        for (Integer y : sortedY) {
            StringBuilder result = new StringBuilder();
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));

            for(TextPosition position : line){
                result.append(position.getUnicode());
            }

            if(result.toString().contains("@")){
                lineResult = result.toString();
                break;
            }
        }

        if(!lineResult.isEmpty()){
            String[] stringList = lineResult.split("[ /]+");
            for(String word : stringList){
                if(word.contains("@")){
                    lineResult = word;
                }
            }
        }

        return lineResult;
    }

    public List<String> getATSSkills(List<List<TextPosition>> positionList){
        StringBuilder finalResult = new StringBuilder();
        List<String> skills = new ArrayList<>();
        Boolean flag = false;
        SkillsCoordinateResponse coordinateResponse = skillsExtractor.coordinate(positionList);
        if(coordinateResponse == null){
            return new ArrayList<>();
        }
        List<TextPosition> xyCoordinate = coordinateResponse.getPositionList();
        List<TextPosition> textPositions = positionList.get(coordinateResponse.getPage());
        TextPosition startCoordinate = xyCoordinate.getFirst();

        Map<Integer, List<TextPosition>> lines = textPositions.stream()
                .filter(t -> t.getXDirAdj() >= startCoordinate.getXDirAdj())
                .filter(t -> t.getYDirAdj() > startCoordinate.getYDirAdj())
                .filter(t -> !t.getUnicode().equals("\u00A0"))
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();

        for(Integer y : sortedY) {
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));
            for(TextPosition position : line){
                if(position.getFont().equals(coordinateResponse.getFont())){
                    flag = true;
                    break;
                }
                finalResult.append(position.getUnicode());
            }
            if(flag){
                break;
            }
            if(!finalResult.toString().trim().isEmpty()){
                skills.add(finalResult.toString().trim());
            }
            finalResult = new StringBuilder();
        }
        return skills;
    }

    public List<String> getATSCertifications(List<List<TextPosition>> positionList){
        StringBuilder finalResult = new StringBuilder();
        List<String> certifications = new ArrayList<>();
        Boolean flag = false;
        SkillsCoordinateResponse coordinateResponse = certificationExtractor.coordinate(positionList);
        if(coordinateResponse == null){
            return new ArrayList<>();
        }
        List<TextPosition> xyCoordinate = coordinateResponse.getPositionList();
        List<TextPosition> textPositions = positionList.get(coordinateResponse.getPage());
        TextPosition startCoordinate = xyCoordinate.getFirst();

        Map<Integer, List<TextPosition>> lines = textPositions.stream()
                .filter(t -> t.getXDirAdj() >= startCoordinate.getXDirAdj())
                .filter(t -> t.getYDirAdj() > startCoordinate.getYDirAdj())
                .filter(t -> !t.getUnicode().equals("\u00A0"))
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();

        for(Integer y : sortedY) {
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));
            for(TextPosition position : line){
                if(position.getFont().equals(coordinateResponse.getFont())){
                    flag = true;
                    break;
                }
                finalResult.append(position.getUnicode());
            }
            if(flag){
                break;
            }
            if(!finalResult.toString().trim().isEmpty()){
                certifications.add(finalResult.toString().trim());
            }
            finalResult = new StringBuilder();
        }
        return certifications;
    }

    public List<String> getATSAchievements(List<List<TextPosition>> positionList){
        StringBuilder finalResult = new StringBuilder();
        List<String> achievements = new ArrayList<>();
        Boolean flag = false;
        SkillsCoordinateResponse coordinateResponse = achievementExtractor.coordinate(positionList);
        if(coordinateResponse == null){
            return new ArrayList<>();
        }
        List<TextPosition> xyCoordinate = coordinateResponse.getPositionList();
        List<TextPosition> textPositions = positionList.get(coordinateResponse.getPage());
        TextPosition startCoordinate = xyCoordinate.getFirst();

        Map<Integer, List<TextPosition>> lines = textPositions.stream()
                .filter(t -> t.getXDirAdj() >= startCoordinate.getXDirAdj())
                .filter(t -> t.getYDirAdj() > startCoordinate.getYDirAdj())
                .filter(t -> !t.getUnicode().equals("\u00A0"))
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();

        for(Integer y : sortedY) {
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));
            for(TextPosition position : line){
                if(position.getFont().equals(coordinateResponse.getFont())){
                    flag = true;
                    break;
                }
                finalResult.append(position.getUnicode());
            }
            if(flag){
                break;
            }
            if(!finalResult.toString().trim().isEmpty()){
                achievements.add(finalResult.toString().trim());
            }
            finalResult = new StringBuilder();
        }
        return achievements;
    }

    public List<String> getATSPublications(List<List<TextPosition>> positionList){
        StringBuilder finalResult = new StringBuilder();
        List<String> publications = new ArrayList<>();
        Boolean flag = false;
        SkillsCoordinateResponse coordinateResponse = publicationExtractor.coordinate(positionList);
        if(coordinateResponse == null){
            return new ArrayList<>();
        }
        List<TextPosition> xyCoordinate = coordinateResponse.getPositionList();
        List<TextPosition> textPositions = positionList.get(coordinateResponse.getPage());
        TextPosition startCoordinate = xyCoordinate.getFirst();

        Map<Integer, List<TextPosition>> lines = textPositions.stream()
                .filter(t -> t.getXDirAdj() >= startCoordinate.getXDirAdj())
                .filter(t -> t.getYDirAdj() > startCoordinate.getYDirAdj())
                .filter(t -> !t.getUnicode().equals("\u00A0"))
                .collect(Collectors.groupingBy(t -> Math.round(t.getYDirAdj())));
        List<Integer> sortedY = lines.keySet().stream().sorted().toList();

        for(Integer y : sortedY) {
            List<TextPosition> line = lines.get(y);
            line.sort(Comparator.comparing(TextPosition::getXDirAdj));
            for(TextPosition position : line){
                if(position.getFont().equals(coordinateResponse.getFont())){
                    flag = true;
                    break;
                }
                finalResult.append(position.getUnicode());
            }
            if(flag){
                break;
            }
            if(!finalResult.toString().trim().isEmpty()){
                publications.add(finalResult.toString().trim());
            }
            finalResult = new StringBuilder();
        }
        return publications;
    }

    public void test(List<List<TextPosition>> positionList){
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

    public void testFirstPage(List<List<TextPosition>> positionList){
        List<TextPosition> textPositions = positionList.get(0);
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
