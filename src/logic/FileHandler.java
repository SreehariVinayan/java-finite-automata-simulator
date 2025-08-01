package logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler{

    public List<String> readFile(String fileName, boolean skipFirstRow) throws IOException{
        fileName = fileName == "" ? "source.csv" : fileName;
        FileReader fileReader = new FileReader(System.getProperty("user.dir")+"/files/"+fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        if(bufferedReader.markSupported()){
            bufferedReader.mark(100);
        }

        List<String> lines = new ArrayList<>();
        String line;

        if(bufferedReader.ready()){
            while ((line = bufferedReader.readLine()) != null)
            {
                if(skipFirstRow){
                    skipFirstRow = false;
                    continue;
                } 
                lines.add(line);
            }
        }

        bufferedReader.close();

        return lines;
    }
    
}
