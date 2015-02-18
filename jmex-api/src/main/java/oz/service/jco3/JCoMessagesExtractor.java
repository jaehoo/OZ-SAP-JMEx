package oz.service.jco3;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oz.model.dto.Message;
import oz.service.FileManager;
import oz.service.MessagesExtractor;

import java.util.ArrayList;
import java.util.List;

import static oz.util.Labels.*;

/**
 * Created by jaehoo on 2/7/15.
 */
public class JCoMessagesExtractor implements MessagesExtractor{

    public static final Logger log = LoggerFactory.getLogger(JCoMessagesExtractor.class);

    private DestinationDataProvider desDataProvider;
    private String destinationName;
    private JCoParameterList importParamList;
    private JCoParameterList exportParamList;
    private JCoStructure structure;
    private JCoTable tableParam;
    private static final String PIPE_ID ="CENTRAL";
    private JCoFunction function;
    private JCoDestination destination;

    public JCoMessagesExtractor(DestinationDataProvider desDataProvider, String destinationName) {
        this.desDataProvider = desDataProvider;
        this.destinationName = destinationName;
    }

    @Override
    public Message getMessageById(String key) {
        try{
            init();
        }
        catch(JCoException e){
            log.error(e.getMessage(), e);
        }

        return extractById(key);

    }

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

        try{
            init();
        }
        catch(JCoException e){
            log.error(e.getMessage(), e);
        }

        for(String key: keys ){
            messages.add(extractById(key));
        }

        return messages;
    }


    /**
     *
     * @throws JCoException
     */
    public void init() throws JCoException {

        destination = JCoDestinationManager.getDestination(destinationName);
        function = destination.getRepository().getFunction("SXMB_READ_MESSAGE_VERSION_RAW");

        if(function == null){
            throw new RuntimeException("SXMB_READ_MESSAGE_VERSION_RAW not found in SAP.");
        }

        importParamList=function.getImportParameterList();

        // SELECTION must be like this!
        importParamList.setValue(SELECTION.name(), "2");
        // This is the msg version number, where 000 is the first (Inbound);
        // the last can be caught from the function output (see below).
        // Setting this strongly depends on what you want to get: basically before or after the mapping...
        importParamList.setValue(VERSION_REQUEST.name(),"000");

        structure = importParamList.getStructure(MESSAGEKEY.name());

        exportParamList = function.getExportParameterList();
        tableParam = exportParamList.getTable(MESSAGEPAYLOAD.name());

    }

    /**
     *
     * @param key
     * @return
     */
    private Message extractById(String key){

        structure.setValue(MSGID.name(), key);
        structure.setValue(PID.name(), PIPE_ID);

        try
        {
            function.execute(destination);
        }
        catch(AbapException e)
        {
            log.error("ABAP Error ¬¬ ...");
            log.error(e.getMessage(), e);
            return null;
        }
        catch(JCoException e){
            log.error(e.getMessage(), e);
            return null;
        }

        Message msj= null;
        log.info("MSGID:{}", key);

        if (tableParam.getNumRows() > 0) {
            log.info("*** Payload found! ***");

            msj = new Message(key);
            msj.setFounded(true);
            do{
                String payload=new String(tableParam.getByteArray(PAYLOAD.name()));
                log.debug("Message Last Version:{}", exportParamList.getString(MAXVERSION.name()));
                log.debug("File size: {} bytes", payload.getBytes().length);
                log.debug("{} *** BEGIN ***", tableParam.getString(NAME.name()));
                 msj.addPayload(payload);

                log.debug("{}", payload);
                log.debug("{} *** END ***", tableParam.getString(NAME.name()));
            }while(tableParam.nextRow());
        }
        else{
            log.info("*** No payload found! :( ***");
        }
        return msj;
    }



}
