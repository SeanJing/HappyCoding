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
* P44 Wildcard Matching

* P14 Longest Common Prefix

* P17 Letter Combination of Phone Numbers - Backtracking/DFS
* P22 Generate Patentheses - Backtracking/DFS
    ```java
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        char[] chars = new char[2*n];
        helper(result, chars, n, 0,0,0);
        return result;
    }
    
    private void helper(List<String> result, char[] chars, int n, int pos, int left, int right) {
        if(left == n && right == n)
            result.add(new String(chars));
        
        if(left < n) {
            chars[pos] = '(';
            helper(result, chars, n, pos+1, left+1, right);
        }
        if(left > right) {
            chars[pos] = ')';
            helper(result, chars, n, pos+1, left, right+1);
        }
    }

* P20 Valid Parentheses - Stack
* P32 Longest Valid Parentheses - Stack / DP
    ```java 
    public int longestValidParentheses(String s) {
        if(s == null || s.length() == 0) return 0;
        Stack<Integer> stack = new Stack<>();
        int max = 0;
        for(int i = 0; i <s.length();i++) {
            char c = s.charAt(i);
            if(c == '(')
                stack.push(i);
            else {
                if(!stack.isEmpty() && s.charAt(stack.peek()) == '(') {
                    stack.pop();
                    max = Math.max(max, stack.isEmpty() ? i+1 : i-stack.peek());
                }
                else
                    stack.push(i);
            }
        }
        return max;
    }

* P28 Implement strStr - Two Pointers(early termination)
    ```java
    public int strStr(String haystack, String needle) {
        if(needle == null || needle.length() == 0) return 0;
        for(int i = 0; i <= haystack.length() - needle.length(); i++) {
            int j = 0;
            for(; j<needle.length();j++)
                if(needle.charAt(j) != haystack.charAt(i+j))
                    break;
            if(j == needle.length()) return i;
        }
        
        return -1;
    }
* P30 Substring with Concatenation of All Words