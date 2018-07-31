# Idea to distribute Region_Clear events via WAN Gateway
According to the specification of events distribution via WAN Gateway of Pivotal GemFire, it does not Region_Clear event to receiver side - i.e. even if executing Region#clear() at the sender side region, it is not executed at the receiver side region.

This sample emulate Region_Clear event distribution via WAN Gateway by replacing the event to Create event with using CacheListener.

The basic steps are as below.

* Get Region_Clear event at the sender side region with using CacheListener (ClearCacheListenerAdaptor).
* In the CacheListener, generate a Create event by creating the special data entries in the target region.
* The Create event is distributed by WAN Gateway from the sender side region to the receiver side region.
* Get Create event at the receiver side region with using AsyncEventListener (OpsExecutionAsyncEventListener). This is because of reducing duplicate events, esperically in the case of Replicated region with applying the CacheListener to all the members hosting the region.
* In the AsyncEventListener, execute Region#clear() if getting the special data entries regenerated at sender side instead of Regoin_Clear event.