package pt.franciscorp.wit_challenge.Weather;

import java.io.Serializable;

public class CityWeather implements Serializable {
    public String countryName;
    public String cityName;
    public double latitude;
    public double longitude;

    public int weatherConditionID;
    public String weatherCondition;
    public String weatherTypeDescription;
    //TODO image
    public double maxTemperature;
    public double minTemperature;
    public double currentTemperature;
    public double windSpeed;
    public double humidity;
    //TODO debug, apagar
    public String completeJson;


    ///
    //getters
    ///

    public String getCountryName() {
        return countryName;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    ///
    //gets and sets
    ///

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getWeatherConditionID() {
        return weatherConditionID;
    }

    public void setWeatherConditionID(int weatherConditionID) {
        this.weatherConditionID = weatherConditionID;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getWeatherTypeDescription() {
        return weatherTypeDescription;
    }

    public void setWeatherTypeDescription(String weatherTypeDescription) {
        this.weatherTypeDescription = weatherTypeDescription;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }




    public String getCompleteJson() {
        return completeJson;
    }

    public void setCompleteJson(String completeJson) {
        this.completeJson = completeJson;
    }

    ///
    //constructor
    ///

    public CityWeather(String cityName) {
        this.cityName = cityName;
    }

    public CityWeather(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
