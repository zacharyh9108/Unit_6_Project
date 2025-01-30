import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] lines = getFileData("src/InputFile");
        Main m = new Main();
        System.out.println(m.partOne(lines));
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
        int fiveKind = 0;
        int fourKind = 0;
        int fullHouse = 0;
        int threeKind = 0;
        int twoPair = 0;
        int pair = 0;
        int highCard = 0;

        for (String line : file) {
            String[] parts = line.split("\\|");

            String leftSide = parts[0].trim();
            String[] cardLabels = leftSide.split(",");
            System.out.println(Arrays.toString(cardLabels));
            Map<String, Integer> frequencyMap = new HashMap<>();
            for (String label : cardLabels) {
                label = label.trim();
                frequencyMap.put(label, frequencyMap.getOrDefault(label, 0) + 1);
            }

            ArrayList<Integer> freqs = new ArrayList<>(frequencyMap.values());
            if (freqs.size() == 1 && freqs.getFirst() == 5) {
                fiveKind++;
            } else if (freqs.size() == 2) {
                if (freqs.getFirst() == 4) {
                    fourKind++;
                } else {
                    fullHouse++;
                }
            } else if (freqs.size() == 3) {
                if (freqs.getFirst() == 3) {
                    threeKind++;
                } else {
                    twoPair++;
                }
            } else if (freqs.size() == 4) {
                pair++;
            } else {
                highCard++;
            }
        }
        return "Number of five of a kind hands: " + fiveKind + "\n"
                + "Number of full house hands: " + fullHouse + "\n"
                + "Number of four of a kind hands: " + fourKind + "\n"
                + "Number of three of a kind hands: " + threeKind + "\n"
                + "Number of two pair hands: " + twoPair + "\n"
                + "Number of one pair hands: " + pair + "\n"
                + "Number of high card hands: " + highCard;
    }

    public String partTwo(String[] args) {
        return " ";
    }
}
