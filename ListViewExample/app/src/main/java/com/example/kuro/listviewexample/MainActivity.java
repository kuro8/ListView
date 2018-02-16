package com.example.kuro.listviewexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //simple list
    ArrayAdapter<String> adapter;
    ListView listView;

    //custom list
    ArrayList<MyItem>arrayListCustom;
    MyAdapter adapterCustom;
    ListView listView2;

    Button b1;
    int itemToRemove = -1;
    int counter = 0;
    int[] images = {R.color.colorAccent, R.color.colorPrimary, R.mipmap.ic_launcher};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Simple List
        listView = (ListView) findViewById(R.id.listDefault);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(adapter);

        //Custom List
        listView2 = (ListView) findViewById(R.id.listCustom);
        arrayListCustom = new ArrayList<MyItem>();
        adapterCustom = new MyAdapter(this, R.layout.custom_item_list, arrayListCustom);
        listView2.setAdapter(adapterCustom);


        //simple click item function
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });
        listView2.setOnItemClickListener(new MyItemClicked());

        //long click item function
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                itemToRemove = position;
                b1.setEnabled(true);
                return true;
            }
        });
        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemToRemove = position;
                b1.setEnabled(true);
                return true;
            }
        });

        //add item function
        Button b = (Button) findViewById(R.id.buttonAddItem);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                int position = counter%images.length;
                MyItem item = new MyItem(images[position], "custom item " + counter);

                //add directly in the adapter
                adapter.add("simple item" + counter);
                adapter.notifyDataSetChanged(); //do not forget this to refresh the view

                //or add in the array attached in the adapter
                arrayListCustom.add(item);
                adapterCustom.notifyDataSetChanged();
            }
        });

        //remove item function
        b1 = (Button) findViewById(R.id.buttonRemoveItem);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemToRemove != -1){
                    //simple list
                    adapter.remove(adapter.getItem(itemToRemove));
                    adapter.notifyDataSetChanged();

                    //custom list
                    arrayListCustom.remove(itemToRemove);
                    adapterCustom.notifyDataSetChanged();

                    itemToRemove = -1;
                    b1.setEnabled(false);
                }
            }
        });

    }


    class MyItemClicked implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), arrayListCustom.get(position).title, Toast.LENGTH_SHORT).show();
        }
    }

    class MyItem{
        int icon;
        String title;

        MyItem(int icon, String title){
            this.icon = icon;
            this.title = title;
        }
    }

    //customized adapter for listView
    private class MyAdapter extends ArrayAdapter<MyItem>{
        Context context;
        int layoutResourceId;
        ArrayList<MyItem> data;

        MyAdapter(Context context, int layoutResourceId, ArrayList<MyItem> data){
            super(context, layoutResourceId, data);
            this.context = context;
            this.data = data;
            this.layoutResourceId = layoutResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = getLayoutInflater().inflate(layoutResourceId, parent, false);
            }

            ((ImageView)convertView.findViewById(R.id.imageView)).setImageResource(data.get(position).icon);
            ((TextView)convertView.findViewById(R.id.text_title)).setText(data.get(position).title);

            return convertView;
        }
    }
}
