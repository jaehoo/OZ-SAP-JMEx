package oz.model.dto;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 8/07/14
 * Time: 08:46 PM
 * To change this template use File | Settings | File Templates.
 */
public enum MessageStatus{
    MSG_ID_ERROR("The message id format is incorrect")
    ,MSG_ID_OK("The message id is ok")
    ,DOWNLOADED("")
    ,NOT_FOUND("");

    private String codeDesc;

    private MessageStatus(String codeDesc){
        this.codeDesc= codeDesc;
    }

    public String getCodeDesc(){
     return this.codeDesc;
    }

}
