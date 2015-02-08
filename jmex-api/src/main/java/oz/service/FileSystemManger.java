package oz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by jaehoo on 2/8/15.
 */
public class FileSystemManger  implements FileManager{

    public static final Logger log = LoggerFactory.getLogger(FileSystemManger.class);

    private String pathName;
    private String fileExtension;
    public static final String defExtension = ".xml";

    public FileSystemManger(String pathName) {
        this.pathName = pathName;

        //TODO Validate path
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public void save(String fileName, String content) {
        try {

            //String content = "This is the content to write into file";

            if(fileExtension == null){
                fileExtension = defExtension;
            }

            File file = new File(pathName+fileName+fileExtension);
            File path = new File(pathName);

            log.info("Path:{} exists: {}", path, path.exists());


            if(!path.exists()){
                path.mkdirs();
            }

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();


        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
