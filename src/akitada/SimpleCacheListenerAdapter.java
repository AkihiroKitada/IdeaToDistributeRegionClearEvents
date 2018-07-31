package akitada;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

import java.util.Properties;

public class SimpleCacheListenerAdapter extends CacheListenerAdapter implements Declarable {

    public void afterCreate(EntryEvent e) {
        System.out.println("---Received afterCreate event for entry: " +
                e.getKey() + ", " + e.getNewValue());
    }

    public void afterUpdate(EntryEvent e) {
        System.out.println("---Received afterUpdate event for entry: " +
                e.getKey() + ", " + e.getNewValue());
    }
    public void init(Properties props) {
        // do nothing
    }
}