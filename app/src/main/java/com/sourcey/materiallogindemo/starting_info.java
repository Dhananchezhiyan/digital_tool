package com.sourcey.materiallogindemo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.net.URI;

/**
 * Created by Ajay on 02-Dec-17.
 */

public class starting_info extends AppCompatActivity {
    String[] trader_prof = { "Select the trader","Beginner","Intermediate","Advanced" };
    String[] trader_type = { "Select the trader","Daily","Monthly" };
    String traderstr,tradertypestr;
    Spinner spin1,spin2;
    EditText editText;
    Button submit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_info);
         spin1 = (Spinner) findViewById(R.id.spinner1);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,trader_prof);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin1.setAdapter(aa);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                traderstr = trader_prof[i];
              }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        editText = (EditText) findViewById(R.id.trader_amount);
        spin2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter aa1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,trader_type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin2.setAdapter(aa1);
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tradertypestr = trader_type[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String interest = editText.getText().toString();
                String name = getIntent().getStringExtra("name");
                String email = getIntent().getStringExtra("email");
                String password = getIntent().getStringExtra("password");
                Uri uri = Uri.parse(getIntent().getStringExtra("uri"));
                ImageView imageView = (ImageView) findViewById(R.id.test);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String fields[] = {"Name","Email","Password","level","interest","trader"};
                String values[] = {name,email,password,traderstr,interest,tradertypestr};
                HttpCaller http = new HttpCaller();
                http.starter(fields,bitmap,values,".png","http://impulse.heliohost.org/Digital/account_creation.php");
                if(http.response.indexOf("<Success>") != -1)
                {
                    Toast.makeText(starting_info.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
