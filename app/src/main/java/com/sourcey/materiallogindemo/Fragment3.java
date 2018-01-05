package com.sourcey.materiallogindemo;

/**
 * Created by Ajay on 27-Nov-17.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Fragment3 extends Fragment {

    public Fragment3()
    {

    }
    private SearchView mSearchView;
    private ListView mListView;
    CustomFriendList adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        mSearchView = (SearchView) view.findViewById(R.id.search);
        mListView = (ListView) view.findViewById(R.id.searchlist);
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
        String username[] = new String[Integer.parseInt(pref.getString("all_det_count", null))];
        String userid[] = new String[Integer.parseInt(pref.getString("all_det_count", null))];
        String fyn[] = new String[Integer.parseInt(pref.getString("all_det_count", null))];
        String follower ="";
        for(int i=0;i<Integer.parseInt(pref.getString("following_count",null));i++)
        {
            follower = follower+pref.getString("following_name"+i,null);
        }
        Log.e("follower list",follower);
        for (int i = 0; i < Integer.parseInt(pref.getString("all_det_count", null)); i++) {
            if(pref.getString("all_user_name" + i, null).equals(""))
            {
                continue;
            }

            username[i] = pref.getString("all_user_name" + i, null);
            Log.e("username",username[i]);
            Log.e("hfjh",follower.indexOf(username[i])+"");
            if(follower.indexOf(username[i])>-1)
            {
                fyn[i] =  "yes";
            }else
            {
                fyn[i] = "no";
            }
            userid[i] = pref.getString("all_user_id" + i, null);
        }
        ArrayList<Friend> Friends = new ArrayList<Friend>();
        Friend p;
        for (int i = 0; i < username.length; i++) {
            p = new Friend(username[i], Integer.parseInt(userid[i]),fyn[i]);
            Friends.add(p);
        }
        final CustomFriendList adapter = new CustomFriendList(getActivity(), Friends,pref.getString("user_id",null));
        mListView.setAdapter(adapter);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String arg0) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // TODO Auto-generated method stub
                adapter.getFilter().filter(query);
                return false;
            }

        });
        return view;
    }

}