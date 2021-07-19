package pt.franciscorp.wit_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewWeatherInfo;
    String weatherInfo = "";

    WeatherData weatherData;
    OpenWeatherMapCommunication weatherCommunication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //15m planning on paper. 1 class. 2 basic drawing of UI
        //35 of setting up the first request. Not tested
        //47 minutes, UI is updated
        //22 m to Get All json data from api
        //UI START UP
        textViewWeatherInfo = findViewById(R.id.TextViewWeatherInfo);

        //VARS START UP
        weatherData = new WeatherData();
        weatherCommunication = new OpenWeatherMapCommunication();

//        getAllCitiesWeatherFromApi();

        callWeatherApi(weatherData.cityWeatherArrayList.get(0));
        System.out.println();
    }

    public void getAllCitiesWeatherFromApi(){
        for(CityWeather city : weatherData.cityWeatherArrayList){
            callWeatherApi(city);
        }
    }

    public void callWeatherApi(CityWeather cityWeather) {
        new Thread(new Runnable() {
            public void run() {
                weatherInfo = weatherCommunication.getWeatherData(cityWeather.cityName, null, Constants.UnitsOfMeasure.METRIC);
                //TODO error treatment in case of invalid request
                //note:won't be done. Not required for challenge
                cityWeather.completeJson = weatherInfo;
                Util.getCityWeatherFromJson(cityWeather ,cityWeather.completeJson);

                textViewWeatherInfo.post(new Runnable() {
                    public void run() {
                        textViewWeatherInfo.setText(weatherInfo);
                    }
                });
            }
        }).start();
    }


}