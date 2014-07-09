package oz.mex;

import com.sap.mw.jco.JCO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 8/07/14
 * Time: 04:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class JCOPayloadExtractor implements PayloadExtractor {

    public static final Logger LOG = LoggerFactory.getLogger(PayloadExtractor.class);

    private JCO.Client client;



    public void setClient(JCO.Client client) {
        this.client = client;
    }



    @Override
    public ArrayList<String> getPayloadByMSGIDs(String[] msgids) {


        //Validate msgid

        //validate String               String pattern="\\-\\s*$";



        return null;
    }


}
