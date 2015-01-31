package oz.service;

import com.sap.mw.jco.JCO;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asanchez on 23/01/15.
 */
public class JCOMessagesExtractorTest {

    public static final Logger log = LoggerFactory.getLogger(JCOMessagesExtractorTest.class);
    private MessagesExtractor service;
    private JCO.Client client;

    @Before
    public void setUp(){
        log.info("Setup test case ====");

        client=  JCO.createClient("001", "USER", "PASS", "EN", "HOST", "11");
        service= new JCOMessagesExtractor(client);

    }

    @Test
    public void testGetMessagesById() throws Exception {

        List<String> keys= getKeys();
        log.info("Size:{} \n {}", keys.size(),keys);

        client.connect();
        service.getMessagesById(keys);
        client.disconnect();

    }

    @Test
    public void testGetMessageById() throws Exception {

        client.connect();
        service.getMessageById("54CC10960AE54140E10s080000A0A32A6");
        client.disconnect();

    }

    public List getKeys() throws IOException {
        List<String> list = new ArrayList<String>();

        //Read source
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream("PayloadList.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line= null;
        while((line = reader.readLine()) != null) {
            log.info("{}", line);
            list.add(line);

        }
        log.info("size:{}", list.size());
        reader.close();

        return list;
    }
}
