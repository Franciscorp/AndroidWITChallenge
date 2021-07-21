package pt.franciscorp.wit_challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import pt.franciscorp.wit_challenge.Utils.Constants;
import pt.franciscorp.wit_challenge.Utils.Logger;
import pt.franciscorp.wit_challenge.Utils.Util;

import static pt.franciscorp.wit_challenge.Utils.Constants.locationUpdateInterval;
import static pt.franciscorp.wit_challenge.Utils.Constants.threadTimeout;
import static pt.franciscorp.wit_challenge.Utils.Constants.updateHandlerInterval;


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
// 60m list view + threads efficiency
//45m bug fix current location. ListView size adapatable
//30m show current location city name
//30m updateInterval for app. Plus Optimizations
//20m tests of update optimization
//35m setting up new activity, passing data and organizing layout
//17m data passing to activity. Added onStart pause, stop to optimize program


public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private Handler updateWeatherHandler;
    private int numberOfCyclesPassed = 0;
    TextView textViewWeatherInfo;
    ListView listViewWeatherCities;
    Thread[] connectionToApiThreads;


    CityWeather currentLocationCityWeather;
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

        //UI START UP
        textViewWeatherInfo = findViewById(R.id.TextViewWeatherInfo);
        listViewWeatherCities = findViewById(R.id.lvWeatherCities);
        listViewWeatherCities = findViewById(R.id.lvWeatherCities);

        //TODO improve
        if (!getAppPermissions())
            return;

        //VARS START UP
        weatherCommunication = new OpenWeatherMapCommunication();
        updateWeatherHandler = new Handler();
        weatherData = new WeatherData();
        connectionToApiThreads = new Thread[weatherData.cityWeatherArrayList.size()];
        setWeatherListView();

        listViewWeatherCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, CityWeatherActivity.class);
                myIntent.putExtra("CityWeather", (CityWeather)weatherData.cityWeatherArrayList.get(position));
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopWeatherUpdateTask();
    }

    @Override
    public void onStart() {
        updateLocationAndGetItsWeather();
        startWeatherUpdateTask();
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopWeatherUpdateTask();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopWeatherUpdateTask();
    }

    Runnable weatherUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                numberOfCyclesPassed++;
                getAllCitiesWeatherFromApi();
                if(numberOfCyclesPassed == locationUpdateInterval){
                    numberOfCyclesPassed = 0;
                    updateLocationAndGetItsWeather();
                }
            } finally {
                updateWeatherHandler.postDelayed(weatherUpdateRunnable, updateHandlerInterval);
            }
        }
    };

    void startWeatherUpdateTask() {
        weatherUpdateRunnable.run();
    }

    //TODO see callbacks theory
    void stopWeatherUpdateTask() {
        updateWeatherHandler.removeCallbacks(weatherUpdateRunnable);
    }

    public void setWeatherListView() {
        citiesWeatherListAdapter = new CitiesWeatherListAdapter(this, R.layout.lv_layout_weather, R.id.tvLayoutWeatherList, weatherData.cityWeatherArrayList);
        listViewWeatherCities.setAdapter(citiesWeatherListAdapter);
    }

    private void updateLocationAndGetItsWeather(){
        //TODO REMOVE THIS VERIFICATION
        //note: this is done already
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            currentLocationCityWeather = new CityWeather(location.getLatitude(), location.getLongitude());
                            weatherData.cityWeatherArrayList.set(0, currentLocationCityWeather);
                            callWeatherApi(0, currentLocationCityWeather);

                            try {
                                connectionToApiThreads[0].join(threadTimeout);
                            } catch (InterruptedException exception) {
                                Logger.crashLog("MainActivity", "Location: An error occured while waiting for Current Weather Thread from API.", exception);
                            }
                            setWeatherListView();
                        }else{
                            Logger.log("MainActivity", "Location: An error occured while getting location. Location was null.");
                        }
                    }
                });
    }

    public boolean getAppPermissions(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            return false;
        }
        return true;
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
        //thead position starts at 1, because current is 0
        //it is treated within OnSucess response from location
        int threadPosition = 1;
        for(int i = threadPosition; i < weatherData.cityWeatherArrayList.size();i++){
            callWeatherApi(threadPosition, weatherData.cityWeatherArrayList.get(i));
            threadPosition++;
        }
        //TODO separar maybe
        threadPosition = 1;
        Thread current;
        for (int i = threadPosition; i < weatherData.cityWeatherArrayList.size(); i++){
            current = connectionToApiThreads[i];
            try {
                current.join(threadTimeout);
            } catch (InterruptedException exception) {
                Logger.crashLog("MainActivity", "weatherAPI: An error occured while waiting for Weather Threads from API.", exception);
            }
        }
        setWeatherListView();
    }

    public void callWeatherApi(int threadPosition ,CityWeather cityWeather) {
        connectionToApiThreads[threadPosition] = new Thread(new Runnable() {
            public void run() {
                cityWeather.completeJson = weatherCommunication.getWeatherData(cityWeather, null, Constants.UnitsOfMeasure.METRIC);
                //note: this process will asumme the threads would terminate connection after some time ( 100ms )

                //TODO error treatment in case of invalid request
                //note:won't be done. Not required for challenge
                Util.getCityWeatherFromJson(threadPosition, cityWeather ,cityWeather.completeJson);
            }
        });
        connectionToApiThreads[threadPosition].start();
    }
}