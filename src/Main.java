import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] lines = getFileData("src/InputFile");

        Poker p = new Poker();

        // Part1 answer
        p.partOne(lines);
        System.out.println("Number of five of a kind hands: " + p.getFiveKind());
        System.out.println("Number of full house hands: " + p.getFullHouse());
        System.out.println("Number of four of a kind hands: " + p.getFourKind());
        System.out.println("Number of three of a kind hands: " + p.getThreeKind());
        System.out.println("Number of two pair hands: " + p.getTwoPair());
        System.out.println("Number of one pair hands: " + p.getPair());
        System.out.println("Number of high card hands: " + p.getHighCard() + "\n");

        // Part2 answer
        p.partTwo(lines);
        System.out.println("Total Bid Value: " + p.getTotalValue());

        // Part3 answer
        p.partThree(lines);
        System.out.println("Total Bid Value With Jacks Wild: " + p.getTotalValue());
    }

    private static String[] getFileData(String fileName) {
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
}