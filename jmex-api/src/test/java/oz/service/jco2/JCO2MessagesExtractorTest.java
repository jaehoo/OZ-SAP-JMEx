package oz.service.jco2;

import com.sap.mw.jco.JCO;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oz.service.FileManager;
import oz.service.FileSystemManger;
import oz.service.MessagesExtractor;
import oz.util.CommonUtils;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by asanchez on 23/01/15.
 */
public class JCO2MessagesExtractorTest extends CommonUtils{

    public static final Logger log = LoggerFactory.getLogger(JCO2MessagesExtractorTest.class);
    private MessagesExtractor serviceExtractor;
    private JCO.Client client;
    private FileManager fileManager;

    @Before
    public void setUp() throws IOException {
        log.info("Setup test case ====");

        Properties conf = getDestinationProperties();
        client=  JCO.createClient(conf);

        fileManager = new FileSystemManger(getPathToSaveFiles());

        JCO2MessagesExtractor service =new JCO2MessagesExtractor(client);
        service.setSaveFiles(true);
        service.setFileManager(fileManager);
        serviceExtractor = service;

    }

    @Test
    public void testGetMessagesById() throws Exception {

        List<String> keys= getKeys();
        client.connect();
        serviceExtractor.getMessagesById(keys);
        client.disconnect();

    }

    @Test
    public void testGetMessageById() throws Exception {

        client.connect();

        serviceExtractor.getMessageById("54dbfca263f00a70e10080000a0a35c8".toUpperCase());
        client.disconnect();

    }

}
