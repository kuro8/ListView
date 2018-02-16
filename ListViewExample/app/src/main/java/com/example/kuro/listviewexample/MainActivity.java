package com.example.kuro.listviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //simple list
    ArrayAdapter<String> adapter;
    ListView listView;

    //custom list
    ArrayList<MyItem>arrayListCustom;
    CustomAdapter adapterCustom;
    ListView listView2;

    Button buttonRemove;
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
        adapterCustom = new CustomAdapter(this, R.layout.custom_item_list, arrayListCustom);
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
                buttonRemove.setEnabled(true);
                return true;
            }
        });
        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemToRemove = position;
                buttonRemove.setEnabled(true);
                return true;
            }
        });

        //add item function
        Button buttonAdd = (Button) findViewById(R.id.buttonAddItem);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                int position = counter%images.length;
                MyItem item = new MyItem(images[position], "custom item " + counter);

                //add directly in the adapter
                adapter.add("simple item" + counter);
                adapter.notifyDataSetChanged(); //refresh the view

                //or add in the array attached in the adapter
                arrayListCustom.add(item);
                adapterCustom.notifyDataSetChanged();
            }
        });

        //remove item function
        buttonRemove = (Button) findViewById(R.id.buttonRemoveItem);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
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
                    buttonRemove.setEnabled(false);
                }
            }
        });

    }


    class MyItemClicked implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), arrayListCustom.get(position).text, Toast.LENGTH_SHORT).show();
        }
    }
}
