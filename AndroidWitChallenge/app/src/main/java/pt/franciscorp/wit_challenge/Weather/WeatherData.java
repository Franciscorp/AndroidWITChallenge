package pt.franciscorp.wit_challenge.Weather;

import java.util.ArrayList;

import pt.franciscorp.wit_challenge.Utils.Constants;

public class WeatherData {
    public ArrayList<CityWeather> cityWeatherArrayList;

    public WeatherData() {
        this.cityWeatherArrayList = new ArrayList<>();
        for(Constants.Cities city : Constants.Cities.values()){
            cityWeatherArrayList.add(new CityWeather(city.toString()));
        }
    }
}
