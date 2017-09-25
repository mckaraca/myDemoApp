package com.mycompany.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void testFound() {
      ArrayList<String> array = new ArrayList<>(Arrays.asList("bil481","bil461","bil361","bil331"));
      assertTrue(new App().search(array, "bil481","bil461"));
    }

    public void testNotFound() {
      ArrayList<String> array = new ArrayList<>(Arrays.asList("bil481","bil461","bil361","bil331"));
      assertFalse(new App().search(array, "bil481","bil441"));
    }

    public void testEmptyArray() {
      ArrayList<String> array = new ArrayList<>();
      assertFalse(new App().search(array, "bil481","bil461"));
    }

    public void testNull() {
      assertFalse(new App().search(null, "bil481","bil461"));
    }
    
    public void testUpperLetter(){
      ArrayList<String> array = new ArrayList<>(Arrays.asList("bil481","bil461","bil361","bil331"));
      assertFalse(new App().search(array,"bil481","Bil461"));
    }

}
