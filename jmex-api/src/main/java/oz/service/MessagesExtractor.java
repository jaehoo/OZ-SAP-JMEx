package oz.service;

import com.sap.mw.jco.JCO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oz.model.dto.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jaehoo
 * Date: 8/07/14
 * Time: 04:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessagesExtractor {

    /**
     * Get a single message by Id
     * @param key Message unique Id
     * @return Message with attributes
     */
   abstract public Message getMessageById(String key);

    /**
     * Get multiple messages by specific key list
     * @param keys List of messages (unique Id's)
     * @return Founded list of messages with attributes
     */
   abstract public List<Message> getMessagesById(List<String> keys);

}
