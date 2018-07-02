package com.example.shell.shellwallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.shell.shellwallet.MainActivity.currentTheme;
import static com.example.shell.shellwallet.MainActivity.sharedPref;

public class AddCategoryActivity extends AppCompatActivity {
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    ArrayList<Category> categoryList;
    DatabaseHelper mydb;
    public static int type  = 0;
    int edit;
    int selectedCategoryPositon = -1;
    EditText editText;
    Category category;
    int themeColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentTheme = sharedPref.getInt("currentTheme", R.style.AppTheme);
        themeColor = sharedPref.getInt("currentColor",R.color.colorPrimary);
        setTheme(currentTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        mydb = new DatabaseHelper(this);

        categoryList = new ArrayList<>();
        addCategoryList();
        TextView chooseicon = (TextView) findViewById(R.id.chooseiconcat);
        chooseicon.setTextColor(getResources().getColor(themeColor));
        gridView = (GridView) findViewById(R.id.grid_view_cat_add);
        gridViewAdapter = new GridViewAdapter(this,categoryList,1);
        gridView.setAdapter(gridViewAdapter);
        editText = (EditText) findViewById(R.id.cat_name);
        final Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        edit = intent.getIntExtra("edit",0);

        if(edit==0) {
            if (type == 1) {//expense
                setTitle(getString(R.string.add_expense));
            } else {//income
                setTitle(getString(R.string.add_income));
            }
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
        else{
            category = (Category) intent.getSerializableExtra("cat");
            editText.setText(category.getName());
            for(int i=0;i<categoryList.size();i++){
                if(category.getIcon()==categoryList.get(i).getIcon()){
                    selectedCategoryPositon = i;
                    gridView.setSelection(i);
                    gridViewAdapter.setSelectedPosition(i);
                    gridViewAdapter.notifyDataSetChanged();
                    break;
                }
            }
            if (type == 1) {//expense
                setTitle(getString(R.string.edit_expense));
            } else {//income
                setTitle(getString(R.string.edit_income));
            }
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedCategoryPositon = position;
                    gridViewAdapter.setSelectedPosition(position);
                    gridViewAdapter.notifyDataSetChanged();
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(edit==0) {
            getMenuInflater().inflate(R.menu.menu, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.menu_edit, menu);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        String name = editText.getText().toString();
            switch (item.getItemId()) {
                case R.id.save:
                    if(selectedCategoryPositon==-1){
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.alert_message_icon, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else if(editText.getText().toString().length()==0){
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.alert_message_name, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        if (edit == 0) {
                            int catId = mydb.getUniqueId("category");
                            mydb.insertCategory(catId, name, type, categoryList.get(selectedCategoryPositon).getIcon());
                        } else {
                            mydb.updateCategory(category.getId(), name, type, category.getIcon());
                            setResult(RESULT_OK);
                        }
                        setResult(RESULT_OK);
                        finish();
                    }
                    return true;
                case R.id.delete:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle(R.string.delete_category_title);
                    dialog.setMessage(R.string.delete_category_message);
                    dialog.setPositiveButton(R.string.delete_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            mydb.deleteRow("category",category.getId());
                            setResult(RESULT_OK);
                            finish();
                        }
                    })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Action for "Cancel".
                                }
                            });
                    final AlertDialog alert = dialog.create();
                    alert.show();
                    return true;
                default:
                    finish();
                    return true;
            }

    }
    void addCategoryList(){
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_bills_utillies));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_education));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_business));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_family));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_food));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_friends_lovers));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_gift_donation));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_heath_fitness));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_insuarences));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_investment));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_entertainment));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_shopping));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_transportation));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.expence_travel));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.income_gift));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.income_interest));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.income_salary));
        categoryList.add(new Category(categoryList.size(),"",0,R.drawable.income_selling));

    }
}
