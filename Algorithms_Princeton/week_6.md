## Hash Tables
* Save items in a key-indexed table, index is a function of key
* Equality test
* Collision resolution

### Hash Functions
* Combine each significant field using the 31x + y rule.
* If field is a primitive type, use wrapper type hashCode().
* If field is null, return 0.
* If field is a reference type, use hashCode() recursively.
* If field is an array, apply to each entry.

### Separate Chaining
Use an array of M < N linked lists      
Typical M ~ N/5

### Linear Probing
When a new key collides, find next empty slot, and put it there     
Array size M must be greater than key-value pairs N
Typical N/M ~ 1/2
