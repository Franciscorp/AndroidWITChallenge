package pt.franciscorp.wit_challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    TextView textViewWeatherInfo;

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
        //47 minutes for json parser
        //15m for icon
        //UI START UP
        textViewWeatherInfo = findViewById(R.id.TextViewWeatherInfo);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            //current location was based on long and lat
                            CityWeather cityWeather = new CityWeather(location.getLatitude(), location.getLongitude());
                            callWeatherApi(cityWeather);
                            System.out.println();

                            // Logic to handle location object
                        }
                    }
                });

        //VARS START UP
        weatherData = new WeatherData();
        weatherCommunication = new OpenWeatherMapCommunication();

//        getAllCitiesWeatherFromApi();

//        callWeatherApi(weatherData.cityWeatherArrayList.get(0));
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
                cityWeather.completeJson = weatherCommunication.getWeatherData(cityWeather, null, Constants.UnitsOfMeasure.METRIC);

                //TODO error treatment in case of invalid request
                //note:won't be done. Not required for challenge
                Util.getCityWeatherFromJson(cityWeather ,cityWeather.completeJson);

                textViewWeatherInfo.post(new Runnable() {
                    public void run() {
                        textViewWeatherInfo.setText(cityWeather.completeJson);
                    }
                });
            }
        }).start();
    }


}