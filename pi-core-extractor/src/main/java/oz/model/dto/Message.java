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

    public Message(String key) {
        this.key = key;
        this.payloads= new ArrayList();
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

}

