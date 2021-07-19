package pt.franciscorp.wit_challenge;

public class CityWeather {
    public String countryName;
    public String cityName;
    public double maxTemperature;
    public double minTemperature;
    public double currentTemperature;
    public double windSpeed;
    public double humidity;
    //TODO debug, apagar
    public String completeJson;

    public CityWeather(String cityName) {
        this.cityName = cityName;
    }
}
