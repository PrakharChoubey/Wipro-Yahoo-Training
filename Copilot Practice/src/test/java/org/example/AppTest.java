package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testIsPalindrome() {
        App app = new App();
        assertTrue(app.isPalindrome("racecar"));
        assertFalse(app.isPalindrome("hello"));
    }

    public void testIsPrime() {
        App app = new App();
        assertTrue(app.isPrime(7));
        assertFalse(app.isPrime(8));
    }
}
