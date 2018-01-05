package com.sourcey.materiallogindemo;

/**
 * Created by Ajay on 28-Nov-17.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomPostList extends ArrayAdapter<String>{

    private final Activity Context;
    String post[];
    String post_name[];
    String post_date[];

    public CustomPostList(Activity context, String[] post,String[] post_name,String[] post_date) {

        super(context, R.layout.postitem, post);
        // TODO Auto-generated constructor stub

        this.Context = context;
        this.post = post;
        this.post_name = post_name;
        this.post_date = post_date;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = Context.getLayoutInflater();
        View ListViewSingle = inflater.inflate(R.layout.postitem, null, true);

        TextView posttxt = (TextView) ListViewSingle.findViewById(R.id.post);
        TextView postnametxt = (TextView) ListViewSingle.findViewById(R.id.post_name);
        TextView postdatetxt = (TextView) ListViewSingle.findViewById(R.id.post_date);

        posttxt.setText(post[position]);
        postnametxt.setText(post_name[position]);
        postdatetxt.setText(post_date[position]);
        return ListViewSingle;

    };

}