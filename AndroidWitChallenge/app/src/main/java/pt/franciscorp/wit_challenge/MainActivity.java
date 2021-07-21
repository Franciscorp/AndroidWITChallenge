package pt.franciscorp.wit_challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import pt.franciscorp.wit_challenge.Utils.Constants;
import pt.franciscorp.wit_challenge.Utils.Util;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    TextView textViewWeatherInfo;
    ListView listViewWeatherCities;

    WeatherData weatherData;
    CitiesWeatherListAdapter citiesWeatherListAdapter;
    OpenWeatherMapCommunication weatherCommunication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        this.setTitle("");
        setLayoutForApp();



        //15m planning on paper. 1 class. 2 basic drawing of UI
        //35 of setting up the first request. Not tested
        //47 minutes, UI is updated
        //22 m to Get All json data from api
        //47 minutes for json parser
        //15m for icon
        //60m for current location
        //20m change the theme, apply dark theme, title and others
        //43m lv, how it works, design changes
        //30m icons + debug full list
        //40m list adapter so far
        //60m em list adapter, images and parsing of images
        //9m compile errors
        //45m list view basic working. no images

        //UI START UP
        textViewWeatherInfo = findViewById(R.id.TextViewWeatherInfo);
        listViewWeatherCities = findViewById(R.id.lvWeatherCities);

        weatherData = new WeatherData();
        listViewWeatherCities = findViewById(R.id.lvWeatherCities);
        citiesWeatherListAdapter = new CitiesWeatherListAdapter(MainActivity.this, R.layout.lv_layout_weather,R.id.tvLayoutWeatherList, weatherData.cityWeatherArrayList);
        listViewWeatherCities.setAdapter(citiesWeatherListAdapter);


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
                            weatherData.cityWeatherArrayList.add(cityWeather);
                            fillWeatherListWith10Elements();
                            citiesWeatherListAdapter = new CitiesWeatherListAdapter(MainActivity.this, R.layout.lv_layout_weather,R.id.tvLayoutWeatherList, weatherData.cityWeatherArrayList);
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

        //form the list


    }

    private void fillWeatherListWith10Elements(){
        if(weatherData.cityWeatherArrayList.size() < 1 || weatherData.cityWeatherArrayList.size() > 10)
            return;
        CityWeather city = weatherData.cityWeatherArrayList.get(0);

        int i = weatherData.cityWeatherArrayList.size();
        while(i < 10){
            weatherData.cityWeatherArrayList.add(city);
            i++;
        }
    }

    private void setLayoutForApp() {
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
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