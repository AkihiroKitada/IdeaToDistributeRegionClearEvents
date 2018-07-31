package akitada;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.RegionEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// To get region_clear event and execute custom logics, applying CacheListener at Sender side
public class ClearCacheListenerAdaptor extends CacheListenerAdapter implements Declarable {
  ExecutorService exe = Executors.newSingleThreadExecutor();
  SerialExecutor serialExecutor = new SerialExecutor(exe);

  static final Logger LOGGER = LogManager.getLogger(ClearCacheListenerAdaptor.class);

  private final static String OPS_KEY = "OPERATION";
  private final static String OPS_VALUE = "CLEAR";

  // Regenerate cache event instead of region_clear event to distribute to Receiver side cluster
  public void afterRegionClear(RegionEvent event) {
    Region<String, Object> region = event.getRegion();

    // For your safety to prevent dead lock, it's recommend to execute region operations in cache
    // listener by separate thread with using SerialExecutor.
    serialExecutor.execute(new Runnable() {
      public void run() {
        region.put(OPS_KEY,OPS_VALUE);
        region.destroy(OPS_KEY);
        LOGGER.info("Got clear event for " + region.getName() + " region.");
      }
    });
  }

  @Override
  public void init(Properties properties) {

  }
}
