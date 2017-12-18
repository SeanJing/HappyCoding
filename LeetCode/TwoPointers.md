# Two Pointers

## P11 Container with Most Water

## P15 3Sum - check duplicates
   P16 3Sum Closet - check diff
   P18 4Sum - check duplicates
   
   
## P26 Remove Duplicates from Sorted Array
   P27 Remove Element
   
## P28 Implement strStr
  ```java
  public int strStr(String haystack, String needle) {
        int s = haystack.length();
        int t = needle.length();
        
        for(int i = 0; i <= s-t; i++) {
            int j = 0;
            for(; j < t; j++)
                if(needle.charAt(j) != haystack.charAt(i+j)) break;
            if(j == t) return i;
        }
        
        return -1;
    }
    
 
