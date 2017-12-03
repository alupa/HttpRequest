package io.github.alupa.httprequest.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import io.github.alupa.httprequest.API.API;
import io.github.alupa.httprequest.API.APIServices.WeatherService;
import io.github.alupa.httprequest.Models.City;
import io.github.alupa.httprequest.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextSearch;
    private TextView textViewCity;
    private TextView textViewDescription;
    private TextView textViewTemp;
    private ImageView img;
    private Button btn;

    private WeatherService service;
    private Call<City> cityCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();

        /*String json =
                "{" +
                        "id: 0, " +
                        "ciudades: [" +
                            "{" +
                                "id: 1," +
                                "name: 'London'" +
                            "},{" +
                                "id: 2," +
                                "name: 'Seville'" +
                            "}]" +
                "}";

        City city = null;*/

        /*JSONObject mJson = null;
        try {
            mJson = new JSONObject(json);
            int id = mJson.getInt("id");
            String name = mJson.getString("name");
            city = new City(id, name);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //Toast.makeText(this, city.getId() + " -- " + city.getName(), Toast.LENGTH_LONG).show();

        /*Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        City city1 = gson.fromJson(json, City.class);
        Toast.makeText(this, city1.getId() + " -- " + city1.getName(), Toast.LENGTH_LONG).show();*/

        /*Gson gson = new GsonBuilder().create();
        Town town = gson.fromJson(json, Town.class);*/

        service = API.getAPI().create(WeatherService.class);
        btn.setOnClickListener(this);
    }

    private void setUI() {
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        textViewCity = (TextView) findViewById(R.id.textViewCity);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewTemp = (TextView) findViewById(R.id.textViewTemperature);
        img = (ImageView) findViewById(R.id.imageViewIcon);
        btn = (Button) findViewById(R.id.buttonSearch);
    }

    private void setResult(City city) {
        textViewCity.setText(city.getName() + ", " + city.getCountry());
        textViewDescription.setText(city.getDescription());
        textViewTemp.setText(city.getTemperature() + "°C");
        Picasso.with(this).load(API.BASE_ICONS + city.getIcon() + API.EXXTENSION_ICONS).into(img);
    }

    @Override
    public void onClick(View v) {
        String city = editTextSearch.getText().toString();
        if (!city.isEmpty()) {
            cityCall = service.getCity(city, API.APPKEY, "metric", "es");
            cityCall.enqueue(new Callback<City>() {
                @Override
                public void onResponse(Call<City> call, Response<City> response) {
                    City city = response.body();
                    setResult(city);
                }

                @Override
                public void onFailure(Call<City> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Type your city", Toast.LENGTH_SHORT).show();
        }
    }
}
