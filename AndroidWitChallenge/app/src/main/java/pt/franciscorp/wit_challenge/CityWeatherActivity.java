package pt.franciscorp.wit_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import static pt.franciscorp.wit_challenge.Utils.Constants.numberOfDecimalCases;
import static pt.franciscorp.wit_challenge.Utils.Constants.temperatureDecimalCases;
import static pt.franciscorp.wit_challenge.Utils.Util.getImageNameFromWeatherConditionID;
import static pt.franciscorp.wit_challenge.Utils.Util.round;

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
        setContentView(R.layout.activity_city_weather);
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
        tvCwaCurrentTemperature.setText(round(cityWeather.getCurrentTemperature(), temperatureDecimalCases) + "º");
        tvCwaWeatherCondition.setText(cityWeather.getWeatherCondition());
        tvCwaMinMaxTemp.setText(round(cityWeather.getMinTemperature()) + " | " + round(cityWeather.getMaxTemperature()));
        tvCwaWindSpeed.setText(round(cityWeather.getWindSpeed()) + "");
        tvCwaHumidityValue.setText(round(cityWeather.getHumidity()) + "%");

        String weatherConditionIcon = getImageNameFromWeatherConditionID(cityWeather.weatherConditionID);
        int resourceImageID = this.getResources().getIdentifier( weatherConditionIcon, "drawable", this.getPackageName());
        if(resourceImageID == 0)
            ivCwaWeatherIcon.setImageDrawable( this.getResources().getDrawable(R.drawable.partly_cloudy_day));
        else
            ivCwaWeatherIcon.setImageResource(resourceImageID);
    }

}