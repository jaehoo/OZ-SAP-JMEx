package oz.service;

import com.sap.conn.jco.ext.DataProviderException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.util.HashMap;
import java.util.Properties;

/**
 * Created by jaehoo on 2/1/15.
 */
public class SimpleDestinationDataProvider implements DestinationDataProvider {

    private DestinationDataEventListener eventListener;
    private final HashMap<String, Properties> secureDBStorage = new HashMap<String, Properties>();

    public SimpleDestinationDataProvider() {




    }

    @Override
    public Properties getDestinationProperties(String destinationName) {
        try
        {
            //read the destination from DB
            Properties p = secureDBStorage.get(destinationName);

            if(p!=null)
            {
                //check if all is correct, for example
                if(p.isEmpty())
                    throw new DataProviderException(DataProviderException.Reason.INVALID_CONFIGURATION, "destination configuration is incorrect", null);

                return p;
            }

            return null;
        }
        catch(RuntimeException re)
        {
            throw new DataProviderException(DataProviderException.Reason.INTERNAL_ERROR, re);
        }
    }

    @Override
    public boolean supportsEvents() {
        return true;
    }

    @Override
    public void setDestinationDataEventListener(DestinationDataEventListener destinationDataEventListener) {
        this.eventListener = destinationDataEventListener;
    }

    public void setProperties(String destName, Properties properties)
    {
        synchronized(secureDBStorage)
        {
            if(properties==null)
            {
                if(secureDBStorage.remove(destName)!=null)
                    eventListener.deleted(destName);
            }
            else
            {
                secureDBStorage.put(destName, properties);
                eventListener.updated(destName); // create or updated
            }
        }
    }

}
