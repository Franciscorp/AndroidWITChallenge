package pt.franciscorp.wit_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewWeatherInfo;

    OpenWeatherMapCommunication weatherCommunication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI START UP
        String weatherInfo = "";
        textViewWeatherInfo = findViewById(R.id.TextViewWeatherInfo);

        //VARS START UP
        weatherCommunication.getWeatherData("Coimbra", null, Constants.UnitsOfMeasure.METRIC);

        textViewWeatherInfo.setText(weatherInfo);

    }
}