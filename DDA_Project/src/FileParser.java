import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * this class is used for the fakeDB/testing only
 * @author Caelum Noonan
 */

public class FileParser {

    private String pathname;

    /**
     * @author Caelum Noonan
     */
    public FileParser(String pathname) {
        this.pathname = pathname;
    }

    /**
     * @author Caelum Noonan
     */
    public ArrayList<ArrayList<String[]>> putDataInOneListForAllFiles() throws FileNotFoundException {
        File dir = new File(pathname);
        ArrayList<ArrayList<String[]>> allFiles = new ArrayList<>();
        for (File file : dir.listFiles()) {
            Scanner s = new Scanner(file);
            ArrayList<String[]> tempList = new ArrayList<>();
            while (s.hasNextLine() == true) {
                String[] tempStringArr = s.nextLine().split(",");
                tempList.add(tempStringArr);
            }
            allFiles.add(tempList);
            s.close();
        }
        return allFiles;
    }

    /**
     * @author Caelum Noonan
     */
    public ArrayList<String[]> getOneFileFromList(ArrayList<ArrayList<String[]>> allFiles, int fileNumberWanted) {
        return allFiles.get(fileNumberWanted-1);
    }

}
