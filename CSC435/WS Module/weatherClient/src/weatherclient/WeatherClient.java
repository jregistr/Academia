/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherclient;

import com.cdyne.ws.weatherws.WeatherReturn;

/**
 *
 * @author Jeff
 */
public class WeatherClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         WeatherReturn ozWeather = getCityWeatherByZIP("13126");
        System.out.println("Oz temperature:" + ozWeather.getTemperature());
    }
    
    private static WeatherReturn getCityWeatherByZIP(java.lang.String zip) {
        com.cdyne.ws.weatherws.Weather service = new com.cdyne.ws.weatherws.Weather();
        com.cdyne.ws.weatherws.WeatherSoap port = service.getWeatherSoap();
        return port.getCityWeatherByZIP(zip);
    }
    
}
