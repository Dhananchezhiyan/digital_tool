package com.sourcey.materiallogindemo;

/**
 * Created by Ajay on 27-Nov-17.
 */

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.data;

public class Fragment1 extends android.support.v4.app.Fragment implements Runnable {

    public Fragment1()
    {

    }
    CircleImageView circleImageView;
    SharedPreferences pref;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        TextView textView = (TextView) view.findViewById(R.id.profile_name);
        pref = getActivity().getSharedPreferences("MyPref", 0);
        textView.setText(pref.getString("name", null));
        circleImageView = (CircleImageView) view.findViewById(R.id.profile_image);
        Thread th = new Thread(this);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ListView follower = (ListView) view.findViewById(R.id.follower_profile);
        ListView following = (ListView) view.findViewById(R.id.following_profile);
        //follower.set
        String followerStr[];
        if (Integer.parseInt(pref.getString("followers_count", null)) == 0) {
            followerStr = new String[1];
            followerStr[0] = "you dont have follower";
        } else {
            followerStr = new String[Integer.parseInt(pref.getString("followers_count", null))];
            followerStr[0] = "you dont have follower";
        }
        //followerStr[0]="you dont have follower";
        String followingStr[];
        if (Integer.parseInt(pref.getString("following_count", null)) == 0) {
            followingStr = new String[1];
            followingStr[0] = "you dont have following";
        } else {
             followingStr = new String[Integer.parseInt(pref.getString("following_count", null))];
             followingStr[0] = "you dont have following";
        }
        for(int i = 0 ; i < Integer.parseInt(pref.getString("followers_count",null));i++)
        {
            followerStr[i] = "  "+pref.getString("followers_name"+i,null);
            Log.e("follower",followerStr[i]);
        }
        for(int i = 0 ; i < Integer.parseInt(pref.getString("following_count",null));i++)
        {
            followingStr[i] = " "+pref.getString("following_name"+i,null);
            Log.e("following",followingStr[i]);
        }
        follower.setAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.customtextview, followerStr));
        following.setAdapter(new ArrayAdapter<String>(getActivity(),

                R.layout.customtextview, followingStr));
        return view;
    }

    @Override
    public void run() {
        URL url = null;
        try {

            String rep = pref.getString("image",null).replace(" ","%20");
            Log.e("imjd",rep);
            url = new URL(rep);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            Bitmap myBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
            circleImageView.setImageBitmap(myBitmap);
        }catch (Exception e) {
        }
        }
        //circleImageView.setImageResource(R.drawable.attachment);


}
