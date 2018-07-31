package akitada;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.Operation;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Properties;

// To conflate duplicate events, applying AsyncEventListener
public class OpsExecutionAsyncEventListener implements AsyncEventListener, Declarable {

  static final Logger LOGGER = LogManager.getLogger(OpsExecutionAsyncEventListener.class);

  private final static String OPS_KEY = "OPERATION";
  private final static String OPS_VALUE = "CLEAR";

  @Override
  public void init(Properties properties) {

  }

  @Override
  public boolean processEvents(List<AsyncEvent> list) {
    LOGGER.info("Size of List<AsyncEvent> = " + list.size());

    for (AsyncEvent ae : list) {
      if (ae.getOperation().equals(Operation.CREATE) && ae.getKey().equals(OPS_KEY) && ae.getDeserializedValue().equals(OPS_VALUE)) {
        // Need not to use SerialExecutor in the case of AsyncEventListener because the logic itself is executed asynchronously.
        Region<String, Object> region = ae.getRegion();
        region.clear();
        LOGGER.info("Got update/create event for " + region.getName()
            + " region to trigger clear region operatoin.");
      }
    }
    return true;
  }

  @Override
  public void close() {

  }
}
