# Arrays

* P1 Two Sum: HashMap
* P 167 Two Sum - sorted / P15 Three Sum / P16 Three Sum Closest / P18 Four Sum: Two Pointers 
   ```java
   public int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        
        int i = 0;
        int j = numbers.length - 1;
        
        while(i < j) {
            int sum = numbers[i] + numbers[j];
            if(sum < target)
                i++;
            else if(sum > target)
                j--;
            else {
                result[0] = i+1;
                result[1] = j+1;
                break;
            }
        }
        
        return result;
    }
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


* Binary Search:
   * find an element in a sorted array
   * find the first/last occurrence of an element in a sorted array
   * find the min-element in a rotated array
   * find an element in a rotated array

   
