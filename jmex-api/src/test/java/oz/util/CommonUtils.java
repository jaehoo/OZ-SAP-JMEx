package oz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oz.model.dto.Message;
import oz.service.FileManager;
import oz.service.FileSystemManger;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * Created by jaehoo on 2/8/15.
 */
public abstract class CommonUtils {

    public static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    protected FileManager fileManager;

    protected CommonUtils() {
        fileManager = new FileSystemManger(getPathToSaveFiles(), true);
    }

    protected Properties getDestinationProperties() throws IOException {

        Properties config = new Properties();
        String fileName="config.properties";
        //Read source
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream(fileName);

        if (is != null) {
            config.load(is);
        } else {
            throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
        }

        return config;
    }

    protected  List getKeys() throws IOException {
        List<String> list = new ArrayList<String>();

        //Read source
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream("messagesKeys.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line= null;
        while((line = reader.readLine()) != null) {
            log.info("{}", line);
            list.add(line);

        }
        log.info("list size:{}", list.size());
        reader.close();

        return list;
    }

    /**
     *
     * @return
     */
    protected String getPathToSaveFiles(){


        DateFormat df =new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();

        log.info("Today:\t{}", cal.getTime());
        cal.roll(Calendar.DATE, -1 );
        log.info("Yesterday:\t{}", cal.getTime());
        //log.info("Yesterday: {}", cal.get(Calendar.DAY_OF_MONTH));

        String date=df.format(cal.getTime());
        String defaultPath= System.getProperty("user.home")
                +File.separator+date+File.separator;

        log.info("Default path:{}", defaultPath);

        return defaultPath;

    }

    /**
     * Print Messages list and their contents
     * @param messages
     */
    public void printMessages(List<Message> messages){

        for(Message m : messages){
            log.info(m.toString());
        }

    }

    /**
     * Save messages
     * @param messages
     */
    protected void persist(List<Message> messages){

        if(fileManager== null){
            log.info("FileSystemManager is not initialized");
            return;
        }

        for(Message m : messages){
            List<String> payloads=m.getPayloads();

            int version=0;
            for (String  payload: payloads  ){
                fileManager.persist(m.getKey() + "-" + version, payload);
                version+=1;
            }
        }

    }

}

class TestUtils extends CommonUtils{

    public static void main(String[] args) {

        TestUtils t = new TestUtils();

    }

}