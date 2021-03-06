package akitada;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.RegionEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegionOperationListenerAdaptor extends CacheListenerAdapter implements Declarable {
  ExecutorService exe = Executors.newSingleThreadExecutor();
  SerialExecutor serialExecutor = new SerialExecutor(exe);

  static final Logger LOGGER = LogManager.getLogger(RegionOperationListenerAdaptor.class);

  private final static String OPS_REGION = "RegionOperation";
  private final static String OPS_VALUE = "CLEAR";
  private Cache cache = null;

  public void afterRegionClear(RegionEvent event) {
    serialExecutor.execute(new Runnable() {
      public void run() {
        Region<String, String> operationRegion = cache.getRegion(OPS_REGION);
        String targetRegion = event.getRegion().getName();
        operationRegion.put(targetRegion,OPS_VALUE);
        operationRegion.destroy(targetRegion);
        LOGGER.info("Got Region_Clear event for " + targetRegion + " region.");
      }
    });
  }

  @Override
  public void init(Properties properties) {
    cache = CacheFactory.getAnyInstance();
  }
}
