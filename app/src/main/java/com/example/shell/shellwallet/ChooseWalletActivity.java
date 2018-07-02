package com.example.shell.shellwallet;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by QuocBao on 06/04/2017.
 */

public class ChooseWalletActivity extends ListActivity{
    public static String WALLET_ID = "id";
    public String[] names, currencies;
    private ArrayList<Wallet> walletList;
    private DatabaseHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new DatabaseHelper(this);
        walletList = mydb.getAllWallet();
        final ArrayAdapter<Wallet> adapter = new ChooseWalletListAdapter(this, walletList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Wallet w = walletList.get(position);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("id",w.getId());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }


}
