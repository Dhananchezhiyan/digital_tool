package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Bind;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    int count = 0 ;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    SharedPreferences pref;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //if(validate()) {
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();
                String[] fields = {"email", "password"};
                String[] value = {email, password};
                // 0 - for private mode
                SharedPreferences.Editor editor1 = pref.edit();
                editor1.putString("ip", "http://impulse.heliohost.org/Digital/login.php");
                editor1.commit();
                HttpCaller http = new HttpCaller();
                http.starter(fields, null, value, null, pref.getString("ip", null).toString());
               // Toast.makeText(getApplication(), http.response, Toast.LENGTH_SHORT).show();
                String[] response = http.response.split("---Segment---");
                if (response[0].indexOf("<Success>") != -1) {
                    String[] basic = response[1].split("<br>")[0].split("<space>");
                    String[] post_all_data = response[2].split("<br>");
                    String[] all_following = response[3].split("<br>");
                    String[] all_followers = response[4].split("<br>");
                    String[] all_det = response[5].split("<br>");
                    String[] user_post = response[6].split("<br>");
                    String[] leaderbord = response[7].split("<br>");
                    SharedPreferences.Editor editor = pref.edit();
                    Log.e("fo", Integer.toString(all_followers.length));
                    String[] all_user_email = new String[all_det.length];
                    String[] all_user_pic = new String[all_det.length];
                    for(int i=0;i<post_all_data.length ;i++)
                    {
                        if(post_all_data[i].equals(""))
                        {
                            continue;
                        }
                        String[] temp = post_all_data[i].split("<space>");
                        editor.putString("post_data"+i , temp[0]);
                        editor.putString("post_by"+i , temp[1]);
                        editor.putString("post_date"+i , temp[2]);
                        ++count;
                    }
                    editor.putString("post_count",count+"");
                    count =0;
                    for(int i=0;i<all_followers.length;i++)
                    {
                        if(all_followers[i].equals(""))
                        {
                            continue;
                        }
                        String[] temp = all_followers[i].split("<space>");
                        editor.putString("followers_id"+i , temp[0]);
                        editor.putString("followers_name"+i , temp[1]);
                        Log.e("follower",temp[1]);
                        ++count;
                    }
                    editor.putString("followers_count",count+"");
                    count = 0;
                    for(int i=0;i<all_following.length ;i++)
                    {
                        if(all_following[i].equals(""))
                        {
                            continue;
                        }
                        String[] temp = all_following[i].split("<space>");
                        editor.putString("following_id"+i , temp[0]);
                        editor.putString("following_name"+i , temp[1]);
                        count++;
                    }
                    editor.putString("following_count",count+"");
                    count = 0;
                    for(int i=0;i<all_det.length ;i++)
                    {
                        if(all_det[i].equals(""))
                        {
                            continue;
                        }
                        String[] temp = all_det[i].split("<space>");
                        Log.e("Check"," ---- "+temp.length);
                        // Toast.makeText(getApplicationContext(),temp.length,Toast.LENGTH_SHORT).show();
                        editor.putString("all_user_id"+i , temp[0]);
                        editor.putString("all_user_name"+i , temp[1]);
                        all_user_email[i] = temp[2];
                        ++count;
                    }
                    editor.putString("all_det_count",count+"");
                    count = 0;
                    for(int i=0;i<user_post.length-1;i++)
                    {
                        if(user_post[i].equals(""))
                        {
                            continue;
                        }
                        String[] temp = user_post[i].split("<space>");
                        editor.putString("user_post_data"+i , temp[0]);
                        editor.putString("user_post_date"+i , temp[1]);
                        editor.putString("user_post_by"+i , temp[2]);
                        ++count;
                    }
                    editor.putString("user_post_count",count+"");
                    count =0;
                    for(int i=0;i<leaderbord.length-1;i++)
                    {
                        if(leaderbord[i].equals(""))
                        {
                            continue;
                        }
                        String[] temp = leaderbord[i].split("<space>");
                        Log.e("temp",leaderbord[i]);
                        editor.putString("leaderbord_id"+i, temp[0]);
                        editor.putString("leaderbord_name"+i, temp[1]);
                        editor.putString("leaderbord_score"+i, temp[2]);
                        editor.putString("leaderbord_content"+i, temp[3]);
                        editor.putString("leaderbord_image"+i,temp[4]);
                        ++count;
                    }
                    editor.putString("leaderbord_count",count+"");
                    String user_id = basic[0];
                    String username = basic[1];
                    String user_email = basic[2];
// SharedPreferences.Editor editor = pref.edit();
//                        String[] val = http.response.split("<br>");
//                        String[] basics = val[0].split(" ");
//                        for (int i = 1; i < val.length; i++) {
//                            String a[] = val[i].split("<space>");
//                            editor1.putString("post" + i, a[0]);
//                            editor1.putString("author" + i, a[1]);
//                            editor1.putString("post_date" + i, a[2]);
//                        }
//                        editor1.putString("post_count", Integer.toString(val.length - 1));
                    editor.putString("user_id", basic[0]);
                    editor.putString("name", basic[1]);
                    editor.putString("email", basic[2]);
                    editor.putString("image", basic[3]);
                    editor.commit();
                    Toast.makeText(getApplication(), http.response
                            , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplication(), Main2Activity.class);
                    startActivity(i);
                    Toast.makeText(getApplication(),
                            http.response, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplication(),
                            http.response, Toast.LENGTH_SHORT).show();
                }

                //}
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }



    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
