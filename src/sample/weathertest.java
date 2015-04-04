package sample;


import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Eldin on 4-4-2015.
 */
public class weathertest {
    public static  void main(String args[]) throws IOException, JSONException {
        getCityWeather();
    }
    private static void getCityWeather() throws IOException, JSONException {
        OpenWeatherMap owm = new OpenWeatherMap("");

        CurrentWeather cwd = owm.currentWeatherByCityName("Rotterdam");

        float getMaxTemp = cwd.getMainInstance().getMaxTemperature();
        float getMinTemp = cwd.getMainInstance().getMinTemperature();
        float getCurrentTemp = cwd.getMainInstance().getTemperature();

        //convert float naar int, rond af naar beneden
        int minTemp = (int)getMinTemp;
        int maxTemp = (int)getMaxTemp;
        int curTemp = (int)getCurrentTemp;

        //convert Fahrenheit naar Celsius
        int minTempC = (minTemp - 32)*(5/9);
        int maxTempC = (maxTemp - 32)*(5/9);
        int curTempC = (curTemp - 32)*(5/9);

        System.out.println("City: " + cwd.getCityName());
        System.out.println("Temperature Fahrenheit: " + cwd.getMainInstance().getMaxTemperature()
                + "/" + cwd.getMainInstance().getMinTemperature() + "\'F" + "\n\r");

        System.out.println("Temperature Celsius: " + maxTempC
                + "/" + minTempC + "\'C" + "\n\r");

        // pakt de eerste resultaat van het weer van een arraylist en print uit de descriptie.
        System.out.println("descriptie: " + cwd.getWeatherInstance(0).getWeatherName() + "\n\r");
        //pakt de eerste resultaat van een arraylist en print uit de naam van het weer.
        System.out.println("naam: " + cwd.getWeatherInstance(0).getWeatherName() + "\n\r");

        //checkt of cloud info beschikbaar is en als het beschikbaar is dan
        //print die uit wat de percentage is van de wolken.
        boolean cloudy = cwd.hasCloudsInstance();
        if (cloudy){
            System.out.println("Cloudpercentage: " + cwd.getCloudsInstance().getPercentageOfClouds() + "\n\r");
        }

        //pakt de main info van het weer en print uit de real time temp.
        System.out.println("current temp Fahrenheit: " + cwd.getMainInstance().getTemperature() + "\'F" + "\n\r");
        System.out.println("current temp Celsius: " + curTempC + "\'C" + "\n\r");
        //checkt of humidity info beschikbaar is voor de stad en als het beschikbaar is print die uit wat het is.
        boolean humidity = cwd.getMainInstance().hasHumidity();
        if (humidity){
            System.out.println("humidity: " + cwd.getMainInstance().getHumidity() + "\n\r");
        }

        //denk wel dat het duidelijk is vanaf hier
        System.out.println("Wind degree: " + cwd.getWindInstance().getWindDegree() + "\n\r");

        System.out.println("Wind speed: " + cwd.getWindInstance().getWindSpeed() + "\n\r");

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////let op dat het weer in fahrenheit is en de rest weet ik niet in welke eenheden ze geprint worden.////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}
