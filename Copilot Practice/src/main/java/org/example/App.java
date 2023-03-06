package org.example;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println( "Hello World!" );
        System.out.println( "Hello World!" );
        System.out.println( "Hello Wsd!" );
        System.out.println( "Hello World!" );
    }
    public static int add(int a, int b){
        return a+b;
    }

    //create a static function called subtract
    public static int subtract(int a, int b){
        return a-b;
    }

    public String reverse(String str){
        return new StringBuilder(str).reverse().toString();
    }
    public String substring(String str, int start, int end){
        return str.substring(start, end);
    }
    public static int calculateDaysBetweenTwoDates(Date startDate, Date endDate){
        long diff = endDate.getTime() - startDate.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    //is a palindrome
    public boolean isPalindrome(String str){
        return str.equals(reverse(str));
    }
    //is a number prime
    public boolean isPrime(int num){
        for(int i=2; i<num; i++){
            if(num%i==0){
                return false;
            }
        }
        return true;
    }

    //is a number even
    public boolean isEven(int num){
        return num%2==0;
    }

    //delete substring from a string
    public String deleteSubstring(String str, String subStr){
        return str.replace(subStr, "");
    }

    //is a string a palindrome
    public boolean isPalindromeString(String str){
        return str.equals(reverse(str));
    }

    //a function to return the concatenation of multiple strings
    public String concatenateStrings(String... strings){
        String result = "";
        for(String str: strings){
            result += str;
        }
        return result;
    }


}
