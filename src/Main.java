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
        int onePair = 0;
        int highCard = 0;

        for (String line : file) {
            String[] parts = line.split("\\|");
            if (parts.length == 0) continue;

            String leftSide = parts[0].trim();
            if (leftSide.isEmpty()) continue;

            String[] cardLabels = leftSide.split(",");
            if (cardLabels.length != 5) continue;

            Map<String, Integer> frequencyMap = new HashMap<>();
            for (String label : cardLabels) {
                label = label.trim();
                frequencyMap.put(label, frequencyMap.getOrDefault(label, 0) + 1);
            }

            List<Integer> freqs = new ArrayList<>(frequencyMap.values());
            freqs.sort(Collections.reverseOrder());

            if (freqs.equals(Arrays.asList(5))) {
                fiveKind++;
            } else if (freqs.equals(Arrays.asList(4, 1))) {
                fourKind++;
            } else if (freqs.equals(Arrays.asList(3, 2))) {
                fullHouse++;
            } else if (freqs.equals(Arrays.asList(3, 1, 1))) {
                threeKind++;
            } else if (freqs.equals(Arrays.asList(2, 2, 1))) {
                twoPair++;
            } else if (freqs.equals(Arrays.asList(2, 1, 1, 1))) {
                onePair++;
            } else if (freqs.equals(Arrays.asList(1, 1, 1, 1, 1))) {
                highCard++;
            }
        }

        return "Number of five of a kind hands: " + fiveKind + "\n"
             + "Number of full house hands: " + fullHouse + "\n"
             + "Number of four of a kind hands: " + fourKind + "\n"
             + "Number of three of a kind hands: " + threeKind + "\n"
             + "Number of two pair hands: " + twoPair + "\n"
             + "Number of one pair hands: " + onePair + "\n"
             + "Number of high card hands: " + highCard;
    }

    public String partTwo(String[] args) {
        return " ";
    }
}