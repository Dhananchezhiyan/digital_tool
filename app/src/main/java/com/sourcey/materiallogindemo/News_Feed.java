package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class News_Feed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__feed);
        ListView listView = (ListView) findViewById(R.id.news_feed);

        final SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        HttpCaller http = new HttpCaller();
        String[] fileds = {"user"};
        String[] value = {pref.getString("user_id",null)};
        http.starter(fileds,null,value,null,"http://impulse.heliohost.org/Digital/classifier.php");
        String[] response = http.response.split("<br>");
//        String[] heading = new String[response.length];
//        String[] content = new String[response.length];
//        String[] link = new String[response.length];
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("news_count",response.length+"");
        Log.e("news",http.response);
        for(int i=0;i<response.length;i++)
        {
            String[] temp = response[i].split("<space>");
            editor.putString("heading"+i , temp[0]);
            editor.putString("feedlink"+i , temp[1]);
            editor.putString("feedcontent"+i , temp[2]);
            editor.putString("feedimage"+i,temp[3]);
        }
        editor.commit();
        String heading[] = new String[Integer.parseInt(pref.getString("news_count", null))];
        String content[] = new String[Integer.parseInt(pref.getString("news_count", null))];
        final String link[] = new String[Integer.parseInt(pref.getString("news_count", null))];
        for (int i = 0; i < Integer.parseInt(pref.getString("news_count", null)); i++) {
            heading[i] = pref.getString("heading"+i, null);
            content[i] = pref.getString("content"+i, null);
            link[i] = pref.getString("link"+i, null);
        }
        CustomFeeds adapter = new CustomFeeds(this, heading, content, link);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplication(),webview.class);
                intent.putExtra("url",link[i]);
                startActivity(intent);
            }
        });
    }
}
