package oz.model.dto;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 8/07/14
 * Time: 08:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageItem {

    private String uid;
    private MessageStatus messageStatus;

    public MessageItem(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }
}

