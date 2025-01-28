import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        String[] hands = getFileData("src/InputFile");
        System.out.println(Arrays.toString(hands));
    }

    public static String[] getFileData(String fileName) {
        String fileData = "";
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                fileData += s.nextLine() + "\n";
            }
            return fileData.split("\n");
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public String partOne(String[] file) {
        String[] cards = {"Ace", "King", "Queen", "Jack"};
        ArrayList<String> hands = new ArrayList(file.length);
        for (int i = 0; i < hands.size(); i++) {
            String index = hands.get(i);
            String[] w = index.split("\\|");
        }
        int fiveKind = 0;
        int fourKind = 0;
        int fullHouse = 0;
        int threeKind = 0;
        int twoPair = 0;
        int pair = 0;
        int highCard = 0;
        //Map<cards, hands> map = new HashMap<>();
        return "\n" +
                "Number of five of a kind hands: " + fiveKind + "\n" +
                "Number of full house hands: " + fourKind + "\n" +
                "Number of four of a kind hands: " + fullHouse + "\n" +
                "Number of three of a kind hands: " + threeKind + "\n" +
                "Number of two pair hands: " + twoPair + "\n" +
                "Number of one pair hands: " + pair + "\n" +
                "Number of high card hands: " + highCard;

    }

    public String partTwo (String[] args){
        return " ";
    }

}