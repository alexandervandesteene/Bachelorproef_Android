package com.example.alexandervandesteene.testapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alexandervandesteene on 22/04/16.
 */
public class ListViewAdapter extends ArrayAdapter{
    Activity context;
    private List<User> userList;
    private int resource;
    private LayoutInflater inflater;

    public ListViewAdapter(Activity context, int resource, List<User> objtects) {
        super(context,resource,objtects);
        this.userList = objtects;
        this.resource = resource;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(resource, null);
        }

        TextView txtFirstname,txtLastname;
        txtFirstname = (TextView) convertView.findViewById(R.id.txtRowFistname);
        txtLastname = (TextView) convertView.findViewById(R.id.txtRowLastname);

        txtFirstname.setText(userList.get(position).getFirst_name());
        txtLastname.setText(userList.get(position).getLast_name());

        return convertView;
    }
}
