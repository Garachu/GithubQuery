package com.meg.githubquery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 * Created by meg on 9/24/17.
 */


/**
 * Adapter to populate the RecyclerView with values
 * Adapter main Roles
 * 1. Create new items i.e ViewHolders
 * 2. Populate the items with data
 * 3. Return information
 * */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.NumberViewHolder>{

    private static final String TAG = MainAdapter.class.getSimpleName();

    private int numberItems;

    /**
     * Constructor that accept a number of items to display
     * */
    public MainAdapter(int numberItems) {
        this.numberItems = numberItems;
    }

    /**
     * Called when RecyclerView instantiates a new ViewHolder instance
     * Responsible for creating the views, either by inflating the item views from XML OR creating them in code
     * Returns a new ViewHolder object
     */
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //Get the context
        Context context = viewGroup.getContext();

        //Get layoutId
        int layoutIdForListItem = R.layout.number_list_item;

        //Get LayoutInflater object
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        /**
         * Inflate the Item Layout
         * The LayoutInflater's inflate method explained
         * It's first parameter is ID of a layout in XML
         * It inflates or converts this into a collection of ViewGroups and views that represent it in java code
         * The last parameter the inflate method takes is set to false, so that the inflated layout isn't immediately attached to its parent ViewGroup
         * */
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        //Create NumberViewHolder with the inflated View
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        //Return the ViewHolder
        return viewHolder;
    }

    /**
     * Called when the RecyclerView wants to populate the view with data from our model so that the user can see it effectively binding it to the data source.
     * RecyclerView call onBindViewHolder after ViewHolder is created to populate item with data.
     * When you scroll the RecyclerView will reuse those ViewHolders asking the adapter to bind new data to them.
     * */
    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        holder.bind(position);
    }


    /**
     * Returns the number of items in our data source*/
    @Override
    public int getItemCount() {

        return numberItems;
    }

    //Create a class called NumberViewHolder that extends RecyclerView.ViewHolder
    //Add RecyclerView 2 the Layout
    //Craft Items that will be displayed in the RecyclerView
    //Create an object "ViewHolder" that will store references to the item displayed on RecyclerView
    class NumberViewHolder extends RecyclerView.ViewHolder{

        TextView listItemNumberView;

        public NumberViewHolder(View itemView) {

            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
        }

        void bind(int listIndex){

            //If we pass in an integer Android assumes we're passing a string resource from strings.xml to display
            listItemNumberView.setText(String.valueOf(listIndex));
        }
    }

}
