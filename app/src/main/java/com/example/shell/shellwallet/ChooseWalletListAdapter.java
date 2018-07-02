package com.example.shell.shellwallet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by QuocBao on 06/04/2017.
 */

public class ChooseWalletListAdapter extends ArrayAdapter<Wallet> {
    private final ArrayList<Wallet> list;
    private final Activity context;

    static class ViewHolder {
        protected TextView name;
        protected TextView currency;
        protected ImageView icon;
    }

    public ChooseWalletListAdapter(Activity context, ArrayList<Wallet> list) {
        super(context, R.layout.wallet_list_view, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        LayoutInflater inflator = context.getLayoutInflater();
        view = inflator.inflate(R.layout.wallet_list_view, null);
        final ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = (TextView) view.findViewById(R.id.wallet_name);
        viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
        viewHolder.currency = (TextView) view.findViewById(R.id.currency);

        viewHolder.name.setText(list.get(position).getName());
        viewHolder.icon.setImageResource(list.get(position).getIcon());
        viewHolder.currency.setText(list.get(position).getCurrency());
        return view;
    }
}
