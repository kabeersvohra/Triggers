package com.biovoso.Triggers;

/**
 * Created by mmanghnani on 07/02/15.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class navAdapter extends RecyclerView.Adapter<navAdapter.ViewHolder>
{

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        int Holderid;

        TextView textView;
        ImageView imageView;
        LinearLayout rowView;
        TextView Name;


        public ViewHolder(View itemView,int ViewType)
        {
            super(itemView);

            if(ViewType == TYPE_ITEM)
            {
                textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
                rowView = (LinearLayout) itemView.findViewById(R.id.row); // Creating LinearLayout object with the entire row
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            }
            else
            {
                Name = (TextView) itemView.findViewById(R.id.name);         // Creating Text View object from header.xml for name
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }
        }


    }



    navAdapter(String Titles[], Context context) { mNavTitles = Titles; this.context = context; }



    //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
    // if the viewType is TYPE_HEADER
    // and pass it to the view holder

    @Override
    public navAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        if (viewType == TYPE_ITEM)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_item,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

            //inflate your layout and pass it to view holder

        }
        else if (viewType == TYPE_HEADER)
        {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_header,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created


        }
        return null;

    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(navAdapter.ViewHolder holder, final int position) {
        if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.textView.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
            holder.rowView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(context instanceof HomeActivity){
                                ((HomeActivity)context).getSupportActionBar().setTitle(mNavTitles[position - 1]);
                            }
                        }
                    }
            );
        }
        else{

            //holder.profile.setImageResource(profile);           // Similarly we set the resources for header view
            //holder.Name.setText(name);
            //holder.email.setText(email);
        }
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return mNavTitles.length+1; // the number of items in the list will be +1 the titles including the header view.
    }


    // With the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
