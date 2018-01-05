package com.sourcey.materiallogindemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity
        implements Runnable {
    SharedPreferences pref;
    CircleImageView  circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        pref = getApplication().getSharedPreferences("MyPref", 0);
        circleImageView = (CircleImageView) findViewById(R.id.leaderbord_image);
        TextView textView = (TextView) findViewById(R.id.profile_name);
        ListView leaderbord_list = (ListView) findViewById(R.id.leader_board_list);
        TextView content = (TextView) findViewById(R.id.content);
        content.setText(pref.getString("leaderbord_content0",null));
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_push);
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.title, "LeaderBoard");
        contentView.setTextViewText(R.id.text, pref.getString("leaderbord_name0",null)+"is trader of week");

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContent(contentView);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(1, notification);
         String leaderbordstr[] = new String[Integer.parseInt(pref.getString("leaderbord_count",null))-1];
        for(int i = 1;i < Integer.parseInt(pref.getString("leaderbord_count",null));i++)
        {
            leaderbordstr[i-1] = pref.getString("leaderbord_name"+i,null);
            Log.e("leadelist",leaderbordstr[i-1]);
        }
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("9543387247", null, "SMS", null, null);
        textView.setText(pref.getString("leaderbord_name0",null));
        leaderbord_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                R.layout.customtextview, leaderbordstr));
        Thread th = new Thread(this);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.leader_board) {
                    Intent i = new Intent(getApplicationContext(),Main2Activity.class);
                    startActivity(i);
                } else if (id == R.id.social) {
                    Intent i = new Intent(getApplicationContext(),SocialMedia.class);
                    startActivity(i);
                } else if (id == R.id.chatbot) {
                    Intent i = new Intent(getApplicationContext(),ChatBot.class);
                    startActivity(i);
                } else if (id == R.id.chat) {
                    Intent i = new Intent(getApplicationContext(),Group_chat_Activity.class);
                    startActivity(i);
                } else if (id == R.id.feed_news) {

                    Intent intent= new Intent(getApplication(),News_Feed.class);
                    startActivity(intent);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {
        URL url = null;
        try {

            String rep = pref.getString("leaderbord_image0",null).replace(" ","%20");
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
}
