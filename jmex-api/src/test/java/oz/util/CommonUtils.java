package oz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by jaehoo on 2/8/15.
 */
public abstract class CommonUtils {

    public static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

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
}
