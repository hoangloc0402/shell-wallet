package com.example.shell.shellwallet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.shell.shellwallet.MainActivity.currentTheme;
import static com.example.shell.shellwallet.MainActivity.sharedPref;

public class AddActivity extends AppCompatActivity {
    private Calendar calendar;
    private TextView dateTextView;
    private int year, month, day;
    DatabaseHelper mydb;
    SharedPreferences sharedPreferences;
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    Context context;
    ArrayList<Category> categoryList;
    ImageView walletImage;
    TextView currencyText;
    Wallet currentWallet;
    int selectedCategoryPositon = -1;
    int type;
    int edit;
    int currentwalletID;
    HistoryCard history;
    EditText editTextAmount;
    EditText editTextNote;
    int themeColor;
    int dateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentTheme = sharedPref.getInt("currentTheme", R.style.AppTheme);
        themeColor = sharedPref.getInt("currentColor",R.color.colorPrimary);
        setTheme(currentTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dateFormat = sharedPref.getInt("currentDateFormat",0);
        mydb = new DatabaseHelper(this);
        sharedPreferences =  getSharedPreferences(getString(R.string.preperences_file),Context.MODE_PRIVATE);
        currentwalletID = sharedPreferences.getInt(getString(R.string.wallet_current),0);
        dateTextView = (TextView) findViewById(R.id.textViewDate);
        dateTextView.setTextColor(getResources().getColor(themeColor));
        TextView chooseCat = (TextView) findViewById(R.id.choosecategory);
        chooseCat.setTextColor(getResources().getColor(themeColor));
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        setGridView();
        //Handle Wallet List Click
        final Intent intentWallet = new Intent(this, ChooseWalletActivity.class);
        //Display walleth
        currentWallet = mydb.getWallet(currentwalletID);
        walletImage = (ImageView) findViewById(R.id.walletImage);
        walletImage.setImageResource(currentWallet.getIcon());
        currencyText = (TextView) findViewById(R.id.currencydisplay);
        currencyText.setText(currentWallet.getCurrency());
        editTextAmount= (EditText) findViewById(R.id.editTextAmount);
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        if(edit!=0) {
            float a = history.getAmount();
            if(a<0) a = a*-1;
            editTextAmount.setText(format(a) + "");
            editTextNote.setText(history.getNote());
        }
        walletImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivityForResult(intentWallet, 1);
            }
        });
    }
    private void setGridView(){
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        edit = intent.getIntExtra("edit",0);
        history = (HistoryCard) intent.getSerializableExtra("history");
        //categoryList = new ArrayList<Category>();
        gridView = (GridView) findViewById(R.id.gridview1);
        if(type==1){//expense
            categoryList=mydb.getExpenseCategory();
            gridViewAdapter = new GridViewAdapter(this,categoryList);
            gridView.setAdapter(gridViewAdapter);
            if(edit==0) setTitle(getString(R.string.add_expense));
            else {
                setTitle(getString(R.string.edit_expense));
                for (int i = 0; i < categoryList.size(); i++) {
                    if (history.getIcon() == categoryList.get(i).getIcon()) {
                        selectedCategoryPositon = i;
                        gridView.setSelection(i);
                        gridViewAdapter.setSelectedPosition(i);
                        gridViewAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
        else {//income
            categoryList=mydb.getIncomeCategory();
            gridViewAdapter = new GridViewAdapter(this, categoryList);
            gridView.setAdapter(gridViewAdapter);
            if(edit==0) setTitle(getString(R.string.add_income));
            else {
                setTitle(getString(R.string.edit_income));
                for (int i = 0; i < categoryList.size(); i++) {
                    if (history.getIcon() == categoryList.get(i).getIcon()) {
                        selectedCategoryPositon = i;
                        gridView.setSelection(i);
                        gridViewAdapter.setSelectedPosition(i);
                        gridViewAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
        //gridView.setSelection(20);
        //gridView.invalidateViews();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //gv.setItemChecked(position,true);
                //finish();
                selectedCategoryPositon = position;
                gridViewAdapter.setSelectedPosition(position);
                gridViewAdapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            int walletId = data.getIntExtra(ChooseWalletActivity.WALLET_ID,0);
            currentWallet = mydb.getWallet(walletId);
            walletImage.setImageResource(currentWallet.getIcon());
            currencyText.setText(currentWallet.getCurrency());
        }
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(100);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 100) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
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
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.alert_message_cat), Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{

                    String amountString = editTextAmount.getText().toString();
                    if(amountString.length()==0){
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.alert_message_amount), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        float amount = Float.parseFloat(amountString);
                        if(amount==0){
                            Toast toast = Toast.makeText(getApplicationContext(), R.string.alert_message_amount_zero, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else{
                            if(type == 1) amount = amount*-1f;
                            int categoryId = categoryList.get(selectedCategoryPositon).getId();

                            String note = editTextNote.getText().toString();
                            if(edit==0) {
                                int walletId = currentWallet.getId();

                                int uniqueId = mydb.getUniqueId(DatabaseHelper.HISTORY_TABLE_NAME);
                                mydb.insertHistory(uniqueId, amount, note, walletId, categoryId, day, month + 1, year);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt(getString(R.string.wallet_current), walletId);
                                editor.commit();
                            }
                            else{
                                mydb.updateHistory(history.getId(),amount,note,currentwalletID,categoryId,day, month + 1, year);
                            }
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                }
                return true;

            default:
                finish();
                return true;
        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    year = arg1;
                    month = arg2;
                    day = arg3;
                    showDate(year,month+1,day);
                }
            };

    private void showDate(int year, int month, int day) {
        if(day == calendar.get(Calendar.DAY_OF_MONTH) &&
                month == calendar.get(Calendar.MONTH)+1 &&
                year == calendar.get(Calendar.YEAR)){
            dateTextView.setText(R.string.today);
        }
        else {
            if(dateFormat==1){
                dateTextView.setText(month+"/"+day+"/"+year);
            }
            else {
                dateTextView.setText(day + "/" + month + "/" + year);
            }
        }
    }
    String format(float a){
        return new DecimalFormat("#.####").format(a);
    }

}
