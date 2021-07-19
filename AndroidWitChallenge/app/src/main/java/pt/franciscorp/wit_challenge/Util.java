package pt.franciscorp.wit_challenge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {

    //parser comes from: https://www.spaceotechnologies.com/implement-openweathermap-api-android-app-tutorial/

    public static CityWeather getCityWeatherFromJson(CityWeather cityWeather, String json){
        try {
            JSONObject jsonObject = new JSONObject(json);

            // weather type
            // We get weather info (This is an array)
            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            if(jsonArray.length() > 0){
                JSONObject JSONWeather = jsonArray.getJSONObject(0);
                cityWeather.setWeatherCondition(getString("main", JSONWeather));
                cityWeather.setWeatherTypeDescription(getString("description", JSONWeather));
//                weather.currentCondition.setWeatherId(getInt("id", JSONWeather));
//                weather.currentCondition.setIcon(getString("icon", JSONWeather));
            }

            JSONObject mainObject = getObject("main", jsonObject);
            cityWeather.setHumidity(getInt("humidity", mainObject));
            //TODO pressure
//            cityWeather.currentCondition.setPressure(getInt("pressure", mainObj));
            cityWeather.setMaxTemperature(getFloat("temp_max", mainObject));
            cityWeather.setMinTemperature(getFloat("temp_min", mainObject));
            cityWeather.setCurrentTemperature(getFloat("temp", mainObject));

            //wind
            JSONObject windObject = getObject("wind", jsonObject);
            cityWeather.setWindSpeed(getFloat("speed", windObject));

        } catch (JSONException e) {
            //TODO tratar
            e.printStackTrace();
        }

        return cityWeather;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }
    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

}
