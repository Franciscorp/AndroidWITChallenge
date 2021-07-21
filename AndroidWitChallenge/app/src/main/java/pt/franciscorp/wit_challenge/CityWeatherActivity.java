package pt.franciscorp.wit_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import pt.franciscorp.wit_challenge.Weather.CityWeather;

import static pt.franciscorp.wit_challenge.Utils.Util.getRoundInString;
import static pt.franciscorp.wit_challenge.Weather.WeatherUtils.getImageNameFromWeatherConditionID;

public class CityWeatherActivity extends AppCompatActivity {
    private TextView tvCwaCityName;
    private TextView tvCwaDate;
    private ImageView ivCwaWeatherIcon;
    private TextView tvCwaCurrentTemperature;
    private TextView tvCwaWeatherCondition;
    private TextView tvCwaMinMaxTemp;
    private TextView tvCwaWindSpeed;
    private TextView tvCwaHumidityValue;

    private CityWeather cityWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_city_weather);
        this.setTitle(R.string.app_title);

        setupViewsFromIDS();
        cityWeather = (CityWeather)getIntent().getSerializableExtra("CityWeather");
        setupLayoutWithData();
    }


    private void setupViewsFromIDS(){
        tvCwaCityName = findViewById(R.id.tvCwaCityName);
        tvCwaDate = findViewById(R.id.tvCwaDate);
        ivCwaWeatherIcon = findViewById(R.id.ivCwaWeatherIcon);
        tvCwaCurrentTemperature = findViewById(R.id.tvCwaCurrentTemperature);
        tvCwaWeatherCondition = findViewById(R.id.tvCwaWeatherCondition);
        tvCwaMinMaxTemp = findViewById(R.id.tvCwaMinMaxTemp);
        tvCwaWindSpeed = findViewById(R.id.tvCwaWindSpeed);
        tvCwaHumidityValue = findViewById(R.id.tvCwaHumidityValue);
    }

    private void setupLayoutWithData(){
        tvCwaCityName.setText(cityWeather.getCityName());
        tvCwaDate.setText(Calendar.getInstance().getTime().toString());
        tvCwaCurrentTemperature.setText("" + getRoundInString(cityWeather.getCurrentTemperature()) + "ºC");
        tvCwaWeatherCondition.setText(cityWeather.getWeatherCondition());
        tvCwaMinMaxTemp.setText(
                getRoundInString(cityWeather.getMinTemperature()) + "ºC   |   " +
                getRoundInString(cityWeather.getMaxTemperature()) + "ºC");
        tvCwaWindSpeed.setText(getRoundInString(cityWeather.getWindSpeed()) + " km/h");
        tvCwaHumidityValue.setText(getRoundInString(cityWeather.getHumidity()) + "%");

        String weatherConditionIcon = getImageNameFromWeatherConditionID(cityWeather.weatherConditionID);
        int resourceImageID = this.getResources().getIdentifier( weatherConditionIcon, "drawable", this.getPackageName());
        if(resourceImageID == 0)
            ivCwaWeatherIcon.setImageDrawable( this.getResources().getDrawable(R.drawable.partly_cloudy_day));
        else
            ivCwaWeatherIcon.setImageResource(resourceImageID);
    }

}