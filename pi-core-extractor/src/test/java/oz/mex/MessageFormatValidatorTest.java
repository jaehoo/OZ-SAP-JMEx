package oz.mex;

import junit.extensions.TestSetup;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: asanchez
 * Date: 8/07/14
 * Time: 09:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageFormatValidatorTest {

    private MessageFormatValidator mfv;
    private String[] ids={
            "53BC030C-285A-0850-E100-80000A0A35C8"
            ,"53BC030C-285A0850E10080000A0A35C8"
            ,"53BC030C285A0850E10080000A0A35C8"
    };

    @Before
    public void init(){
        mfv = new MessageFormatValidator();
    }

    @Test
    public void testValidateIds() throws Exception {

        //mfv.validateIds();


    }
}
