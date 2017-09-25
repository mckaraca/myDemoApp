package com.mycompany.app;
import java.util.ArrayList;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

public static boolean search(ArrayList<String> array, String e1,String e2) {
        System.out.println("inside search");
        if (array == null) return false;
  	int sayac1 = 0;
	int sayac2 = 0;
        for (String elt : array) {
          if (elt.equals(e1)){
		sayac1++;
	  }
	  if(elt.equals(e2)){
		sayac2++;
	  }
	  if(sayac1 > 0 && sayac2 > 0)
		return true;
        }
        return false;
      }

}
