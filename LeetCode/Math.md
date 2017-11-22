## Math

* P204 Count Primes - Sieve

* Binary Search             
    * P29 Divide Two Integers
        ```java
        public int divide(int dividend, int divisor) {
            if (divisor == 0) 
                return dividend >= 0? Integer.MAX_VALUE : Integer.MIN_VALUE;
            if(dividend == Integer.MIN_VALUE && divisor == -1)
                return Integer.MAX_VALUE;
            if(dividend == 0) return 0;
            
            int sign = ((dividend<0)^(divisor<0)) ? -1:1;
            long a = Math.abs((long)dividend);
            long b = Math.abs((long)divisor);

            int result = 0;
            
            while(a >= b) {
                long temp = b;
                int m = 1;
                while((temp<<1)<=a) {
                    temp<<=1;
                    m<<=1;
                }
                a -= temp;
                result+=m;
            }
            
            return sign == 1 ? result: -result;
        }

    * P50 Pow(x,n)
        ```java
        public double myPow(double x, int n) {
            if(n == 0) return 1;
            if(n == 1) return x;
            
            double root = myPow(x, n/2);
            if(n%2 == 0)
                return root*root;
            else 
                return n > 0 ? root*root*x : root*root/x;
        }

    * P69 Sqrt(x)
    * P367 Valid Perfect Square

* HashMap
    * P166 Fraction to Recurring Decimal
    * P202 Happy Number


* P224 Basic Calculator - Stack
    ```java
    public int calculate(String s) {
        Stack<Character> operator = new Stack<>();
        Stack<Integer> operand = new Stack<>();
        for(int i = 0; i<s.length();i++) {
            char c = s.charAt(i);
            if(c<='9' && c>='0') {
                int sum = s.charAt(i)-'0';
                while(i+1<s.length() && s.charAt(i+1) <='9' && s.charAt(i+1)>='0') {
                    sum = s.charAt(i+1)-'0' + sum*10;
                    i++;
                }
                operand.push(sum);
            } else if (c == '+' || c == '-' || c =='('){
                operator.push(c);
                continue;
            } else if(c== ')') {
                operator.pop();
            } else
                continue;
            
            if(operator.isEmpty() || operator.peek()=='(') {
               continue;
            } else {
                char op = operator.pop();
                int n = operand.pop();
                if(op=='+') operand.push(n + operand.pop());
                else if (op=='-') operand.push(operand.pop() - n);
            }
        }
        
        return operand.peek();
    }

* DP
    * P279 Perfect Squares
    * P343 Integer Break

* Ugly Number
    * P263 Ugly Number
    * P264 Ugly Number II               
        1. Solution 1 - Merge 3 sorted lists
            ```java
            public int nthUglyNumber(int n) {
                int[] dp = new int[n];
                dp[0] = 1;
                int index2 = 0;
                int index3 = 0;
                int index5 = 0;
                int i = 1;
                
                while(i < n) {
                    int min = Math.min(Math.min(dp[index2]*2, dp[index3]*3), dp[index5]*5);
                    if(min == dp[index2]*2) index2++;
                    if(min == dp[index3]*3) index3++;
                    if(min == dp[index5]*5) index5++;
                    dp[i++] = min;
                }
                
                return dp[n-1];
            }
        
        2. Solution 2 - Priority Queue , credit from [jiuzhang](https://www.jiuzhang.com/solution/ugly-number-ii/)    
            ```java
            public int nthUglyNumber(int n) {
                Queue<Long> Q = new PriorityQueue<Long>();
                HashSet<Long> inQ = new HashSet<Long>();
                Long[] primes = new Long[3];
                primes[0] = Long.valueOf(2);
                primes[1] = Long.valueOf(3);
                primes[2] = Long.valueOf(5);
                for (int i = 0; i < 3; i++) {
                    Q.add(primes[i]);
                    inQ.add(primes[i]);
                }
                Long number = Long.valueOf(1);
                for (int i = 1; i < n; i++) {
                    number = Q.poll();
                    for (int j = 0; j < 3; j++) {
                        if (!inQ.contains(primes[j] * number)) {
                            Q.add(number * primes[j]);
                            inQ.add(number * primes[j]);
                        }
                    }
                }
                return number.intValue();
            }

        
    * P313 Super Ugly Number - Merge/PQ