/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw2;

/**
 *
 * @author Jeffrey R
 */
public class Observation 
{
    private String date;
    private int windSpeed;
    private int temperature;
    private double windChill;
    
    public Observation (String d, int wS, int t)
    {
        date = d;
        windSpeed = wS;
        temperature = t;
        windChill = calcWindChill (temperature, windSpeed);
    }
    
    private double calcWindChill (int t, int wS)
    {
        // Suplly int temperature then int windSpeed.
      double const1 = 35.74;
      double const2 = 0.6215;
      double const3 = 35.75;
      double const4 = 0.4275;
      double constPow = 0.16;
      double wsSquared = Math.pow(wS, constPow);
      
      double wC = const1 + (const2 * t) - const3 * (wsSquared) + const4 * t * wsSquared;
      return wC;
    }
    
    public double getWindChill ()
    {
        return windChill;
    }
    
    public String getDate ()
    {
        return date;
    }
}
