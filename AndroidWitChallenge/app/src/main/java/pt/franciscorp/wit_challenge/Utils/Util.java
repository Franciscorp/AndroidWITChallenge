package pt.franciscorp.wit_challenge.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

import pt.franciscorp.wit_challenge.Weather.CityWeather;

import static pt.franciscorp.wit_challenge.Utils.Constants.numberOfDecimalCases;

public class Util {



    //https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP );
        return bd.doubleValue();
    }

    public static double round(double value) {
        int places = numberOfDecimalCases;
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP );
        return bd.doubleValue();
    }

    public static String getRoundInString(double value) {
        int places = numberOfDecimalCases;
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP );
        return bd.toPlainString();
    }

    public static boolean isStringEmptyOrNull(String str){
        if (str == null || str.isEmpty() || str.trim().isEmpty())
            return true;
        else
            return false;
    }

    //JSON PARSING UTIL

    public static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    public static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }
    public static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    public static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

}
