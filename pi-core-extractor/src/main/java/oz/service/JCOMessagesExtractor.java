package oz.service;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oz.model.dto.Message;
import static oz.util.Labels.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 8/07/14
 * Time: 04:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class JCOMessagesExtractor implements MessagesExtractor {

    public static final Logger log = LoggerFactory.getLogger(MessagesExtractor.class);

    private JCO.Client client;
    private IRepository repository;
    private IFunctionTemplate functionTemplate;
    private JCO.Function function;
    private JCO.Structure structure;
    private JCO.Table tableParam;
    private static final String PIPE_ID ="CENTRAL";
    private JCO.ParameterList exportParameterList;
    private JCO.ParameterList importParameterList;

    public void setClient(JCO.Client client) {
        this.client = client;
    }

    /**
     * Required valid client to initialize connection
     * @param client
     */
    public JCOMessagesExtractor(JCO.Client client) {
        this.client = client;
    }

    /**
     * Get a single message by Id from JCO connection
     * @param key Message unique Id
     * @return
     */
    @Override
    public Message getMessageById(String key) {

        initFunction();
        return extractById(key);

    }

    /**
     * Get multiple messages by specific key list
     * @param keys List of messages (unique Id's)
     * @return Founded list of messages with attributes
     */
    @Override
    public List<Message> getMessagesById(List<String> keys) {

        //TODO Validate msgid with REGEX String pattern="\\-\\s*$"

        List<Message> messages = null;

        if(keys== null || keys.size()== 0){
            throw new NullPointerException("The key list is empty");
            //TODO create Empty Exception
        }else {
            messages = new ArrayList<Message>();
        }

        initFunction();

        for(String key: keys ){
            messages.add(extractById(key));
        }

        return messages;
    }

    public void initFunction(){

        repository = JCO.createRepository("FunctionRepository", client);
        functionTemplate= repository.getFunctionTemplate("SXMB_READ_MESSAGE_VERSION_RAW");

        function = functionTemplate.getFunction();
        importParameterList = function.getImportParameterList();
        exportParameterList = function.getExportParameterList();

        structure = importParameterList.getStructure(MESSAGEKEY.name());

        // SELECTION must be like this!
        importParameterList.getField(SELECTION.name()).setValue("2");
        // This is the msg version number, where 000 is the first (Inbound);
        // the last can be caught from the function output (see below).
        // Setting this strongly depends on what you want to get: basically before or after the mapping...
        importParameterList.getField(VERSION_REQUEST.name()).setValue("000");

        tableParam = exportParameterList.getTable(MESSAGEPAYLOAD.name());

    }

    /**
     * Extract message by specific Id
     * @param key message unique Id
     * @return A message with attributes
     */
    private Message extractById(String key){

        structure.setValue(key, MSGID.name());
        structure.setValue(PIPE_ID, PID.name());

        client.execute(function);

        Message msj= null;
        log.info("MSGID:{}", key);

        if (tableParam.getNumRows() > 0) {
            log.info("*** Payload found! ***");

            msj = new Message(key);

            do{
                log.info("Message Last Version:{}",  exportParameterList.getField(MAXVERSION.name()).getString() );
                log.info("{} *** BEGIN ***", tableParam.getField(NAME.name()).getString());

                String payload=new String (tableParam.getField(PAYLOAD.name()).getByteArray());
                msj.addPayload(payload);
                log.info("{}", payload);

                //write(payload, key);

                log.info("{} *** END ***", tableParam.getField(NAME.name()).getString());
            }while(tableParam.nextRow());
        }
        else{
            log.info("*** No payload found! :( ***");
        }
        return msj;
    }

    public void write(String content, String filename){
        try {

            //String content = "This is the content to write into file";

            File file = new File("/abc/"+filename+".xml");

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            //System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
