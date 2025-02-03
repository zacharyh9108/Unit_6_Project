import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] lines = getFileData("src/InputFile");
        Poker p = new Poker();
        System.out.println(p.partOne(lines));
        System.out.println(p.partTwo(lines));
        System.out.println(p.partThree(lines));
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