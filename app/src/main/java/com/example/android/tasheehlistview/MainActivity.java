package com.example.android.tasheehlistview;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context;
    Button click;
    String data="";
    public  ListView dataList;
    public  EditText keyword;
    String key  ="";
    final ArrayList<Ayah> words = new ArrayList<Ayah>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = (Button) findViewById(R.id.button);
        dataList =(ListView) findViewById(R.id.list);
        keyword = (EditText) findViewById(R.id.edit_text_view);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = keyword.getText().toString();
                key = "http://api.alquran.cloud/v1/search/"+word+"/all/en";
                new FetchData().execute();

            }
        });

    }
    public class FetchData extends AsyncTask<String,Void,ArrayList>{
        @Override
        protected void onPostExecute(ArrayList w) {
            //super.onPostExecute(w);
            WordAdapter adapter = new WordAdapter(MainActivity.this,words);
            dataList = (ListView) findViewById(R.id.list);
            dataList.setAdapter(adapter);

        }

        @Override
        protected ArrayList doInBackground(String... params) {

            try {
                URL url = new URL(key);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while(line != null){
                    line = bufferedReader.readLine();
                    data = data + line;
                }

                JSONObject JO1 = new JSONObject(data);
                JSONObject JO2 = JO1.getJSONObject("data");
                int counts = JO2.getInt("count");
                JSONArray JA = JO2.getJSONArray("matches");
                for (int i = 0; i < JA.length(); i++) {
                    JSONObject JO = (JSONObject) JA.get(i);
                    JSONObject JOT = JO.getJSONObject("surah");

                    String surahName = (String)JOT.get("englishName");
                    int ayahNo = (int)JO.get("numberInSurah");
                    String ayahText = (String)JO.get("text");

                    words.add(new Ayah(surahName,ayahText,ayahNo));

                }




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
