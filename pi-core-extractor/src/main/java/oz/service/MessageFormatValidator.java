package oz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oz.model.dto.MessageItem;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 8/07/14
 * Time: 08:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageFormatValidator {

    public static final Logger LOG = LoggerFactory.getLogger(MessageFormatValidator.class);

    private Pattern p= Pattern.compile("[-*]");
    private Matcher matcher;
    private ArrayList messages;


    public ArrayList<MessageItem> validateIds(String[] msgIds ){

        this.messages= new ArrayList();
        String tmsg;

        for(String msgId:msgIds){

            tmsg= msgId.trim();

            if(msgId!= null && msgId.length()>0 && tmsg.length()>0){
                LOG.info("the messages is null, or empty, or is invalid");
                continue;
            }

            matcher= p.matcher(tmsg);

            MessageItem msg;
            String nmsg;
            if(matcher.find()){
                LOG.debug("remove minus sign -");

                nmsg = matcher.replaceAll("");

                LOG.debug("Message converted to:{}", nmsg);
            }

                valMinSize(tmsg);
                addMessage(new MessageItem(msgId));
        }

        return messages;

    }

    private void valMinSize(String uid){

        if(uid.length()<=32){

        }

    }

    private void addMessage(MessageItem mit){


       this.messages.add(mit);
    }
}
