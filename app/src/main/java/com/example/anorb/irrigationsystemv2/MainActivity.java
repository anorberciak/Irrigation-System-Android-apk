package com.example.anorb.irrigationsystemv2;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private TextView text_value_temp;
    private TextView text_value_hum;
    private TextView text_value_mois;
    private TextView text_value_water;
    SwipeRefreshLayout swipeRefreshLayout;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout  = (SwipeRefreshLayout) findViewById(R.id.Swipe);

        text_value_temp= findViewById(R.id.text_value1);
        text_value_hum= findViewById(R.id.text_value2);
        text_value_mois= findViewById(R.id.text_value3);
        text_value_water= findViewById(R.id.text_value4);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                OkHttpClient client = new OkHttpClient();

                String url1 = "http://192.168.43.215:9000/android";

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

                            String cutString1[] = null;
                            String cutString2[] = null;
                            String paramStringVector[] = null;
                            cutString1 = myResponse.split("=",2);
                            String myResponse2 = cutString1[1];
                            cutString2 = myResponse2.split("=",2);
                            String myResponse3 = cutString2[0];
                            paramStringVector = myResponse3.split("&");
                            final String temp = paramStringVector[1];
                            final String hum = paramStringVector[3];
                            final String mois = paramStringVector[5];
                            final String water = paramStringVector[7];


                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    text_value_temp.setText(temp);
                                    text_value_hum.setText(hum);
                                    text_value_mois.setText(mois);
                                    text_value_water.setText(water);
                                }
                            });
                        }
                    }
                });

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });

        button = (Button) findViewById(R.id.button_pump);
        button.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0){
                Intent intent = new Intent(context,TurnOnPump.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishActivity(this.hashCode());
            }
        });


    }
}