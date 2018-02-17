package com.example.kuro.listviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //simple list
    ArrayAdapter<String> adapter;
    ListView simpleListView;

    //custom list
    ArrayList<MyItem>arrayListCustom;
    CustomAdapter adapterCustom;
    ListView customListView;

    Button buttonRemove;
    int positionRemove = -1;
    int counter = 0;
    int[] images = {android.R.drawable.ic_lock_silent_mode, android.R.drawable.ic_dialog_email, android.R.drawable.ic_dialog_alert, android.R.drawable.ic_delete};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Simple List
        simpleListView = (ListView) findViewById(R.id.listDefault);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        simpleListView.setAdapter(adapter);
        simpleListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        //Custom List
        customListView = (ListView) findViewById(R.id.listCustom);
        arrayListCustom = new ArrayList<MyItem>();
        adapterCustom = new CustomAdapter(this, R.layout.custom_item_list, arrayListCustom);
        customListView.setAdapter(adapterCustom);


        //simple click item function
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(positionRemove != -1){
                    if(positionRemove == position){
                        positionRemove = -1;
                        simpleListView.setSelected(false);
                        simpleListView.clearFocus();
                        simpleListView.setSelector(android.R.color.holo_blue_light);
                        buttonRemove.setEnabled(false);
                    }
                    else{
                        positionRemove = position;
                    }
                }
                else{
                    simpleListView.clearFocus();
                    Toast.makeText(getApplicationContext(), adapter.getItem(position), Toast.LENGTH_SHORT).show();
                }
            }
        });
        customListView.setOnItemClickListener(new MyItemClicked());

        //long click item function
        simpleListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(positionRemove != -1)
                    return true;
                simpleListView.setSelector(android.R.color.holo_red_light);
                simpleListView.setSelected(true);
                positionRemove = position;
                buttonRemove.setEnabled(true);
                return true;
            }
        });
        customListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                customListView.setItemChecked(position, true);
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
                MyItem newItem = new MyItem(images[position], "custom item " + counter);

                //add directly in the adapter
                adapter.add("simple item " + counter);
                adapter.notifyDataSetChanged(); //refresh the view

                //or add in the array attached in the arrayList
                arrayListCustom.add(newItem);
                adapterCustom.notifyDataSetChanged();
            }
        });

        //remove item function
        buttonRemove = (Button) findViewById(R.id.buttonRemoveItem);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //simple list
                if(simpleListView.getCheckedItemCount() > 0){
                    simpleListView.clearFocus();
                    adapter.remove(adapter.getItem(positionRemove));
                    positionRemove = -1;
                    adapter.notifyDataSetChanged();
                }
                //custom list
                if(customListView.getCheckedItemCount() > 0){
                    SparseBooleanArray selected = customListView.getCheckedItemPositions();
                    for(int i=0; i<selected.size(); i++){
                        arrayListCustom.remove(selected.keyAt(i));
                    }
                    adapterCustom.notifyDataSetChanged();
                }
                buttonRemove.setEnabled(false);
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
