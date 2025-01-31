import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] lines = getFileData("src/InputFile");
        Main m = new Main();
        System.out.println(m.partOne(lines));
        System.out.println(m.partTwo(lines));
        System.out.println(m.partThree(lines));
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

    public List<String[]> parseHands(String[] file) {
        List<String[]> result = new ArrayList<>();
        if (file == null) return result;
        for (String line : file) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (!line.contains("|")) {
                continue;
            }
            String[] parts = line.split("\\|");
            if (parts.length < 2) {
                continue;
            }
            String[] cardLabels = parts[0].split(",");
            if (cardLabels.length < 5) {
                continue;
            }
            String bidStr = parts[1].trim();
            result.add(new String[] {
                    cardLabels[0].trim(),
                    cardLabels[1].trim(),
                    cardLabels[2].trim(),
                    cardLabels[3].trim(),
                    cardLabels[4].trim(),
                    bidStr
            });
        }
        return result;
    }

    public String partOne(String[] file) {
        int fiveKind = 0;
        int fourKind = 0;
        int fullHouse = 0;
        int threeKind = 0;
        int twoPair = 0;
        int pair = 0;
        int highCard = 0;

        // Use the new parseHands function
        List<String[]> hands = parseHands(file);
        for (String[] h : hands) {
            String[] cardLabels = Arrays.copyOfRange(h, 0, 5);
            Map<String, Integer> frequencyMap = new HashMap<>();
            for (String label : cardLabels) {
                frequencyMap.put(label, frequencyMap.getOrDefault(label, 0) + 1);
            }
            List<Integer> freqs = new ArrayList<>(frequencyMap.values());
            Collections.sort(freqs, Collections.reverseOrder());
            if (freqs.size() == 1 && freqs.get(0) == 5) {
                fiveKind++;
            } else if (freqs.get(0) == 4) {
                fourKind++;
            } else if (freqs.get(0) == 3) {
                if (freqs.size() > 1 && freqs.get(1) == 2) {
                    fullHouse++;
                } else {
                    threeKind++;
                }
            } else if (freqs.get(0) == 2) {
                if (freqs.size() > 1 && freqs.get(1) == 2) {
                    twoPair++;
                } else {
                    pair++;
                }
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

        // Use the new parseHands function
        List<String[]> hands = parseHands(file);
        for (String[] h : hands) {
            String[] cardLabels = Arrays.copyOfRange(h, 0, 5);
            int bid = Integer.parseInt(h[5]);

            Map<String, Integer> freqMap = new HashMap<>();
            for (String lbl : cardLabels) {
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
                intCards[i] = cardValue.get(cardLabels[i]);
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

    public String partThree(String[] file) {
        String[] labelsForWild = { "Ace","King","Queen","10","9","8","7","6","5","4","3","2" };
        Map<String,Integer> tieValue = new HashMap<>();
        tieValue.put("Ace",   14);
        tieValue.put("King",  13);
        tieValue.put("Queen", 12);
        tieValue.put("10",    10);
        tieValue.put("9",     9);
        tieValue.put("8",     8);
        tieValue.put("7",     7);
        tieValue.put("6",     6);
        tieValue.put("5",     5);
        tieValue.put("4",     4);
        tieValue.put("3",     3);
        tieValue.put("2",     2);
        tieValue.put("Jack",  1);

        List<Integer> handTypes = new ArrayList<>();
        List<int[]> cardLists = new ArrayList<>();
        List<Integer> bids = new ArrayList<>();

        List<String[]> hands = parseHands(file);
        for (String[] h : hands) {
            String[] cardLabels = Arrays.copyOfRange(h, 0, 5);
            int bid = Integer.parseInt(h[5]);
            int jackCount = 0;
            List<String> nonJack = new ArrayList<>();
            for (String lbl : cardLabels) {
                if (lbl.equals("Jack")) {
                    jackCount++;
                } else {
                    nonJack.add(lbl);
                }
            }
            if (jackCount == 0) {
                Map<String, Integer> freqMap = new HashMap<>();
                for (String lbl : cardLabels) {
                    freqMap.put(lbl, freqMap.getOrDefault(lbl, 0) + 1);
                }
                List<Integer> freqs = new ArrayList<>(freqMap.values());
                Collections.sort(freqs, Collections.reverseOrder());
                int type;
                if (freqs.get(0) == 5) type = 7;
                else if (freqs.get(0) == 4) type = 6;
                else if (freqs.get(0) == 3 && freqs.size() > 1 && freqs.get(1) == 2) type = 5;
                else if (freqs.get(0) == 3) type = 4;
                else if (freqs.get(0) == 2 && freqs.size() > 1 && freqs.get(1) == 2) type = 3;
                else if (freqs.get(0) == 2) type = 2;
                else type = 1;

                int[] tieCards = new int[5];
                for (int i = 0; i < 5; i++) {
                    tieCards[i] = tieValue.get(cardLabels[i]);
                }
                handTypes.add(type);
                cardLists.add(tieCards);
                bids.add(bid);
            } else {
                int bestType = 1;

                int comboCount = (int) Math.pow(labelsForWild.length, jackCount);
                for (int combo = 0; combo < comboCount; combo++) {
                    Map<String,Integer> freqMap = new HashMap<>();
                    for (String lbl : nonJack) {
                        freqMap.put(lbl, freqMap.getOrDefault(lbl, 0) + 1);
                    }

                    int tmp = combo;
                    for (int j = 0; j < jackCount; j++) {
                        int pick = tmp % labelsForWild.length;
                        tmp /= labelsForWild.length;
                        String chosen = labelsForWild[pick];
                        freqMap.put(chosen, freqMap.getOrDefault(chosen, 0) + 1);
                    }

                    List<Integer> freqs = new ArrayList<>(freqMap.values());
                    Collections.sort(freqs, Collections.reverseOrder());
                    int type;
                    if (freqs.get(0) == 5) type = 7;
                    else if (freqs.get(0) == 4) type = 6;
                    else if (freqs.get(0) == 3 && freqs.size() > 1 && freqs.get(1) == 2) type = 5;
                    else if (freqs.get(0) == 3) type = 4;
                    else if (freqs.get(0) == 2 && freqs.size() > 1 && freqs.get(1) == 2) type = 3;
                    else if (freqs.get(0) == 2) type = 2;
                    else type = 1;

                    if (type > bestType) {
                        bestType = type;
                    }
                    if (bestType == 7) break;
                }

                int[] tieArr = new int[5];
                for (int i = 0; i < 5; i++) {
                    tieArr[i] = tieValue.get(cardLabels[i]);
                }
                handTypes.add(bestType);
                cardLists.add(tieArr);
                bids.add(bid);
            }
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
            totalValue += (long)(rank + 1) * bids.get(idx);
        }
        return "Total Bid Value With Jacks Wild: " + totalValue;
    }
}
