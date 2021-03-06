package akitada;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.Operation;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Properties;

public class RegionOperationExecutionAsyncEventListener implements AsyncEventListener, Declarable {

  static final Logger LOGGER = LogManager.getLogger(RegionOperationExecutionAsyncEventListener.class);

  private final static String OPS_VALUE = "CLEAR";
  private Cache cache = null;

  @Override
  public void init(Properties properties) {
    cache = CacheFactory.getAnyInstance();
  }

  @Override
  public boolean processEvents(List<AsyncEvent> list) {
    LOGGER.info("Size of List<AsyncEvent> = " + list.size());

    for (AsyncEvent ae : list) {
      if (ae.getOperation().equals(Operation.CREATE) && ae.getDeserializedValue().equals(OPS_VALUE)) {
        Region<Object, Object> targetRegion = cache.getRegion((String) ae.getKey());
        targetRegion.clear();
        LOGGER.info("Got Create event for " + targetRegion.getName()
            + " region to trigger region.clear operatoin.");
      }
    }
    return true;
  }

  @Override
  public void close() {

  }
}
