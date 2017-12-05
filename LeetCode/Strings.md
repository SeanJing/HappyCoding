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

* P5 Longest Palindromic Substring - DP

* P10 Regular Expression Matching - DP
    ```java
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        
        for(int i = 0; i <= m;i++) 
            for(int j = 1; j <= n;j++) {
                if(j>=2 && p.charAt(j-1) == '*') {
                    dp[i][j] = dp[i][j-2] || (i>=1 && isEqual(s.charAt(i-1), p.charAt(j-2)) && dp[i-1][j]);
                } else {
                    dp[i][j] = i>=1 && isEqual(s.charAt(i-1), p.charAt(j-1)) && dp[i-1][j-1];
                }
            }
        
        return dp[m][n];
    }
    
    private boolean isEqual(char s, char p) {
        return p=='.' || s == p;
    }

* P14 Longest Common Prefix
