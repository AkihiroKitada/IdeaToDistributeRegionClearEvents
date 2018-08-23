package akitada;

import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

public class ClearRegionCommand implements Command, Serializable {
  static final Logger LOGGER = LogManager.getLogger(ClearRegionCommand.class);

  private String regionName;
  
  public ClearRegionCommand(String regionName) {
    this.regionName = regionName;
  }
  
  public void process() {
    Region region = CacheFactory.getAnyInstance().getRegion(this.regionName);
    if (region != null) {
      LOGGER.info("Clearing region named " + region.getFullPath());
      region.clear();
    }
  }
  
  public String toString() {
    return new StringBuilder()
      .append("ClearRegionCommand[")
      .append("regionName=")
      .append(this.regionName)
      .append("]")
      .toString();
  }
}
