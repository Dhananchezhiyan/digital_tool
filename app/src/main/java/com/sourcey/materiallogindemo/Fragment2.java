package com.sourcey.materiallogindemo;

/**
 * Created by Ajay on 27-Nov-17.
 */

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment2 extends android.support.v4.app.Fragment {

    public Fragment2()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment2, container, false);
        ListView listView = (ListView) view.findViewById(R.id.posts);
        final SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
        final EditText message = (EditText) view.findViewById(R.id.message);
        AppCompatImageButton btnsend = (AppCompatImageButton) view.findViewById(R.id.btn_send);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String post = message.getText().toString();
                if(message.equals("")) {
                    String user_id = pref.getString("user_id", null);
                    String[] fields = {"user", "contents"};
                    String[] value = {user_id, post};
                    HttpCaller http = new HttpCaller();
                    http.starter(fields, null, value, null, "http://192.168.43.229:81/Mumbai/Digital%20Life/post.php");
                    if (http.response.indexOf("<Success>") != -1) {
                        Toast.makeText(getActivity(), "Message Added Succssfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Mistake Mistake Back end team Mistake", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        try {
            String post[] = new String[Integer.parseInt(pref.getString("post_count", null))];
            String post_name[] = new String[Integer.parseInt(pref.getString("post_count", null))];
            String post_date[] = new String[Integer.parseInt(pref.getString("post_count", null))];
            for (int i = 0; i < Integer.parseInt(pref.getString("post_count", null)); i++) {
                post[i] = pref.getString("post_data"+i, null);
                post_name[i] = pref.getString("post_by"+i, null);
                post_date[i] = pref.getString("post_date"+i, null);
            }
            CustomPostList adapter = new
                    CustomPostList(getActivity(), post, post_name, post_date);
            listView.setAdapter(adapter);
        }catch (Exception e)
        {

        }
        return view;
    }
}
