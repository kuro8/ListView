# ListView

This project explain how to add and remove items from ListView, using the default and the custom layout.

The _activity_main.xml_ have buttons to create and remove selected items, and two ListViews for the layouts.

#### Buttons Function: ####
* create item: create a new item in each list and refresh the view.
* remove item: remove selected items from the list and disable the remove button.
#### List items Function: ####
* Simple click: display message with the item information;
* Long click: select item from list and enable the remove button.
  * in the simple list only one item can be selected
  * in the custom list multiple items can be selected

_custom_item_list.xml_, which is the custom item layout, shows an image and text.

## Simple List

To setup the listView, the following lines are important.
```java
ListView listView = (ListView) findViewById(R.id.listDefault);
ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
listView.setAdapter(adapter);
```
The ArrayAdapter constructor is created with Context, layout id and a ArrayList<T> parameters, in which the last the lists elements are placed.

By default, `android.R.layout.simple_list_item_1` is a simple layout in which display a single text.

Instead of an `arrayList` variable created and appended to the constructor,`new ArrayList<String>()`can be appended directly. In this case, only the adapter can manage the items.

Then finally the listView need to bind the adapter.
```java
listView.setAdapter(adapter);
```

When the ListView has its elements modified (not applied when a item is selected), the view needs to be refreshed by calling this method:
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
        //do something
    }
});
```
which the `position` variable is the position of the list in the view.
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

### Select one item
To be able to select an item in the ListView needs to be set the selection mode and its color ().
```java
listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
listView.setSelector(android.R.color.holo_red_light); //setup the color when selected
```
Then when an item is clicked to able the select function add this line:
```java
simpleListView.setSelected(true);
```

For some reason, disable the select function is not working to return to the click simple. When an item is clicked, it continues highlighted with the same color setted in the selector. The same happens if the choice mode is changed to CHOICE_MODE_NONE.
For now, change the color to the previous one and calling `listView.clearFocus()` always when the highlight need to be disable works, but it is not beautiful. Need to find a better way.

### Select multiple items (still working)

## Custom List

For this example a custom layout item has an image and a text. The _MyItem_ class will be responsible for storing these informations to put in the view.
```java
public class MyItem{
        int icon;
        String title;

        MyItem(int icon, String title){
            this.icon = icon;
            this.title = title;
        }
    }
```
The _CustomAdapter_ class is our custom adapter, which extends BaseAdapter. The BaseAdapter has the method _getView()_ that takes care of the process of putting the information in their respective places in the layout.

```java
public class CustomAdapter extends BaseAdapter {
    private Context context;
    private int layoutId;
    private ArrayList<MyItem> data;

    CustomAdapter(Context context, int layoutId, ArrayList<MyItem> data){
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
    }
    
    ...

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layoutId, parent, false);
        }

        ((ImageView)convertView.findViewById(R.id.imageView)).setImageResource(data.get(position).icon);
        ((TextView)convertView.findViewById(R.id.text_title)).setText(data.get(position).text);

        return convertView;
    }
}
```
