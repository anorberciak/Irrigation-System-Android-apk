package com.example.anorb.irrigationsystemv2;

/**
 * Created by anorb on 05.12.2018.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TurnOnPump extends Activity {

    private TextView text_pump;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pump_on);

        text_pump = findViewById(R.id.text_pump);

        OkHttpClient client = new OkHttpClient();

        String url1 = "http://192.168.43.215:9000/turnOn";

        Request request = new Request.Builder()
                .url(url1)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();

                    final String pump = "Pump is working";


                    TurnOnPump.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text_pump.setText(pump);

                        }
                    });
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

    }

}
