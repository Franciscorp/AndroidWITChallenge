package pt.franciscorp.wit_challenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.List;

import pt.franciscorp.wit_challenge.Utils.Logger;

import static pt.franciscorp.wit_challenge.Utils.Util.getImageNameFromWeatherConditionID;

public class CitiesWeatherListAdapter extends ArrayAdapter<CityWeather> {

    private Context context;
    private CityWeather cityWeather;
    private List<CityWeather> citiesWeatherList;
    private int resource;


    public CitiesWeatherListAdapter(@NonNull Context context, int resource, int tvResource, List<CityWeather> citiesWeatherList) {
        super(context, resource, tvResource, citiesWeatherList);
        this.context = context;
        this.citiesWeatherList = citiesWeatherList;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String cityName = getItem(position).getCityName();
        double cityTemperature = getItem(position).getCurrentTemperature();
        int simplifiedCityTemperature = ((int) cityTemperature);
        int weatherConditionID = getItem(position).getWeatherConditionID();
        String weatherIcon = getImageNameFromWeatherConditionID(weatherConditionID);
        Logger.log("WeatherList", "WeatherConditionIcon: " + weatherIcon );

        //inflate
        LayoutInflater inflater =  LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent ,false);

        //get elements from view
        TextView tvCityTemperature = (TextView) convertView.findViewById(R.id.tvCityTemperature);
        TextView tvCityName = (TextView) convertView.findViewById(R.id.tvCityName);
        ImageView ivWeatherIcon = (ImageView) convertView.findViewById(R.id.ivWeatherIcon);

        //set data
        tvCityTemperature.setText(simplifiedCityTemperature + "º");
        tvCityName.setText(cityName);

        int resourceImageID = context.getResources().getIdentifier( weatherIcon, "drawable", context.getPackageName());
        if(resourceImageID == 0)
            ivWeatherIcon.setImageDrawable( context.getResources().getDrawable(R.drawable.partly_cloudy_day));
        else
            ivWeatherIcon.setImageResource(resourceImageID);

        return super.getView(position, convertView, parent);
    }

}
