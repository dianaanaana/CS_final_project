import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StoryLine {
    private ArrayList<String> lineArray = new ArrayList<String>();
    
    public void get_lineText(String FileLocate) throws IOException {
        String str;
        FileReader fileReader = new FileReader(FileLocate);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        lineArray.clear();
        
        while((str = bufferedReader.readLine()) != null) {
            lineArray.add(str);
        }
        
        bufferedReader.close();
        fileReader.close();
    }
    
    public ArrayList<String> getLine() {
        return lineArray;
    }
}