/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.servlet;

/**
 *
 * @author INT303
 */
public class PrimeFinder {
    public static boolean isPrimer(long x) {
        boolean isPrime = x >= 2 ? true : false;
        for (long i = 2; i < x / 2 + 1; i++) {
            if (x % i == 0) {
                isPrime = false;
                break;
            }
            if(i%100000==0) {
                System.out.println(i);
            }
        }
        return isPrime;
    }
}
