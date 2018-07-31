package akitada;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

/**
 * Created with IntelliJ IDEA.
 * User: akitada
 * Date: 2013/11/04
 * Time: 11:50
 *
 * This put some data entries and execute region.clear.
 */
public class SenderClient {

    public static void main(String[] argv)  throws Exception {
        SenderClient mxc = new SenderClient();
        mxc.run();
    }

    public void run() throws Exception {
        ClientCache cCache = new ClientCacheFactory()
                .set("cache-xml-file", "SenderClient.xml")
                .create();

        Region<String, MyPDXObject> exampleRegion = cCache.getRegion("ExampleRegion");
        System.out.println("ExampleRegion region, " + exampleRegion.getFullPath() + ", created in cache. ");

        for (int i = 1; i <= 50 ; i++) {
            String name = Integer.toString(i);
            String date = "20180706";

            exampleRegion.put("key" + name, new MyPDXObject(name, date));
        }

        exampleRegion.clear();

        cCache.close();
    }
}
