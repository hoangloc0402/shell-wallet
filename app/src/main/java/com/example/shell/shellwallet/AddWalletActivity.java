package com.example.shell.shellwallet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.shell.shellwallet.MainActivity.currentTheme;

public class AddWalletActivity extends AppCompatActivity {

    GridView gridView;
    GridViewAdapter gridViewAdapter;
    ArrayList<Category> categoryList;
    DatabaseHelper mydb;
    SharedPreferences sharedPref;

    int selectedCategoryPositon = -1;
    int edit;
    String currency;
    Spinner spinner;
    Wallet wallet;
    EditText walletName;
    EditText walletAmount;
    int themeColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = getSharedPreferences(getString(R.string.preperences_file), Context.MODE_PRIVATE);
        currentTheme = sharedPref.getInt("currentTheme", R.style.AppTheme);
        themeColor = sharedPref.getInt("currentColor",R.color.colorPrimary);
        setTheme(currentTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);

        mydb = new DatabaseHelper(this);

        final Intent intent = getIntent();
        edit = intent.getIntExtra("edit",0);

        categoryList = new ArrayList<>();
        addCategoryList();
        TextView initbalance = (TextView)findViewById(R.id.init_wallet_balance);
        initbalance.setTextColor(getResources().getColor(themeColor));
        TextView walletcur = (TextView)findViewById(R.id.wallet_currency);
        walletcur.setTextColor(getResources().getColor(themeColor));
        gridView = (GridView) findViewById(R.id.wallet_gridview);
        walletName = (EditText) findViewById(R.id.wallet_name);
        walletAmount = (EditText)findViewById(R.id.wallet_init_balance);
        gridViewAdapter = new GridViewAdapter(this,categoryList,1);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryPositon = position;
                gridViewAdapter.setSelectedPosition(position);
                gridViewAdapter.notifyDataSetChanged();
            }
        });
        if(edit==0) {
            setTitle(getString(R.string.add_wallet));
        }
        else{
            setTitle(getString(R.string.edit_wallet));
            wallet = (Wallet) intent.getSerializableExtra("wallet");

            walletName.setText(wallet.getName());
            walletAmount.setText(format(wallet.getInitbalance())+"");
            for(int i=0;i<categoryList.size();i++){
                if(wallet.getIcon()==categoryList.get(i).getIcon()){
                    selectedCategoryPositon = i;
                    gridView.setSelection(i);
                    gridViewAdapter.setSelectedPosition(i);
                    gridViewAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }

        spinner = (Spinner) findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
            switch (item.getItemId()) {
                case R.id.save:
                    if(selectedCategoryPositon==-1){
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.alert_message_icon, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else if(walletName.getText().toString().length()==0){
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.alert_message_name, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else if (walletAmount.getText().toString().length()==0){
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.alert_message_amount), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        String name = walletName.getText().toString();
                        int icon =categoryList.get(selectedCategoryPositon).getIcon();
                        Float balance = Float.parseFloat(walletAmount.getText().toString());
                        String currency = spinner.getSelectedItem().toString();
                        if (edit == 0) {
                            int walletId = mydb.getUniqueId("wallet");
                            mydb.insertWallet(walletId, name, currency, balance, icon);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putInt(getString(R.string.wallet_current), walletId);
                            editor.commit();
                        } else {
                            mydb.updateWallet(wallet.getId(), name, currency, balance, icon);
                        }
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    return true;
                default:
                    finish();
                    return true;
            }

    }
    void addCategoryList(){
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.wallet_cash));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.wallet_google));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.wallet_mastercard));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.wallet_paypal));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.wallet_visa));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.wallet_bitcoin));
    };
    String format(float a){
        return new DecimalFormat("#.####").format(a);
    }
}
