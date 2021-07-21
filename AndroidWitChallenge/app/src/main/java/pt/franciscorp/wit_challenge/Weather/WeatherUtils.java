package pt.franciscorp.wit_challenge.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.franciscorp.wit_challenge.Utils.Constants;
import pt.franciscorp.wit_challenge.Utils.Logger;

import static pt.franciscorp.wit_challenge.Utils.Util.getFloat;
import static pt.franciscorp.wit_challenge.Utils.Util.getInt;
import static pt.franciscorp.wit_challenge.Utils.Util.getObject;
import static pt.franciscorp.wit_challenge.Utils.Util.getString;
import static pt.franciscorp.wit_challenge.Utils.Util.isStringEmptyOrNull;

public class WeatherUtils {

    //parser comes from: https://www.spaceotechnologies.com/implement-openweathermap-api-android-app-tutorial/

    public static boolean getCityWeatherFromJson(int position, CityWeather cityWeather, String json){
        if(isStringEmptyOrNull(json))
            return false;

        try {
            JSONObject jsonObject = new JSONObject(json);

            //cityName
            if(position == 0)
                cityWeather.setCityName(getString("name", jsonObject));

            // weather type
            // We get weather info (This is an array)
            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            if(jsonArray.length() > 0){
                JSONObject JSONWeather = jsonArray.getJSONObject(0);
                cityWeather.setWeatherConditionID(getInt("id", JSONWeather));
                cityWeather.setWeatherCondition(getString("main", JSONWeather));
                cityWeather.setWeatherTypeDescription(getString("description", JSONWeather));
            }

            Logger.log("WeatherUtils", cityWeather.cityName + ": " + cityWeather.weatherCondition + " - " + cityWeather.weatherConditionID);

            JSONObject mainObject = getObject("main", jsonObject);
            cityWeather.setHumidity(getInt("humidity", mainObject));

            cityWeather.setMaxTemperature(getFloat("temp_max", mainObject));
            cityWeather.setMinTemperature(getFloat("temp_min", mainObject));
            cityWeather.setCurrentTemperature(getFloat("temp", mainObject));

            //wind
            JSONObject windObject = getObject("wind", jsonObject);
            cityWeather.setWindSpeed(getFloat("speed", windObject));

        } catch (JSONException jsonException) {
            Logger.crashLog("WeatherUtils", "An error occured while parsing Json from API to Weather Object.", jsonException);
        }

        return true;
    }

    public static String getImageNameFromWeatherConditionID(int weatherID){
        int rest = weatherID % 100;
        String weatherConditionImage;
        int weatherConditionGroup = weatherID - rest;

        switch(weatherConditionGroup) {
            case 200:
                weatherConditionImage = Constants.WeatherConditionImage.cloud_lightning.name();
                break;
            case 300:
                weatherConditionImage = Constants.WeatherConditionImage.moderate_rain.name();
                break;
            case 500:
                weatherConditionImage = Constants.WeatherConditionImage.heavy_rain.name();
                break;
            case 600:
                weatherConditionImage = Constants.WeatherConditionImage.snow.name();
                break;
            case 700:
                weatherConditionImage = Constants.WeatherConditionImage.dust.name();
                break;
            case 800:
                if(rest == 0)
                    weatherConditionImage = Constants.WeatherConditionImage.sun.name();
                else if(rest == 1 || rest == 2)
                    weatherConditionImage = Constants.WeatherConditionImage.partly_cloudly_day.name();
                else
                    weatherConditionImage = Constants.WeatherConditionImage.heavy_clouds.name();
                break;
            default:
                //it's not supposed to get here
                Logger.log("Weather","Weather Condition Group identified as Default. Valid group was not found.");
                weatherConditionImage = Constants.WeatherConditionImage.partly_cloudly_day.name();
        }
        return weatherConditionImage;
    }
}
