package com.example.shell.shellwallet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    Drawer result;
    DatabaseHelper mydb;
    static SharedPreferences sharedPref;
    AccountHeader headerResult;
    Toolbar toolbar;
    Bundle savedInstanceState;
    ArrayList<Wallet> wallets;
    static SharedPreferences.Editor prefEditor;
    static int currentLang;
    static int currentTheme;
    final int delay = 350;
    int themeColor;
    int headerImage;
    public static Context sContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setElevation(0);
        mydb = new DatabaseHelper(this);
        sContext = this;
        wallets = mydb.getAllWallet();

        sharedPref = getSharedPreferences(getString(R.string.preperences_file),Context.MODE_PRIVATE);
        prefEditor = sharedPref.edit();
        currentTheme = sharedPref.getInt("currentTheme", R.style.AppTheme);
        themeColor = sharedPref.getInt("currentColor",R.color.colorPrimary);
        headerImage = sharedPref.getInt("currentHeader",R.drawable.header);


        toolbar.setBackgroundColor(getResources().getColor(themeColor));
        setTheme(currentTheme);
        setAppLang();
        setDateFormat();
        CreateDrawer();

    }
    void setAppLang(){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        currentLang = sharedPref.getInt("currentLang", 0);
        if (currentLang == 0)
            conf.setLocale(new Locale("en"));
        else if (currentLang == 1)
            conf.setLocale(new Locale("vi"));
        res.updateConfiguration(conf, dm);
    }
    void setDateFormat(){

    }

    @Override
    public void onBackPressed() {

        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){//request of add activity
            super.onActivityResult(requestCode,resultCode,data);
            CreateDrawer();
        }
        else if (requestCode == 2 && resultCode == Activity.RESULT_OK){//request of add category activity
            super.onActivityResult(requestCode,resultCode,data);
            result.setSelection(2,true);
        }

    }
    void CreateDrawer(){
        int color = getResources().getColor(R.color.text);
        int themeColorRes = getResources().getColor(themeColor);
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(headerImage)
                .addProfiles(
                        new ProfileSettingDrawerItem().withName(getString(R.string.addwallet)).withIcon(R.drawable.ic_add).withIdentifier(10000)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        if(profile instanceof IDrawerItem){
                            long id = profile.getIdentifier();
                            if(id == 10000){
                                Intent intent = new Intent(view.getContext(),AddWalletActivity.class);
                                intent.putExtra("edit",0);
                                startActivityForResult(intent,3);

                            }
                            for (int i=0;i<wallets.size();i++){
                                if(id == wallets.get(i).getId()){
                                    Toast toast = Toast.makeText(getApplicationContext(), wallets.get(i).getName(), Toast.LENGTH_SHORT);
                                    toast.show();
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt(getString(R.string.wallet_current), (int)id);
                                    editor.commit();
                                    result.setSelection(1,true);
                                }
                            }
                        }
                        return true;
                    }
                })
                .withOnAccountHeaderItemLongClickListener(new AccountHeader.OnAccountHeaderItemLongClickListener() {
                    @Override
                    public boolean onProfileLongClick(final View view, final IProfile profile, boolean current) {
                        if(profile instanceof IDrawerItem){
                            long id = profile.getIdentifier();
                            for (int i=0;i<wallets.size();i++){
                                if(id == wallets.get(i).getId()){
                                    final Wallet wallet = wallets.get(i);
                                    final int ir = i;
                                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                                    MenuInflater inflater = popup.getMenuInflater();
                                    inflater.inflate(R.menu.menu_history, popup.getMenu());
                                    popup.show();
                                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                        @Override
                                        public boolean onMenuItemClick(MenuItem item) {
                                            switch (item.getItemId()){
                                                case R.id.edit:
                                                    result.closeDrawer();
                                                    Intent intent = new Intent(view.getContext(),AddWalletActivity.class);
                                                    intent.putExtra("edit",1);
                                                    intent.putExtra("wallet",wallet);
                                                    startActivityForResult(intent,3);
                                                    break;
                                                case R.id.delete:
                                                    int curwalletid = sharedPref.getInt(getString(R.string.wallet_current),0);
                                                    if(curwalletid != wallet.getId()) {
                                                        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                                                        dialog.setTitle(R.string.delete_wallet_title);
                                                        dialog.setMessage(R.string.delete_wallet_message);
                                                        dialog.setPositiveButton(R.string.delete_confirm, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                mydb.deleteRow("wallet", wallet.getId());
                                                                headerResult.removeProfile(profile);
                                                                wallets.remove(ir);
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
                                                    }
                                                    else{
                                                        Toast t = Toast.makeText(view.getContext(),R.string.delete_wallet_warning,Toast.LENGTH_SHORT);
                                                        t.show();
                                                    }


                                                    break;
                                                default:break;
                                            }
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        return true;
                    }
                })
                .build();
        for (int i=0;i<wallets.size();i++){
            headerResult.addProfile(new ProfileDrawerItem().withName(wallets.get(i).getName())
                    .withIcon(wallets.get(i).getIcon()).withIdentifier(wallets.get(i).getId()).withSelectedTextColor(themeColorRes),i);
        }
        int currentwalletid = sharedPref.getInt(getString(R.string.wallet_current),0);
        headerResult.setActiveProfile(currentwalletid);

        //Now create your drawer and pass the AccountHeader.Result
        result = new DrawerBuilder().withActivity(this)
                .withToolbar(toolbar).withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.overview).withIcon(R.drawable.ic_overview)
                                .withSelectable(true).withTextColor(color).withSelectedTextColor(themeColorRes)
                                .withIconTintingEnabled(true).withIconColor(color)
                                .withTintSelectedIcon(true).withSelectedIconColor(themeColorRes)
                                .withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.managerCategories).withIcon(R.drawable.ic_category)
                                .withSelectable(true).withTextColor(color).withSelectedTextColor(themeColorRes)
                                .withIconTintingEnabled(true).withIconColor(color)
                                .withTintSelectedIcon(true).withSelectedIconColor(themeColorRes)
                                .withIdentifier(2),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.settings).withIcon(R.drawable.ic_settings)
                                .withSelectable(true).withTextColor(color).withSelectedTextColor(themeColorRes)
                                .withIconTintingEnabled(true).withIconColor(color)
                                .withTintSelectedIcon(true).withSelectedIconColor(themeColorRes)
                                .withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.about).withIcon(R.drawable.ic_about).withSelectable(true)
                                .withTextColor(color).withSelectedTextColor(themeColorRes)
                                .withIconTintingEnabled(true).withIconColor(color)
                                .withTintSelectedIcon(true).withSelectedIconColor(themeColorRes)
                                .withIdentifier(4)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener()  {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        long id = drawerItem.getIdentifier();
                        if (id==1) {
                            setTitle(R.string.app_name);
                            final OverviewFragment overviewFragment = new OverviewFragment();
                            ReplaceFragment(overviewFragment);
                        }
                        else if(id==2){
                            setTitle(R.string.managerCategories);
                            final ManageCategoriesFragment manageCategoriesFragment = new ManageCategoriesFragment();
                            ReplaceFragment(manageCategoriesFragment);
                        }
                        else if (id==3){
                            setTitle(R.string.settings);
                            final SettingsFragment settingsFragment = new SettingsFragment();
                            ReplaceFragment(settingsFragment);
                        }
                        else{
                            setTitle(R.string.about);
                            final AboutFragment aboutFragment = new AboutFragment();
                            ReplaceFragment(aboutFragment);
                        }
                        return true;
                    }
                })
                .build();
        result.setSelection(1,true);
    }
    void ReplaceFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.relative_fragment, fragment);
        ft.commitAllowingStateLoss();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                result.closeDrawer();
            }
        };
        handler.postDelayed(runnable, delay);
    }
}
