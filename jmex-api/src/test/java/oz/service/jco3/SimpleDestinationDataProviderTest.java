package oz.service.jco3;

import com.sap.conn.jco.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oz.util.CommonUtils;

/**
 * Created by jaehoo on 2/2/15.
 */
public class SimpleDestinationDataProviderTest extends CommonUtils {

    public static final Logger log = LoggerFactory.getLogger(SimpleDestinationDataProviderTest.class);

    public SimpleDestinationDataProvider dataProvider = new SimpleDestinationDataProvider();
    private String destName = "XIP";

    @Before
    public void setUp() throws Exception {
        log.info("Registering Data Provider");
        //register the provider with the JCo environment;
        //catch IllegalStateException if an instance is already registered
        try
        {
            com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(dataProvider);
        }
        catch(IllegalStateException providerAlreadyRegisteredException)
        {
            //somebody else registered its implementation,
            //stop the execution
            throw new Error(providerAlreadyRegisteredException);
        }

        dataProvider.setProperties(destName,getDestinationProperties());

    }

    @After
    public void tearDown() throws Exception {

        log.info("Unregister Data Provider");
        try
        {
            com.sap.conn.jco.ext.Environment.unregisterDestinationDataProvider(dataProvider);
        }
        catch(IllegalStateException providerAlreadyRegisteredException)
        {
            throw new Error(providerAlreadyRegisteredException);
        }
    }

    @Test
    public void testConnectJco3() throws Exception {

        JCoDestination dest;
        try
        {
            dest = JCoDestinationManager.getDestination(destName);
            dest.ping();
            log.info("Destination {} works", destName);
        }
        catch(JCoException e)
        {
            e.printStackTrace();
            log.info("Execution on destination {} failed", destName);
        }


    }

    @Test
    public void testCallFunction() throws Exception {

        JCoDestination destination =JCoDestinationManager.getDestination(destName);
        JCoFunction function =destination.getRepository().getFunction("STFC_CONNECTION");

        if(function == null){
            throw new RuntimeException("STFC_CONNECTION not found in SAP.");
        }

        JCoParameterList importParamList=function.getImportParameterList();

        importParamList.setValue("REQUTEXT", "Hello SAP!");

        try
        {
            function.execute(destination);
        }
        catch(AbapException e)
        {
            log.error("ABAP Error ¬¬ ...");
           log.error(e.getMessage(), e);
            return;
        }

        JCoParameterList exportParamList = function.getExportParameterList();

        log.info("STFC_CONNECTION finished:");
        log.info(" Echo: {}", exportParamList.getString("ECHOTEXT"));
        log.info(" Response: {}",exportParamList.getString("RESPTEXT"));

    }

}
