package pt.franciscorp.wit_challenge;

import java.util.ArrayList;

public class WeatherData {
    public ArrayList<CityWeather> cityWeatherArrayList;

    public WeatherData() {
        this.cityWeatherArrayList = new ArrayList<>();
        for(Constants.Cities city : Constants.Cities.values()){
            cityWeatherArrayList.add(new CityWeather(city.toString()));
        }
    }
}
