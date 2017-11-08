# Arrays

* P1 Two Sum: HashMap
* P15 Three Sum / P16 Three Sum Closest / P18 Four Sum: Two Pointers
* P11 MaxArea(Container With Most Water) : Two Pointers
* P31 Next Permutation : 
   ```java
   public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }
    
* 

   
