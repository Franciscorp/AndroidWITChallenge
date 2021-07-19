package pt.franciscorp.wit_challenge;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherMapCommunication{
//    public String openWeatherApiLink = "http://api.openweathermap.org/data/2.5/forecast?id=524901&appid=";
    public String openWeatherApiLink = "https://api.openweathermap.org/data/2.5/weather?q=";
    public String apiKey = "a6f443d25b2fc78fd00b664f9174bcb5";


    //api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
    //city iso names
    //https://www.iso.org/obp/ui/#search

    //	Units of measurement. standard, metric and imperial units are available. If you do not use the units parameter, standard units will be applied by default. Learn more

    //based of: https://www.spaceotechnologies.com/implement-openweathermap-api-android-app-tutorial/

    //language and units will be const, but left here for future adaption to other countries
//    public String getWeatherData(String cityName, String language, Constants.UnitsOfMeasure units) {
    public String getWeatherData(String cityName, String language, Constants.UnitsOfMeasure units) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            String url = openWeatherApiLink + cityName;

            //default unit
            if (units == null)
                units = Constants.UnitsOfMeasure.METRIC;
            if (language != null)
                url = url + "&lang=" + language+"&units" + units + "&APPID=" + apiKey;
            else
                url = url + "&units=" + units + "&APPID=" + apiKey;

            Log.d("Tag","URL " + url);

            con = (HttpURLConnection) ( new URL(url)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

// Let's read the response
            StringBuffer buffer = null;
            try {
                buffer = new StringBuffer();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ( (line = br.readLine()) != null )
                    buffer.append(line + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            is.close();
            con.disconnect();

            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
        return null;

    }
}
