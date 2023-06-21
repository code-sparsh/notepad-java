import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {

    private File file;
    private String fileName = "";
    private String fileURI = "";

    FileSaver(String f) {

        fileName = f;
        fileURI = fileName + ".txt";
        file = new File(fileURI);

    }

    public void save(String content) {

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return fileURI;
    }

}
