package com.example.shell.shellwallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by QuocBao on 20/04/2017.
 */

public class ListViewAdapter extends BaseAdapter{
    Context context;
    List<PieEntry> entries;
    LegendEntry[] legendEntries;
    float total;
    String currency;
    private static LayoutInflater inflater=null;
    int currentDecimal = MainActivity.sharedPref.getInt("currentDecimal",0);
    public ListViewAdapter(Context context, List<PieEntry> entries,LegendEntry[] legendEntries, float total, String currency) {
        this.entries = entries;
        this.legendEntries = legendEntries;
        this.context=context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.total = total;
        this.currency = currency;
    }

    @Override
    public int getCount() {
        return entries.size();
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
        TextView name;
        TextView value;
        ImageView icon;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.listview_legend, null);
            holder.name = (TextView) rowView.findViewById(R.id.cat_name);
            holder.icon = (ImageView) rowView.findViewById(R.id.imageView2);
            holder.value = (TextView) rowView.findViewById(R.id.cat_value);
            String s = entries.get(position).getLabel();
            if (s.length() > 13) s = s.substring(0, 12) + "...";//display long name
            holder.name.setText(s);
            holder.icon.setImageDrawable(entries.get(position).getIcon());
            float value = entries.get(position).getValue();
            holder.value.setText(currencyFormat(value)+" "+currency +"("+percentFormat(value/total*100)+"%)" );
            holder.value.setTextColor(legendEntries[position].formColor);
            holder.name.setTextColor(legendEntries[position].formColor);
            return rowView;
    }
    String percentFormat(float d){
        String s =new DecimalFormat("00.00").format(d);
        if(currentDecimal==1) s = s.replace(".",",");
        return s;
    }
    String currencyFormat(float d){
        String s = new DecimalFormat("###,###,###").format(d);
        if(currentDecimal==1) s = s.replace(",",".");
        return s;
    }
}
