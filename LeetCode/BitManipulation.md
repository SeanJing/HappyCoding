# Bit Manipulation

## Bit Tricks
* set bit at position
    ```java
    x |= (1<<n);
* clear bit at position
    ```java
    x &= ~(1<<n);
* toggle bit at position
    ```java
    x ^= (1<<n);
* clear the lowest set bit of x
    ```java
    x & (x-1)
* get the lowest set bit of x (all others cleared)
    ```java
    x & ~(x-1)   or x & -x

* P78 Subsets
* Single Number
    * P136 Single Number - XOR              
    * P137 Single Number II / P169 Majority Element         
        ```java
        public int singleNumber(int[] nums) {
            int result = 0;
            for (int i = 0; i < 32; i++) {
                int sum = 0;
                for (int n:nums) {
                    sum += ((n >> i) & 1);
                }
                result |= (sum % 3) << i;
            }
            return result;
        }               

    * P260 Single Number III
        ```java
        public int[] singleNumber(int[] nums) {
            int[] result = new int[2];
            int xor = 0;
            for(int n : nums) xor^=n;
            
            xor &= -xor; // important trick
            int xor1 = 0;
            int xor2 = 0;
            
            for(int n: nums) {
                if((n&xor) == 0) xor1^=n;
                else xor2^=n;
            }
            
            result[0] = xor1;
            result[1] = xor2;
            return result;
            
        }

* P190 Reverse Bits
* P191 Number of 1 Bits / P231 Power of Two
