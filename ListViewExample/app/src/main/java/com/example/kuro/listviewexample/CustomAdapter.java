package com.example.kuro.listviewexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    private Context context;
    private int layoutId;
    private ArrayList<MyItem> data;

    CustomAdapter(Context context, int layoutId, ArrayList<MyItem> data){
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layoutId, parent, false);
        }

        ((ImageView)convertView.findViewById(R.id.imageView)).setImageResource(data.get(position).icon);
        ((TextView)convertView.findViewById(R.id.text_title)).setText(data.get(position).text);

        //highlight selected item
        ListView list = (ListView)parent;
        if(list.isItemChecked(position)){
        }
        else{

        }

        return convertView;
    }
}
