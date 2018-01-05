package com.sourcey.materiallogindemo;

/**
 * Created by Ajay on 28-Nov-17.
 */
import java.util.ArrayList;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomFriendList extends BaseAdapter implements Filterable{
    Context c;
    ArrayList<Friend> Friends;
    CustomFilter filter;
    ArrayList<Friend> filterList;
    String id;
    public CustomFriendList(Context ctx,ArrayList<Friend> Friends,String id) {
        // TODO Auto-generated constructor stub
        this.c=ctx;
        this.Friends=Friends;
        this.filterList=Friends;
        this.id = id;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Friends.size();
    }
    @Override
    public Object getItem(int pos) {
        // TODO Auto-generated method stub
        return Friends.get(pos);
    }
    @Override
    public long getItemId(int pos) {
        // TODO Auto-generated method stub
        return Friends.indexOf(getItem(pos));
    }
    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.frienditemlist, null);
        }
        TextView nameTxt=(TextView) convertView.findViewById(R.id.friendname);
        final ImageButton btn = (ImageButton) convertView.findViewById(R.id.friendadd);
        //SET DATA TO THEM
        nameTxt.setText(Friends.get(pos).getName());
        if(filterList.get(pos).getFyn().equals("yes"))
        {
            btn.setImageResource(R.drawable.tickic);
            btn.setEnabled(false);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setImageResource(R.drawable.tickic);
                btn.setEnabled(false);
                Log.e("use-------id",id+"---"+filterList.get(pos).get_id());
                String[] values = {(String) id,""+ filterList.get(pos).get_id()};
                String[] field = {"current","follow"};
                HttpCaller http = new HttpCaller();
                http.starter(field,null,values,null,"http://impulse.heliohost.org/Digital/friends.php");
                if(http.response.indexOf("<Success>")!= -1)
                {
                    Log.e("Success","Success");
                } else
                {
                    Log.e("Failure",http.response);
                }
            }
        });
        return convertView;
    }
    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter=new CustomFilter();
        }

        return filter;
    }
    //INNER CLASS
    class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
            FilterResults results=new FilterResults();
            if(constraint != null && constraint.length()>0)
            {
                //CONSTARINT TO UPPER
                constraint=constraint.toString().toUpperCase();
                ArrayList<Friend> filters=new ArrayList<Friend>();
                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getName().toUpperCase().contains(constraint))
                    {
                        Friend p=new Friend(filterList.get(i).getName(), filterList.get(i).get_id(),filters.get(i).getFyn());
                        filters.add(p);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }else
            {
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub
            Friends=(ArrayList<Friend>) results.values;
            notifyDataSetChanged();
        }
    }
}