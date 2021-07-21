package pt.franciscorp.wit_challenge.Utils;

import pt.franciscorp.wit_challenge.R;

public class Constants {

    public static boolean debugVerbose = true;

    public enum UnitsOfMeasure {
        STANDART,
        METRIC,
        IMPERIAL
    }

    //to add more cities to retrieve from api
    //add here
    //note: Current must be always the first case
    public enum Cities {
        Current,
        Lisbon,
        Madrid,
        Berlim,
        Copenhagen,
        Rome,
        London,
        Dublin,
        Prague,
        Vienna,
        Vienna1,
        Vienna2,
        Vienna3,
        Vienna4
    }

    //https://openweathermap.org/weather-conditions
    public enum WeatherConditions {
        Thunderstorm,
        Drizzle,
        Rain,
        Snow,
        Mist,
        Smoke,
        Haze,
//        Dust,//731	Dust	sand/ dust whirls
        Fog,
        Sand,
        Dust,
        Ash,
        Squall,
        Tornado,
        Clear,
        Clouds
    }

    public enum WeatherConditionImage {
        cloud,
        cloud_lightning,
        sun,
        wind,
        light_snow,
        snow,
        heavy_rain,
        moderate_rain,
        rainfall,
        partly_cloudly_day,
        heavy_clouds,
        dust
    }

    //icons from: https://icons8.com/icon/set/weather/color
    public static int[] getWeatherIconsIDS(){
        int images[] = {
                R.drawable.cloud,
                R.drawable.cloud_lightning,
                R.drawable.sun,
                R.drawable.wind,
                R.drawable.light_snow,
                R.drawable.snow,
                R.drawable.light_rain,
                R.drawable.heavy_rain,
                R.drawable.moderate_rain,
                R.drawable.rainfall,
                R.drawable.partly_cloudy_day,
                R.drawable.heavy_clouds,
                R.drawable.dust
        };
        return images;
    }



}
