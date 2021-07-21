package pt.franciscorp.wit_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CityWeatherActivity extends AppCompatActivity {
    private TextView tvCwaCityName;
    private TextView tvCwaDate;
    private ImageView ivCwaWeatherIcon;
    private TextView tvCwaCurrentTemperature;
    private TextView tvCwaWeatherCondition;
    private TextView tvCwaMinMaxTemp;

    private CityWeather cityWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        setupViewsFromIDS();
        cityWeather = (CityWeather)getIntent().getSerializableExtra("CityWeather");
        tvCwaCityName.setText(cityWeather.getCityName());
    }


    private void setupViewsFromIDS(){
        tvCwaCityName = findViewById(R.id.tvCwaCityName);
        tvCwaDate = findViewById(R.id.tvCwaDate);
        ivCwaWeatherIcon = findViewById(R.id.ivCwaWeatherIcon);
        tvCwaCurrentTemperature = findViewById(R.id.tvCwaCurrentTemperature);
        tvCwaWeatherCondition = findViewById(R.id.tvCwaWeatherCondition);
        tvCwaMinMaxTemp = findViewById(R.id.tvCwaMinMaxTemp);
    }

}