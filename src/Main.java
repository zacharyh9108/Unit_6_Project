import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] lines = getFileData("src/InputFile");
        Main m = new Main();
        System.out.println(m.partOne(lines));
        System.out.println(m.partTwo(lines));
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
            Map<String, Integer> frequencyMap = new HashMap<>();
            for (String label : cardLabels) {
                label = label.trim();
                frequencyMap.put(label, frequencyMap.getOrDefault(label, 0) + 1);
            }
            ArrayList<Integer> freqs = new ArrayList<>(frequencyMap.values());
            if (freqs.size() == 1 && freqs.get(0) == 5) {
                fiveKind++;
            } else if (freqs.size() == 2) {
                if (freqs.get(0) == 4) {
                    fourKind++;
                } else {
                    fullHouse++;
                }
            } else if (freqs.size() == 3) {
                if (freqs.get(0) == 3) {
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
                + "Number of high card hands: " + highCard + "\n";
    }

    public String partTwo(String[] file) {
        Map<String, Integer> cardValue = new HashMap<>();
        cardValue.put("Ace",   14);
        cardValue.put("King",  13);
        cardValue.put("Queen", 12);
        cardValue.put("Jack",  11);
        cardValue.put("10",    10);
        cardValue.put("9",     9);
        cardValue.put("8",     8);
        cardValue.put("7",     7);
        cardValue.put("6",     6);
        cardValue.put("5",     5);
        cardValue.put("4",     4);
        cardValue.put("3",     3);
        cardValue.put("2",     2);
        List<Integer> handTypes = new ArrayList<>();
        List<int[]> cardLists = new ArrayList<>();
        List<Integer> bids = new ArrayList<>();
        for (String line : file) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\|");
            String[] cardLabels = parts[0].split(",");
            int bid = Integer.parseInt(parts[1].trim());
            Map<String, Integer> freqMap = new HashMap<>();
            for (String lbl : cardLabels) {
                lbl = lbl.trim();
                freqMap.put(lbl, freqMap.getOrDefault(lbl, 0) + 1);
            }
            List<Integer> freqs = new ArrayList<>(freqMap.values());
            Collections.sort(freqs, Collections.reverseOrder());
            int type;
            if (freqs.get(0) == 5) {
                type = 7;
            } else if (freqs.get(0) == 4) {
                type = 6;
            } else if (freqs.get(0) == 3 && freqs.size() > 1 && freqs.get(1) == 2) {
                type = 5;
            } else if (freqs.get(0) == 3) {
                type = 4;
            } else if (freqs.get(0) == 2 && freqs.size() > 1 && freqs.get(1) == 2) {
                type = 3;
            } else if (freqs.get(0) == 2) {
                type = 2;
            } else {
                type = 1;
            }
            int[] intCards = new int[5];
            for (int i = 0; i < 5; i++) {
                String lbl = cardLabels[i].trim();
                intCards[i] = cardValue.get(lbl);
            }
            handTypes.add(type);
            cardLists.add(intCards);
            bids.add(bid);
        }
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < handTypes.size(); i++) {
            indices.add(i);
        }
        Collections.sort(indices, (i, j) -> {
            int t1 = handTypes.get(i);
            int t2 = handTypes.get(j);
            if (t1 != t2) {
                return t1 - t2;
            }
            int[] c1 = cardLists.get(i);
            int[] c2 = cardLists.get(j);
            for (int k = 0; k < 5; k++) {
                if (c1[k] != c2[k]) {
                    return c1[k] - c2[k];
                }
            }
            return 0;
        });
        long totalValue = 0;
        for (int rank = 0; rank < indices.size(); rank++) {
            int idx = indices.get(rank);
            totalValue += (long) (rank + 1) * bids.get(idx);
        }
        return "Total Bid Value: " + totalValue;
    }
}
