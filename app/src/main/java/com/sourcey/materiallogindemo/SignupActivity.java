package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;
    @Bind(R.id.upload) CircleImageView imageView;
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    String uristring;
    String path,format;
    Uri uri;
    SharedPreferences pref;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = _nameText.getText().toString();
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();
                String reEnterPassword = _reEnterPasswordText.getText().toString();
                imageView.buildDrawingCache();
                Bitmap bmap = imageView.getDrawingCache();
                imageView.setImageBitmap(bmap);
                Intent i = new Intent(getApplication(),starting_info.class);
                i.putExtra("name",name);
                i.putExtra("email",email);
                i.putExtra("password",password);
                i.putExtra("uri",uristring);
                //i.putExtra("image",)
                startActivity(i);
//                String[] values = {name,email,password};
//                String fields[] = {"Name","Email","Password"};
//                HttpCaller http = new HttpCaller();
//
//                http.starter(fields,bitmap,values,".jpg","http://http://impulse.heliohost.org/Digital/account_creation.php");
//                if(http.response.indexOf("<Success>")!=-1)
//                {
//                    String fields1[] = {"email","password"};
//                    String value[] = {email,password};
//                    http.starter(fields,null,value,null,pref.getString("ip",null).toString());
//
//                    if(http.response.indexOf("<Success>")!=-1)
//                    {
//                        SharedPreferences.Editor editor = pref.edit();
//                        String[] val = http.response.split("<br>");
//                        String[] basics = val[0].split(" ");
//                        for(int i=1;i<val.length;i++)
//                        {
//                            String a[] = val[i].split("<space>");
//                            editor.putString("post"+i,a[0]);
//                            editor.putString("author"+i,a[1]);
//                            editor.putString("post_date"+i,a[2]);
//                        }
//                        editor.putInt("post_count",val.length-1);
//                        editor.putString("user_id",basics[1]);
//                        editor.putString("name",basics[2]);
//                        editor.putString("email",basics[3]);
//                        editor.putString("image",basics[4]);
//                        editor.commit();
//                        Toast.makeText(getApplication(), pref.getString("user_id",null), Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getApplication(),Main2Activity.class);
//                        startActivity(i);
//
//                    }
//
//                } else
//                {
//                    Toast.makeText(getApplication(),
//                            "Mistake Mistake  team mistake",Toast.LENGTH_SHORT).show();
//                }

            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            uristring = uri.toString();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
                imageView.setImageBitmap(circularBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }
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

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}