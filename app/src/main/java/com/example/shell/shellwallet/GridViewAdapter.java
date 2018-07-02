package com.example.shell.shellwallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by QuocBao on 04/04/2017.
 */

public class GridViewAdapter extends BaseAdapter {
    public final int NO_NAME_TYPE = 1;
    Context context;
    ArrayList<Category> list;
    int selectedPosition;
    int type = 0;
    private static LayoutInflater inflater=null;
    public GridViewAdapter(Context context, ArrayList<Category> list) {
        this.list = list;
        this.context=context;
        selectedPosition = -1;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public GridViewAdapter(Context context, ArrayList<Category> list, int type) {
        this.list = list;
        this.context=context;
        selectedPosition = -1;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.type = type;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    public void setSelectedPosition(int position){
        selectedPosition = position;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    public class HolderNoName{
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(type == NO_NAME_TYPE){
            HolderNoName holder = new HolderNoName();
            View rowView;
            rowView = inflater.inflate(R.layout.category_grid_view_noname, null);
            holder.img = (ImageView) rowView.findViewById(R.id.categoryImage_noname);
            holder.img.setImageResource(list.get(position).getIcon());
            if (position == selectedPosition)
                rowView.setBackground(context.getDrawable(R.drawable.custom_ripple_color));
            return rowView;
        }
        else {
            Holder holder = new Holder();
            View rowView;

            rowView = inflater.inflate(R.layout.category_grid_view, null);
            holder.tv = (TextView) rowView.findViewById(R.id.categoryTitle);
            holder.img = (ImageView) rowView.findViewById(R.id.categoryImage);
            String s = list.get(position).getName();
            if (s.length() > 13) s = s.substring(0, 12) + "...";//display long name
            holder.tv.setText(s);
            holder.img.setImageResource(list.get(position).getIcon());
            if (position == selectedPosition)
                rowView.setBackground(context.getDrawable(R.drawable.custom_ripple_color));
            return rowView;
        }
    }
}