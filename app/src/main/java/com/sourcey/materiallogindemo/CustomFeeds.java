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

public class CustomFeeds extends ArrayAdapter<String>{

    private final Activity Context;
    String heading[];
    String content[];
    String link[];

    public CustomFeeds(Activity context, String[] heading,String[] content,String[] link) {

        super(context, R.layout.postitem, heading);
        // TODO Auto-generated constructor stub

        this.Context = context;
        this.heading = heading;
        this.content = content;
        this.link = link;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = Context.getLayoutInflater();
        View ListViewSingle = inflater.inflate(R.layout.postitem, null, true);

        TextView headingtxt = (TextView) ListViewSingle.findViewById(R.id.post_name);
       // TextView headingnametxt = (TextView) ListViewSingle.findViewById(R.id.c);
        TextView headingdatetxt = (TextView) ListViewSingle.findViewById(R.id.post);

        headingtxt.setText(heading[position]);
       // headingnametxt.setText(content[position]);
        headingdatetxt.setText(content[position]);
        return ListViewSingle;

    };

}