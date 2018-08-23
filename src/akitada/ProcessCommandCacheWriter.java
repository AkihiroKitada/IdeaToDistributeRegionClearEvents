package akitada;

import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheWriterAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class ProcessCommandCacheWriter extends CacheWriterAdapter<Integer,Command> implements Declarable {
  static final Logger LOGGER = LogManager.getLogger(ProcessCommandCacheWriter.class);

  public void beforeCreate(EntryEvent<Integer,Command> event) throws CacheWriterException {
    Command command = event.getNewValue();
    LOGGER.info("Received " + command);
    command.process();
  }

  public void init(Properties properties) {}

  public void close() {}
}
