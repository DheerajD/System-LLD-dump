# Problem Statement
Create low level design for an LRU cache. Cache should support below operations:
1. Set: Allows user to add a value in cache against a key.
2. Get: Allows user to fetch previously saved values.
3. Print: Print current state of cache.

## Notes
* Added new exceptions DataNotFound, Insufficient storage for respective usecases.
* Implemented by factory pattern, there can be more cache implementations.
* Reused std lib Deque. Actual implementation of Queue is using doubly linked list.
* Value in <K,V> can further be modified to be an object encapsulating more data.
