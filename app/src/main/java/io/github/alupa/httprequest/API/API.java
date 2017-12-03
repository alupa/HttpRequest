package io.github.alupa.httprequest.API;

import com.google.gson.GsonBuilder;

import io.github.alupa.httprequest.API.Deserializers.MyDeserializer;
import io.github.alupa.httprequest.Models.City;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alvaro on 10-11-2017.
 */

public class API {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String BASE_ICONS = "http://openweathermap.org/img/w/";
    public static final String EXXTENSION_ICONS = ".png";

    public static final String APPKEY = "50e27259b77731471071269ffda0c2ed";

    private static Retrofit retrofit = null;

    public static Retrofit getAPI(){
        if (retrofit == null){
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(City.class, new MyDeserializer());
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
        }
        return retrofit;
    }
}
