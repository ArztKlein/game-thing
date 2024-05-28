import java.io.*;
import java.util.Scanner;

public class Score {
    //Players Score
    static int score;

    //Variables for HighScores
    String line;
    static String[] names = new String[11];
    static int[] highScores = new int[11];
    String[] split = new String[2];
    String tempName;
    int temp;
    int i = 0;
    static int count = 0;
    String delimiter = ";";
    File file = new File("TestSpace/resources/HighScores.txt");

    Scanner sc;

    public static int getHighScore(int i) {return highScores[i];}
    public static String getName(int i) {return names[i];}

    public Score() {
        //Scanner reads the HighScores file line by line storing each line in an array of strings
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);
        }
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            //Splits the lines taken from the HighScores file and separates the score and the scorers name
            split = line.split(delimiter);
            highScores[i] = Integer.parseInt(split[0]);
            names[i] = split[1];

            i++;
            count++;
        }
    }

    public boolean checkScore() {
        return score > getHighScore(count);
    }

    public void updateHighScore(String scoreName) {
        setHighScore(score, scoreName);
        orderHighScores();
        try {
            updateFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setHighScore(int score, String name) {
        if (count < 10) {
            highScores[count+1] = score;
            names[count+1] = name;
            count++;
        } else {
            highScores[10] = score;
            names[10] = name;
        }
    }

    public void orderHighScores() {
        i = count;
        for (int x = count-1; x >= 0; x--) {
            if (highScores[i] > highScores[x]) {
                temp = highScores[x];
                tempName = names[x];
                highScores[x] = highScores[i];
                names[x] = names[i];
                highScores[i] = temp;
                names[i] = tempName;
                i--;
            } else {
                break;
            }
        }
    }

    //Writes the score to the HighScores.txt, each line is the score, a delimiter ";", and the scorers name
    public void updateFile() throws IOException {
        Writer fileWriter = new FileWriter("TestSpace/resources/HighScores.txt", false);
        for (i = 0; i < count; i++) {
            line = highScores[i] + ";" + names[i] + "\n";
            fileWriter.append(line);
        }
        fileWriter.close();
    }
}
