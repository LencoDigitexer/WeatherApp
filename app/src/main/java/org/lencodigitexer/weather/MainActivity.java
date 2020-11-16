package org.lencodigitexer.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvTown;
    TextView tvTemp;
    TextView tvDesc;
    TextView tvFeel;
    TextView tvMin;
    TextView tvMax;
    TextView tvPressure;
    TextView tvHumidity;
    TextView tvWindSpeed;
    TextView tvWindDeg;

    ImageView ivImage;
    EditText etSearch;
    FloatingActionButton btSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Design_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvTown = (TextView)findViewById(R.id.tvTown);
        tvTemp = (TextView)findViewById(R.id.tvTerm);
        tvDesc = (TextView)findViewById(R.id.tvDesc);
        tvFeel = (TextView)findViewById(R.id.tvFeel);
        tvMin = (TextView)findViewById(R.id.tvMin);
        tvMax = (TextView)findViewById(R.id.tvMax);
        tvPressure = (TextView)findViewById(R.id.tvPressure);
        tvHumidity = (TextView)findViewById(R.id.tvHumidity);
        tvWindSpeed = (TextView)findViewById(R.id.tvWindSpeed);
        tvWindDeg = (TextView)findViewById(R.id.tvWindDeg);

        ivImage = (ImageView)findViewById(R.id.ivIcon);
        etSearch = findViewById(R.id.etSearch);
        btSearch = findViewById(R.id.btSearch);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inp = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inp.hideSoftInputFromWindow(getCurrentFocus().getRootView().getWindowToken(), 0);
                api_key(String.valueOf(etSearch.getText()));
            }

            private void api_key(final String City) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://api.openweathermap.org/data/2.5/weather?q=" + City + "&appid=5edd67cb421961279ee47a4f6765f7d9&units=metric&lang=RU")
                        .get().build();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try{
                    Response response = client.newCall(request).execute();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String responceData = response.body().string();
                            try {
                                JSONObject json = new JSONObject(responceData);
                                JSONArray array = json.getJSONArray("weather");
                                JSONObject object = array.getJSONObject(0);

                                String description = object.getString("description");
                                String icons = object.getString("icon");

                                JSONObject temp1 = json.getJSONObject("main");
                                Double Temperature = temp1.getDouble("temp");
                                Double feels_like = temp1.getDouble("feels_like");
                                Double temp_min = temp1.getDouble("temp_min");
                                Double temp_max = temp1.getDouble("temp_max");
                                Integer pressure = temp1.getInt("pressure");
                                Integer humidity = temp1.getInt("humidity");

                                JSONObject wind = json.getJSONObject("wind");
                                Double wind_speed = wind.getDouble("speed");
                                Double wind_deg = wind.getDouble("deg");

                                setText(tvTown, City);
                                String temps = Temperature + " °C";
                                setText(tvTemp, temps);
                                setText(tvDesc, description);
                                setText(tvFeel, feels_like + " °C");
                                setText(tvMin, temp_min + " °C");
                                setText(tvMax, temp_max + " °C");
                                setText(tvPressure, pressure + " мм рт. ст.");
                                setText(tvHumidity, humidity + " %");
                                setText(tvWindSpeed, wind_speed + " м/c");
                                setText(tvWindDeg, wind_deg + " °");

                                setImage(ivImage, icons);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        }


                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void setImage(final ImageView ivImage, final String icons) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (icons) {
                            case "01d": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon1));
                                break;
                            case "01n": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon1));
                                break;
                            case "02d": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon2));
                                break;
                            case "02n": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon2));
                                break;
                            case "03d": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon3));
                                break;
                            case "03n": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon3));
                                break;
                            case "04d": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                                break;
                            case "04n": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                                break;
                            case "09d": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                                break;
                            case "09n": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                                break;
                            case "10d": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                                break;
                            case "10n": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                                break;
                            case "11d": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon7));
                                break;
                            case "11n": ivImage.setImageDrawable(getResources().getDrawable(R.drawable.icon7));
                                break;

                            default:
                                ivImage.setImageDrawable(getResources().getDrawable(R.drawable.weather));
                        }
                    }
                });



            }

            private void setText(final TextView tvTown, String city) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTown.setText(city);
                    }
                });
            }
        });
    }



}