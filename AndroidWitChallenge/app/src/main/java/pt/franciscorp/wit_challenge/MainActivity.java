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
        //UI START UP
        textViewWeatherInfo = findViewById(R.id.TextViewWeatherInfo);

        //VARS START UP
        weatherData = new WeatherData();
        weatherCommunication = new OpenWeatherMapCommunication("Coimbra", null, Constants.UnitsOfMeasure.METRIC);

        callWeatherApi();
        getAllCitiesWeatherFromApi();
        System.out.println();
    }

    public void getAllCitiesWeatherFromApi(){
        for(CityWeather city : weatherData.cityWeatherArrayList){
            callWeatherApi(city);
        }
    }

    public void callWeatherApi(CityWeather city) {
        new Thread(new Runnable() {
            public void run() {
                weatherInfo = weatherCommunication.getWeatherData(city.cityName, null, Constants.UnitsOfMeasure.METRIC);
                city.completeJson = weatherInfo;

                textViewWeatherInfo.post(new Runnable() {
                    public void run() {
                        textViewWeatherInfo.setText(weatherInfo);
                    }
                });
            }
        }).start();
    }

    public void callWeatherApi() {
        new Thread(new Runnable() {
            public void run() {
                weatherInfo = weatherCommunication.getWeatherData("Coimbra", null, Constants.UnitsOfMeasure.METRIC);

                textViewWeatherInfo.post(new Runnable() {
                    public void run() {
                        textViewWeatherInfo.setText(weatherInfo);
                    }
                });
            }
        }).start();
    }


}