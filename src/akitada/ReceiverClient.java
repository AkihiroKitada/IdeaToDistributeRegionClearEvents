package akitada;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: akitada
 * Date: 2013/11/04
 * Time: 11:50
 *
 * This just responds if the Receiver side cluster get update or create events.
 */
public class ReceiverClient {
    public static void main(String[] argv)  throws Exception {
        ReceiverClient mxc = new ReceiverClient();
        mxc.run();
    }

    public void run() throws Exception {
        ClientCache cCache = new ClientCacheFactory()
                .set("cache-xml-file", "ReceiverClient.xml")
                .create();

        Region<String, MyPDXObject> exampleRegion = cCache.getRegion("ExampleRegion");
        System.out.println("ExampleRegion region, " + exampleRegion.getFullPath() + ", created in cache. ");

        exampleRegion.registerInterest("ALL_KEYS");

        System.out.println("\nPress Enter to finish this client...");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        bufferedReader.readLine();

        cCache.close();
    }
}
