package oz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by asanchez on 16/02/15.
 */
public class Test {

    public static final Logger logger = LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void testRemoveTabs() throws Exception {

        Properties config = new Properties();
        String fileName="Resources.prop";
        //Read source
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream(fileName);

        if (is != null) {
            config.load(is);
        } else {
            throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
        }

        String text=config.getProperty("t1");

        char[] cArr = text.toCharArray();

        for(char c: cArr){
            logger.info("char:{}, code:{}", c, (int) c);
        }

        logger.info("IN:[{}]", text);

        String res = text.replaceAll("\\t", " ");
        logger.info("OuT:[{}]", res);

    }
}
