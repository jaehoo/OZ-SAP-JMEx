package oz.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 8/07/14
 * Time: 08:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class Message {

    private String key;
    private MessageStatus messageStatus;
    private List payloads;
    private boolean founded;

    public Message(String key) {
        this.key = key;
        this.payloads= new ArrayList();
    }

    public Message(String key, MessageStatus messageStatus, boolean founded) {
        this.key = key;
        this.messageStatus = messageStatus;
        this.founded = founded;
    }
    
    
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public List getPayloads() {
        return payloads;
    }

    public void addPayload(String payload){
        this.payloads.add(payload);
    }

    public void setFounded(boolean founded) {
        this.founded = founded;
    }

    public boolean isFounded() {
        return founded;
    }

    @Override
    public String toString() {
        return "Message{" +
                "key='" + key + '\'' +
                '}';
    }
}

