package akitada;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

/**
 * Created with IntelliJ IDEA.
 * User: akitada
 * Date: 2018/08/23
 * Time: 18:24
 * To change this template use File | Settings | File Templates.
 */
public class SenderCommandClient {

    public static void main(String[] argv)  throws Exception {
        SenderCommandClient mxc = new SenderCommandClient();
        mxc.run();
    }

    public void run() throws Exception {
        ClientCache cCache = new ClientCacheFactory()
                .set("cache-xml-file", "SenderClient-command.xml")
            .set("statistic-archive-file","senderclient.gfs")
                .create();

        Region<String, MyPDXObject> exampleRegion = cCache.getRegion("ExampleRegion");
        System.out.println("Region, " + exampleRegion.getFullPath() + ", created in cache. ");

        for (int i = 1; i <= 50 ; i++) {
            String name = Integer.toString(i);
            String date = "20180802";
            MyPDXObject mpo = new MyPDXObject(name, date);

            exampleRegion.put("key" + name, mpo);
            //secondRegion.put("key" + name, mpo);
        }


        Thread.sleep(5000); // make sure that Receiver side cluster gets all the update event before getting region.clear() event
        cCache.getRegion("command").put(0, new ClearRegionCommand("ExampleRegion"));

        cCache.close();
    }
}
