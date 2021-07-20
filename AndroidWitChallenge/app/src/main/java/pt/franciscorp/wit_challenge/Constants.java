package pt.franciscorp.wit_challenge;

public class Constants {

    public enum UnitsOfMeasure {
        STANDART,
        METRIC,
        IMPERIAL
    }

    //to add more cities to retrieve from api
    //add here
    public enum Cities {
        Lisbon,
        Madrid,
        Berlim,
        Copenhagen,
        Rome,
        London,
        Dublin,
        Prague,
        vienna
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
                R.drawable.partly_cloudy_day
        };
        return images;
    }

}
