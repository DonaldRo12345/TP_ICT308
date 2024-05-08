import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Question {
    private String question;
    private String correctAnswer;
    private List<String> distractors;

    public Question(String question, String correctAnswer, List<String> distractors) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.distractors = distractors;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getDistractors() {
        return distractors;
    }
}

public class TriviaGame {
    private List<Question> questions;
    private int score;

    public TriviaGame() {
        this.questions = new ArrayList<>();
        this.score = 0;
    }

    public void loadQuestionsFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String question = parts[0];
                String correctAnswer = parts[1];
                List<String> distractors = new ArrayList<>();
                for (int i = 2; i < parts.length; i++) {
                    distractors.add(parts[i]);
                }
                questions.add(new Question(question, correctAnswer, distractors));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        Collections.shuffle(questions);
        Scanner scanner = new Scanner(System.in);
        for (Question question : questions) {
            System.out.println(question.getQuestion());
            List<String> options = new ArrayList<>(question.getDistractors());
            options.add(question.getCorrectAnswer());
            Collections.shuffle(options);
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }
            System.out.print("Your answer: ");
            int userChoice = scanner.nextInt();
            String userAnswer = options.get(userChoice - 1);
            if (userAnswer.equals(question.getCorrectAnswer())) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was: " + question.getCorrectAnswer());
                break;
            }
        }
        System.out.println("Game over! Your score: " + score);
    }

    public static void main(String[] args) {
        TriviaGame game = new TriviaGame();
        game.loadQuestionsFromFile("questions.txt"); // Change to your file name
        game.startGame();
    }
}
