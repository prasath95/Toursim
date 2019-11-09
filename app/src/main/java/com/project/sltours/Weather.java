
package com.project.sltours;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Weather extends AppCompatActivity {

    EditText cityname;
    Button searchbutton;
    TextView result;


    class Weathersub extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... address) {
            try {
                URL url=new URL(address[0]);
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream is=connection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);


                int data=isr.read();
                String content="";
                char ch;
                while(data!=-1)
                {
                    ch=(char)data;
                    content=content+ch;
                    data=isr.read();
                }
                return content;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    public void search(View view)
    {

        cityname=findViewById(R.id.editText3);
        searchbutton=findViewById(R.id.buttonsearch);
        result=findViewById(R.id.textView4);

        String name=cityname.getText().toString();

        String content;
        Weathersub wsub=new Weathersub();
        try {
            content=wsub.execute("https://openweathermap.org/data/2.5/weather?q="+name+"&appid=b6907d289e10d714a6e88b30761fae22").get();

            JSONObject jsonObject=new JSONObject(content);
            String weatherdata=jsonObject.getString("weather");

            //    Log.i("contentdata",content);

            JSONArray array=new JSONArray(weatherdata);

            String main="";
            String description="";

            for(int i=0;i<array.length();i++)
            {
                JSONObject weatherpart=array.getJSONObject(i);
                main=weatherpart.getString("main");
                description=weatherpart.getString("description");

            result.setText("main : "+main+"\ndesacription : "+description);


                //   Toast.makeText(this, ""+main, Toast.LENGTH_SHORT).show();

            }



        } catch (ExecutionException e) {

            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

    }
}
