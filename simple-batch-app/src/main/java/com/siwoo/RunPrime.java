package com.siwoo;

import java.util.Arrays;

public class RunPrime {
    private static boolean[] isPrime;
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        isPrime = new boolean[n+1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int i=2; i<=n; i++) {
            if (isPrime[i]) {
                for (int j=i+i; j<=n; j+=i)
                    isPrime[j] = false;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<isPrime.length; i++)
            if (isPrime[i])
                sb.append(i).append("\n");
        System.out.println(sb);
    }
}
