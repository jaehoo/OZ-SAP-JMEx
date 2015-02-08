package oz.service.jco3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oz.service.FileManager;
import oz.service.FileSystemManger;
import oz.service.MessagesExtractor;
import oz.util.CommonUtils;

import java.util.List;
import java.util.Properties;

/**
 * Created by jaehoo on 2/8/15.
 */
public class JCoMessagesExtractorTest extends CommonUtils {

    public static final Logger log = LoggerFactory.getLogger(JCoMessagesExtractorTest.class);

    private MessagesExtractor extractorService;
    public SimpleDestinationDataProvider dataProvider = new SimpleDestinationDataProvider();
    private String destName = "XIP";
    private FileManager fileManager;

    @Before
    public void setUp() throws Exception {
        log.info("Registering Data Provider");
        //register the provider with the JCo environment;
        //catch IllegalStateException if an instance is already registered
        try
        {
            com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(dataProvider);
        }
        catch(IllegalStateException providerAlreadyRegisteredException)
        {
            //somebody else registered its implementation,
            //stop the execution
            throw new Error(providerAlreadyRegisteredException);
        }

        Properties conf = getDestinationProperties();

        dataProvider.setProperties(destName,conf);

        fileManager = new FileSystemManger(conf.getProperty("fs.save.path"));

        JCoMessagesExtractor service= new JCoMessagesExtractor(dataProvider, destName);
        service.setSaveFiles(true);
        service.setFileManager(fileManager);
        extractorService = service;

    }

    @After
    public void tearDown() throws Exception {

        log.info("Unregister Data Provider");
        try
        {
            com.sap.conn.jco.ext.Environment.unregisterDestinationDataProvider(dataProvider);
        }
        catch(IllegalStateException providerAlreadyRegisteredException)
        {
            throw new Error(providerAlreadyRegisteredException);
        }
    }

    @Test
    public void testGetMessageById() throws Exception {

       extractorService.getMessageById("54D649AB2C221820E10080000A0A325A");

    }

    @Test
    public void testGetMessagesById() throws Exception {

        List<String> keys= getKeys();
        extractorService.getMessagesById(keys);
    }

}
