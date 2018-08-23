# Idea to distribute Region_Clear events via WAN Gateway
According to the specification of events distribution via WAN Gateway of Pivotal GemFire, it does not Region_Clear event to receiver side - i.e. even if executing Region#clear() at the sender side region, it is not executed at the receiver side region.

This sample emulates Region_Clear event distribution via WAN Gateway by replacing the event to Create event with using CacheListener or CacheWriter.

The original basic steps with using CacheListener are as below - using dummy object to distribute Region_Clear event within the target regoin.

* Put something data entries and issue Region.clear from SenderClient.
* Get Region_Clear event at the sender side region with using CacheListener (ClearCacheListenerAdaptor).
* In the CacheListener, generate a Create event by creating the special data entries in the target region.
* The Create event is distributed by WAN Gateway from the sender side region to the receiver side region.
* Get Create event at the receiver side region with using AsyncEventListener (OpsExecutionAsyncEventListener). This is because of reducing duplicate events, esperically in the case of Replicated region with applying the CacheListener to all the members hosting the region.
* In the AsyncEventListener, it executes Region#clear() if getting the special data entries regenerated at sender side instead of Regoin_Clear event.

The other steps with using CacheListener are as below - using dedicated region to distribute Region_Clear event for the target region. You can see "-mod" with modified files for this pattern.

* Put something data entries and issue Region.clear from SenderClient.
* Get Region_Clear event at the sender side target region with using CacheListener (RegionOperationListenerAdaptor).
* In the CacheListener, generate a Create event by creating the data entries in the dedicated region (/RegionOperation).
* The Create event is distributed by WAN Gateway from the sender side dedicated region to the receiver side dedicated region.
* Get Create event at the receiver side dedicated region with using AsyncEventListener (RegionOperationExecutionAsyncEventListener). This is because of reducing duplicate events, esperically in the case of Replicated region with applying the CacheListener to all the members hosting the taeget region.
* In the AsyncEventListener, it executes Region#clear() if getting the special data entries regenerated at sender side instead of Regoin_Clear event.

The most sophisticated steps with using CacheWriter are as below - using dedicated region to distribute Region_Clear event for the target region. You can see "-command" with modified files for this pattern - so called, "Command Region Pattern."

* Put something data entries and put Region.clear command into command region from SenderCommandClient.
* Get Region_Clear command and execute Region#clear() at the sender side target region with using CacheWriter (ProcessCommandCacheWriter).
* In the CacheWriter, generate a Create event by creating the data entries in the dedicated region (/command). CacheWriter is only executed on one of all the members per one event. So you don't have to take care of event duplication. And also, the type of the dedicated region is REPLICATE_PROXY. So you don't have to remove the data entry after executing Region#clear() like the above patterns.
* The Create event is distributed by WAN Gateway from the sender side dedicated region to the receiver side dedicated region.
* Get Create event at the receiver side dedicated region with using CacheWriter (ProcessCommandCacheWriter). 
* In the CacheWriter, it executes Region#clear() if getting the special data entries regenerated at sender side instead of Regoin_Clear event.

This sample is targetted to Pivotal GemFire 8.x.