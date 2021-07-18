package pt.franciscorp.wit_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewWeatherInfo;
    String weatherInfo = "";


    OpenWeatherMapCommunication weatherCommunication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //15m planning on paper. 1 class. 2 basic drawing of UI
        //35 of setting up the first request. Not tested
        //47 minutes, UI is updated
        //UI START UP
        textViewWeatherInfo = findViewById(R.id.TextViewWeatherInfo);

        //VARS START UP
        weatherCommunication = new OpenWeatherMapCommunication("Coimbra", null, Constants.UnitsOfMeasure.METRIC);


//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                weatherInfo = weatherCommunication.getWeatherData("Coimbra", null, Constants.UnitsOfMeasure.METRIC);
////                weatherInfo = weatherCommunication.jsonObtained;
//                textViewWeatherInfo.setText(weatherInfo);
//            }
//        }, 5000);   //5 seconds
//
//
//        new Thread(new Runnable() {
//            public void run() {
//                weatherInfo = weatherCommunication.getWeatherData("Coimbra", null, Constants.UnitsOfMeasure.METRIC);
//                try{
//                    textViewWeatherInfo.setText(weatherInfo);
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        callWeatherApi();

    }

    public void callWeatherApi() {
        new Thread(new Runnable() {
            public void run() {
                weatherInfo = weatherCommunication.getWeatherData("Coimbra", null, Constants.UnitsOfMeasure.METRIC);

                textViewWeatherInfo.post(new Runnable() {
                    public void run() {
                        textViewWeatherInfo.setText(weatherInfo);
                    }
                });
            }
        }).start();
    }


}