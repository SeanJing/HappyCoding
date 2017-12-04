# Strings

* P3 Longest Substring Without Repeating Characters - Two Pointers
    ```java
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int result = 0;
        for(int i = 0, j = 0; j < s.length();j++) {
            if(map.containsKey(s.charAt(j))) {
                i = Math.max(i,map.get(s.charAt(j)) + 1);
            }
            result = Math.max(result, j-i+1);
            map.put(s.charAt(j),j);
        }
        
        return result;
    }

* P5 Longest Palindromic Substring 
