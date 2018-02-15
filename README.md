# ListView with ArrayAdapter

This project explain how to add and remove items from ListView, using the default and the custom layout.

The _activity_main.xml_ have buttons to create and remove selected items, and two ListViews for the layouts.

#### Buttons Function: ####
* create item: create a new item in each list and refresh the view.
* remove item: remove selected items from the list and disable the remove button.
#### List items Function: ####
* Simple click: display message with the item information;
* Long click: select item from list and enable the remove button.

_custom_item_list.xml_, which is the custom item layout, shows an image and text.

Functions are implemented in the _MainActivity.java_

## Simple List

To setup the listView, the following lines are important.
```java
ListView listView = (ListView) findViewById(R.id.listDefault);
ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
listView.setAdapter(adapter);
```
The ArrayAdapter constructor is created with Context, layout id and a ArrayList<T> parameters, in which the elements of the list are placed.

By default, `android.R.layout.simple_list_item_1` is a simple layout in which is a single text.

Instead of an `arrayList` variable created and appended to the constructor,`new ArrayList<String>()`can be appended directly. In this case, only the adapter can manage the items

Then finally the listView need to bind the adapter.
```java
listView.setAdapter(adapter);
```

When the ListView has its elements modified, the view needs to be updated by calling this method:
```java
adapter.notifyDataSetChanged();
```

## Interact with an item from the list
### One click
This method is called when the ListView is clicked

```java
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(arrayList.get(position));
    }
});
```
which the variable `position` is the position of the view in the list.
Or you can create a new class implementing the _AdapterView.OnItemClickListener_:

```java
listView.setOnItemClickListener(new MyItemClicked());
```
Then:
```java
class MyItemClicked implements AdapterView.OnItemClickListener{
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //do something
    }
}
```

### Long click
```java
listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //do something
        return true;
    }
});
```
Which the value returned must be true if the callback consumed the long click (then the _OnItemClick_ method does not run automatically after the _OnItemLongClick_ executes when the finger is released).
And like in the _OnItemClickListener_, you can create a class implementing the _AdapterView.OnItemLongClickListener_.

### Select item (stay highlighted)


## Custom List

For this example a custom layout item has an image and a text. The _MyItem_ class will be responsible for storing these informations to put in the view.
```java
class MyItem{
        int icon;
        String title;

        MyItem(int icon, String title){
            this.icon = icon;
            this.title = title;
        }
    }
```
The _MyAdapter_ class is our custom adapter, need to extend the ArrayAdapter, which extends BaseAdapter. The BaseAdapter has the method _getView()_ that takes care of the process of putting the information in their respective places in the layout.

```java
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
```

A constructor was defined with the same parameters as when we created an ArrayAdapter for the simple layout to keep the pattern.
