import java.io.*;
import java.util.*;

public class AIQuestionGenerator {
    private static final int TOTAL_QUESTIONS = 35;
    private final String[] topics;

    public AIQuestionGenerator() {
        topics = new String[]{"concept", "theory", "principle", "method", "application"};
    }

    public ArrayList<String> generateQuestions(String paperPath) {
        ArrayList<String> generatedQuestions = new ArrayList<>();
        BufferedReader reader = null;
        
        try {
            File paperFile = new File(paperPath);
            if (!paperFile.exists()) {
                throw new FileNotFoundException("Paper file not found: " + paperPath);
            }

            reader = new BufferedReader(new FileReader(paperFile));
            StringBuilder fileContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append(" ");
            }

            StringTokenizer tokenizer = new StringTokenizer(fileContent.toString(), " .,;:!?()[]{}\"\n\t");
            ArrayList<String> keywords = new ArrayList<>();

            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                if (token.length() > 4) {
                    keywords.add(token);
                }
            }

            for (int i = 0; i < TOTAL_QUESTIONS; i++) {
                String question = generateSingleQuestion(i, keywords);
                generatedQuestions.add(question);
            }

        } catch (FileNotFoundException e) {
            generatedQuestions.add("Error: Paper file not found - " + e.getMessage());
        } catch (IOException e) {
            generatedQuestions.add("Error: Unable to read paper - " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error closing reader: " + e.getMessage());
                }
            }
        }
        
        return generatedQuestions;
    }

    private String generateSingleQuestion(int index, ArrayList<String> keywords) {
        StringBuilder question = new StringBuilder();
        question.append((index + 1)).append(". ");

        int typeIndex = index % 5;
        switch (typeIndex) {
            case 0:
                question.append("Explain ");
                break;
            case 1:
                question.append("Define ");
                break;
            case 2:
                question.append("Describe ");
                break;
            case 3:
                question.append("Compare ");
                break;
            case 4:
                question.append("Analyze ");
                break;
            default:
                question.append("Discuss ");
        }

        if (!keywords.isEmpty()) {
            int keywordIndex = index % keywords.size();
            question.append("the concept of ").append(keywords.get(keywordIndex));
        } else {
            question.append("the ").append(topics[index % topics.length]);
        }

        question.append("?");
        return question.toString();
    }

    public ArrayList<String> generateQuestions(String paperPath, int count) {
        ArrayList<String> allQuestions = generateQuestions(paperPath);
        if (allQuestions.size() > count) {
            return new ArrayList<>(allQuestions.subList(0, count));
        }
        return allQuestions;
    }

    public static void main(String[] args) {
        AIQuestionGenerator generator = new AIQuestionGenerator();
        String paperPath = "sample_paper.txt";
        ArrayList<String> questions = generator.generateQuestions(paperPath);
        
        for (String question : questions) {
            System.out.println(question);
        }
    }
}
