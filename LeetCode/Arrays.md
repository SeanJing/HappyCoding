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
     P34  Search for a Range
      ```java
      public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1,-1};
        if(nums == null || nums.length == 0) return result;
        
        int start = 0;
        int end = nums.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start)/2;
            if(nums[mid] == target)
                end = mid;
            else if (nums[mid] < target)
                start = mid;
            else 
                end = mid;
        }
        
        if(nums[start] == target)
            result[0] = start;
        else if (nums[end] == target)
            result[0] = end;
        else 
            return result;
        
        start = 0; 
        end = nums.length - 1;
        
        while(start + 1 < end) {
            int mid = start + (end - start)/2;
            if(nums[mid] == target)
                start = mid;
            else if (nums[mid] < target)
                start = mid;
            else 
                end = mid;
        }
        
        if(nums[end] == target)
            result[1] = end;
        else if (nums[start] == target)
            result[1] = start;
        else 
            return result;
        
        return result;
      }
   * P35 Search Insert Position 
      ```java
      public int searchInsert(int[] a, int target) {
        int start = 0;
        int end = a.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start)/2;
            if(a[mid] == target)
                return mid;
            else if(a[mid] < target)
                start = mid;
            else
                end = mid;
        }
        if(target <= a[start])
            return start;
        else if(target <= a[end])
            return end;
        else
            return end+1;
      }
   * P153 Find Minimum in Rotated Sorted Array        
     P154 Find Minimum in Rotated Sorted Array with duplicates
       ```java
       public int findMin(int[] a) {
        int start = 0;
        int end = a.length - 1;
        while(start+1 < end) {
            int mid = start + (end - start)/2;
            if(a[mid] > a[end])
                start = mid;
            else
                end = mid;
        }
        
        if(a[start] < a[end])
            return a[start];
        else 
            return a[end];
      }    
   * P33 Search in Rotated Sorted Array 
      ```java
      public int search(int[] nums, int target) {
        if(nums == null || nums.length == 0) return -1;
        
        int start = 0;
        int end = nums.length - 1;
        
        while(start + 1 < end) {
            int mid = start + (end - start)/2;
            if(nums[mid] == target) return mid;
            
            if(nums[mid] < nums[end]) {
                if(nums[mid] < target && target <= nums[end])
                    start = mid;
                else 
                    end = mid;
            } else {
                if(nums[mid] > target && target >= nums[start])
                    end = mid;
                else 
                    start = mid;
            }
        }
        
        if(nums[start] == target)
            return start;
        else if (nums[end] == target)
            return end;
        else
            return -1;
      }
  
 * Greedy/Dynamic Programming            
   P55 Jump Game / P45 Jump Game II
   
 * Sorting           
   P56 Merge intervals / P57 Insert Intervals
   
