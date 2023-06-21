import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

public class FileCreator {
    
    private boolean result = true;

    FileCreator(String f) {

        try {
            String fileURI = f + ".txt";
            File file = new File(fileURI);

            if(file.exists()) {
                JOptionPane.showMessageDialog(null, "Error: File with this name already exists. Try again");
                result = false;
                return;
            }

            result = file.createNewFile();

        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        }
    }

    public boolean getResult() {
        return result;
    }
}
