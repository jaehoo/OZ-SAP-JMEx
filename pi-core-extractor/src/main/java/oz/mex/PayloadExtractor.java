package oz.mex;

import com.sap.mw.jco.JCO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 8/07/14
 * Time: 04:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PayloadExtractor {

   //abstract public String getPayloadByMSGID(String msgid);
   abstract public ArrayList<String> getPayloadByMSGIDs(String[] msgids);

}
