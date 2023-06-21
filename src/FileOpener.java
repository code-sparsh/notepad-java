import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class FileOpener {

    private String fileName;
    private File file;
    private String fileContent ="";

    boolean result = true;

    FileOpener(String f) {

        try {
            fileName = f;
            String fileURI = fileName + ".txt";
            file = new File(fileURI);
            if(!file.exists()) {
                JOptionPane.showInternalMessageDialog(null, "Error: File with this name does not exist. Try again");
                result = false;
                return;
            }

            String line;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            
            while((line = reader.readLine()) != null){
                fileContent = fileContent + line + System.lineSeparator();
            }

            reader.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileContent() {
        return fileContent;
    }

    public boolean getResult() {
        return result;
    }

}
