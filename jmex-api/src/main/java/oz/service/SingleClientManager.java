package oz.service;

import com.sap.mw.jco.JCO;
import org.omg.CORBA._PolicyStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by asanchez on 23/01/15.
 */
public class SingleClientManager {

    private static final Logger log = LoggerFactory.getLogger(SingleClientManager.class);

    private JCO.PoolManager poolManager;



    private void addClient(){


        poolManager = JCO.createPoolManager("Temp");


        poolManager.addClientPool("XIP", 10, "001", "USER", "PASS", "EN", "HOST", "11");


        log.info("{}",poolManager.getPoolNames());
        log.info("{}",poolManager.getName());
        JCO.Pool pool = poolManager.getPool("XIP");


        //poolManager.


    }


    public static void main(String args[]){

        SingleClientManager scm = new SingleClientManager();

        scm.addClient();

    }

}
