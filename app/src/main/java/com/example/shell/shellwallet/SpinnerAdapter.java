package com.example.shell.shellwallet;

/**
 * Created by HoangLoc on 18/04/2017.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    int imgs[];
    String[] text;
    LayoutInflater inflater;
    List<SettingsFragment.MyTheme> themes;
    public final static int THEME = 1;
    int type = 0;

    public SpinnerAdapter(Context applicationContext, int[] images, String[] text) {
        this.context = applicationContext;
        this.imgs = images;
        this.text = text;
        inflater = (LayoutInflater.from(applicationContext));
    }
    public SpinnerAdapter(Context applicationContext, List<SettingsFragment.MyTheme> themes) {
        this.context = applicationContext;
        this.themes = themes;
        inflater = (LayoutInflater.from(applicationContext));
        this.type = THEME;
    }

    @Override
    public int getCount() {
        if(type==THEME) return themes.size();
        else return text.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        if (type==THEME) return themes.get(i).id;
        else return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_language_spinner, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);

        if(type==THEME){
            names.setText(themes.get(i).name);
            icon.setImageResource(R.drawable.ic_theme);
            icon.setColorFilter(ContextCompat.getColor(context, themes.get(i).color));
        }
        else {
            names.setText(text[i]);
            icon.setImageResource(imgs[i]);
        }
        return view;
    }
}
