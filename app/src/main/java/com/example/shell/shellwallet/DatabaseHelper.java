package com.example.shell.shellwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.shell.shellwallet.MainActivity.sharedPref;

/**
 * Created by QuocBao on 03/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "shellwallet.db";
    public static final String HISTORY_TABLE_NAME = "history";
    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String WALLET_TABLE_NAME = "wallet";
    private HashMap hp;
    Context context;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+HISTORY_TABLE_NAME+" (" + "id INTEGER PRIMARY KEY , " + "amount REAL, note TEXT," + "wallet INTEGER, " + "category INTEGER, " + "day INTEGER, " + "month INTEGER, " + "year INTEGER)");
        db.execSQL("CREATE TABLE "+CATEGORY_TABLE_NAME+" (" + "id INTEGER PRIMARY KEY, " + "name TEXT," + "flag INTEGER, " + "icon INTEGER )");
        db.execSQL("CREATE TABLE "+WALLET_TABLE_NAME+" (" + "id INTEGER PRIMARY KEY, " + "name TEXT," + "currency TEXT, " + "initbalance REAL, " + "icon INTEGER )");

        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (0 ,      'Bills',1,"             +R.drawable.expence_bills_utillies+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (1 ,      'Shopping',1,"          +R.drawable.expence_shopping+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (2 ,      'Entertainment',1,"     +R.drawable.expence_entertainment+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (3 ,      'Gifts',1,"             +R.drawable.expence_gift_donation+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (4 ,      'Tranport',1,"          +R.drawable.expence_transportation+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (5 ,      'Food',1,"              +R.drawable.expence_food+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (6 ,      'Travel',1,"            +R.drawable.expence_travel+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (7 ,      'Education',1,"         +R.drawable.expence_education+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (8 ,      'Insurances',1,"        +R.drawable.expence_insuarences+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (9 ,      'Familly',1,"           +R.drawable.expence_family+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (10 ,     'Lover',1,"             +R.drawable.expence_friends_lovers+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (11 ,     'Health',1,"            +R.drawable.expence_heath_fitness+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (12 ,     'Investment',1,"        +R.drawable.expence_investment+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (13 ,     'Business',1,"          +R.drawable.expence_business+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (14 ,     'Salary',0,"            +R.drawable.income_salary+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (15 ,     'Selling',0,"           +R.drawable.income_selling+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (16 ,     'Interest',0,"          +R.drawable.income_interest+")");
        db.execSQL("INSERT INTO "+CATEGORY_TABLE_NAME+"(id,name,flag,icon) values (17 ,     'Gifts',0,"             +R.drawable.income_gift+")");

        db.execSQL("INSERT INTO "+WALLET_TABLE_NAME+"(id,name,currency,initbalance,icon) values (0 , 'Cash',      'USD', 0.0, "+R.drawable.wallet_cash+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+HISTORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+WALLET_TABLE_NAME);
        onCreate(db);
    }
    void updateLanguageEN(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE wallet SET name = 'Cash' WHERE id = 0");

        db.execSQL("UPDATE category SET name = 'Bills' WHERE id = 0");
        db.execSQL("UPDATE category SET name = 'Shopping' WHERE id = 1");
        db.execSQL("UPDATE category SET name = 'Entertainment' WHERE id = 2");
        db.execSQL("UPDATE category SET name = 'Gifts' WHERE id = 3");
        db.execSQL("UPDATE category SET name = 'Tranport' WHERE id = 4");
        db.execSQL("UPDATE category SET name = 'Food' WHERE id = 5");
        db.execSQL("UPDATE category SET name = 'Travel' WHERE id = 6");
        db.execSQL("UPDATE category SET name = 'Education' WHERE id = 7");
        db.execSQL("UPDATE category SET name = 'Insurances' WHERE id = 8");
        db.execSQL("UPDATE category SET name = 'Familly' WHERE id = 9");
        db.execSQL("UPDATE category SET name = 'Lover' WHERE id = 10");
        db.execSQL("UPDATE category SET name = 'Health' WHERE id = 11");
        db.execSQL("UPDATE category SET name = 'Investment' WHERE id = 12");
        db.execSQL("UPDATE category SET name = 'Business' WHERE id = 13");
        db.execSQL("UPDATE category SET name = 'Salary' WHERE id = 14");
        db.execSQL("UPDATE category SET name = 'Selling' WHERE id = 15");
        db.execSQL("UPDATE category SET name = 'Interest' WHERE id = 16");
        db.execSQL("UPDATE category SET name = 'Gifts' WHERE id = 17");


    }
    void updateLanguageVN(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE wallet SET name = 'Tiền mặt' WHERE id = 0");

        db.execSQL("UPDATE category SET name = 'Hóa đơn' WHERE id = 0");
        db.execSQL("UPDATE category SET name = 'Mua sắm' WHERE id = 1");
        db.execSQL("UPDATE category SET name = 'Giải trí' WHERE id = 2");
        db.execSQL("UPDATE category SET name = 'Quà' WHERE id = 3");
        db.execSQL("UPDATE category SET name = 'Di chuyển' WHERE id = 4");
        db.execSQL("UPDATE category SET name = 'Ăn uống' WHERE id = 5");
        db.execSQL("UPDATE category SET name = 'Du lịch' WHERE id = 6");
        db.execSQL("UPDATE category SET name = 'Giáo dục' WHERE id = 7");
        db.execSQL("UPDATE category SET name = 'Bảo hiểm' WHERE id = 8");
        db.execSQL("UPDATE category SET name = 'Gia đình' WHERE id = 9");
        db.execSQL("UPDATE category SET name = 'Người yêu' WHERE id = 10");
        db.execSQL("UPDATE category SET name = 'Sức khỏe' WHERE id = 11");
        db.execSQL("UPDATE category SET name = 'Đầu tư' WHERE id = 12");
        db.execSQL("UPDATE category SET name = 'Kinh doanh' WHERE id = 13");
        db.execSQL("UPDATE category SET name = 'Lương' WHERE id = 14");
        db.execSQL("UPDATE category SET name = 'Mua bán' WHERE id = 15");
        db.execSQL("UPDATE category SET name = 'Tiền lời' WHERE id = 16");
        db.execSQL("UPDATE category SET name = 'Quà' WHERE id = 17");
    }

    public boolean insertHistory (Integer id,Float amount, String note,Integer wallet, Integer category, Integer day,Integer month, Integer year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("amount", amount);
        contentValues.put("note", note);
        contentValues.put("wallet",wallet);
        contentValues.put("category", category);
        contentValues.put("day", day);
        contentValues.put("month", month);
        contentValues.put("year",year);
        db.insert(HISTORY_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean insertCategory (Integer id, String name, Integer flag, Integer icon){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("flag",flag);
        contentValues.put("icon",icon);
        db.insert(CATEGORY_TABLE_NAME, null, contentValues);
        return true;
    }


    public boolean updateHistory (Integer id, Float amount, String note,Integer wallet , Integer category, Integer day,Integer month, Integer year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", amount);
        contentValues.put("note", note);
        contentValues.put("wallet",wallet);
        contentValues.put("category", category);
        contentValues.put("day", day);
        contentValues.put("month", month);
        contentValues.put("year",year);
        db.update(HISTORY_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean updateCategory (Integer id, String name, Integer flag, Integer icon ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("icon",icon);
        contentValues.put("flag",flag);
        db.update(CATEGORY_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean insertWallet (Integer id, String name,String currency,float initbalance, Integer icon){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("currency",currency);
        contentValues.put("initbalance",initbalance);
        contentValues.put("icon",icon);
        db.insert(WALLET_TABLE_NAME,null, contentValues);
        return true;
    }
    public boolean updateWallet (Integer id, String name,String currency,float initbalance, Integer icon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("currency",currency);
        contentValues.put("initbalance",initbalance);
        contentValues.put("icon",icon);
        db.update(WALLET_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public void deleteRow (String tableName,Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName,
                "id = ? ",
                new String[]{Integer.toString(id)});
        if(tableName==CATEGORY_TABLE_NAME){
            deleteHistoryCat(id);
        }
        else if(tableName==WALLET_TABLE_NAME){
            deleteHistoryWal(id);
        }
    }
    public void deleteHistoryCat(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("history",
                "category = ? ",
                new String[]{Integer.toString(id)});
    }
    public void deleteHistoryWal(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("history",
                "wallet = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<Category> getIncomeCategory() {
        ArrayList<Category> array_list = new ArrayList<Category>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+CATEGORY_TABLE_NAME+" WHERE flag = 0", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(new Category(res.getInt(0),res.getString(1),0 ,res.getInt(3)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }
    public ArrayList<Category> getExpenseCategory() {
        ArrayList<Category> array_list = new ArrayList<Category>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+CATEGORY_TABLE_NAME+" WHERE flag = 1", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(new Category(res.getInt(0),res.getString(1),1,res.getInt(3)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }
    public ArrayList<Wallet> getAllWallet() {
        ArrayList<Wallet> array_list = new ArrayList<Wallet>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+WALLET_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(new Wallet(res.getInt(0),res.getString(1),res.getString(2),res.getFloat(3),res.getInt(4)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }
    public Wallet getWallet(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+WALLET_TABLE_NAME+ " WHERE id="+id+"", null );
        res.moveToFirst();
        Wallet w = new Wallet(res.getInt(0),res.getString(1),res.getString(2),res.getFloat(3),res.getInt(4));
        res.close();
        return w;
    }
    public int getUniqueId(String tablename){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+tablename+ " ORDER BY id DESC LIMIT 1", null );
        int a;
        if(res.moveToFirst()){
            a = res.getInt(0)+1;
        }
        else a = 0;
        res.close();
        return a;
    }
    public List<HistoryCard> getAllHistory(int walletid){
        List<HistoryCard> array_list = new ArrayList<HistoryCard>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery(
                "SELECT icon , name, note , amount, day, month, year, history.id " +
                        "FROM history LEFT JOIN category " +
                        "ON history.category = category.id "+
                        "WHERE history.wallet ="+ walletid +" "+
                        "ORDER BY year DESC,month DESC,day DESC,history.id DESC " +
                        "LIMIT 30" , null
         );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(new HistoryCard(
                    res.getInt(0),
                    res.getString(1),
                    res.getString(2),
                    res.getFloat(3),
                    res.getInt(4),
                    res.getInt(5),
                    res.getInt(6),
                    res.getInt(7)
            ));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }
    public List<PieEntry> getPieChartData(int walletid) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<PieEntry> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH)+1;
        Cursor res = db.rawQuery(
                "SELECT SUM(history.amount), category.name, category.icon " + "FROM history LEFT JOIN category " +
                        "ON history.category = category.id " + "WHERE category.flag = 1 AND history.wallet ="+walletid +" " +
                        "AND month = "+thisMonth+" AND year = "+ thisYear+" " + "GROUP BY category.id " +
                        "ORDER BY sum(history.amount) ASC", null
        );
        while (res.moveToNext()){
            if(list.size()!=7) {
                list.add(new PieEntry(res.getFloat(0) * -1, res.getString(1), context.getDrawable(res.getInt(2))));
            }
            else{
                list.get(list.size()-1).setLabel(context.getString(R.string.others));
                list.get(list.size()-1).setIcon(context.getDrawable(R.drawable.ic_others));
                list.get(list.size()-1).setY(list.get(list.size()-1).getValue()+ res.getFloat(0)*-1);
            }
        }
        res.close();
        return list;
    }
    public float getTotalSpent(int walletid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT SUM(history.amount)" +
                        "FROM history " +
                        "WHERE history.amount < 0 AND history.wallet="+walletid+"", null
        );
        res.moveToFirst();
        float a= res.getFloat(0);
        res.close();
        if(a<0) {
            return a * -1;
        }
        else return a;
    }
    public float getTotalSpentThisMonth(int walletid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH)+1;
        Cursor res = db.rawQuery(
                "SELECT SUM(history.amount)" +
                        "FROM history " +
                        "WHERE history.amount < 0 AND history.wallet="+walletid+" " +
                        "AND month = "+thisMonth+" AND year = "+ thisYear, null
        );
        res.moveToFirst();
        float a= res.getFloat(0);
        res.close();
        if(a<0) {
            return a * -1;
        }
        else return a;
    }
    public float getTotalIncome(int walletid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT SUM(history.amount)" +
                        "FROM history " +
                        "WHERE history.amount > 0 AND history.wallet="+walletid+"", null
        );
        res.moveToFirst();
        float a= res.getFloat(0);
        res.close();
        return a;
    }
    public float getTotalIncomeThisMonth(int walletid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH)+1;
        Cursor res = db.rawQuery(
                "SELECT SUM(history.amount)" +
                        "FROM history " +
                        "WHERE history.amount > 0 AND history.wallet="+walletid+" " +
                        "AND month = "+thisMonth+" AND year = "+ thisYear, null
        );
        res.moveToFirst();
        float a= res.getFloat(0);
        res.close();
        if(a<0) {
            return a * -1;
        }
        else return a;
    }
    public List<BarEntry> getBarChartData(int walletid) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<BarEntry> list = new ArrayList<>();
        Cursor res = db.rawQuery(
                "SELECT SUM(amount), day, month, year " + "FROM history "+ "WHERE amount<0 AND wallet ="+walletid +" " +
                        "GROUP BY day, month, year " + "ORDER BY year DESC,month DESC, day DESC LIMIT 7" , null
        );
        List<Date> dates = Last7Days();
        List<MyDate> myDates = new ArrayList<>();
        while (res.moveToNext()) {
            myDates.add(new MyDate(res.getInt(3),res.getInt(2),res.getInt(1),res.getFloat(0)));
        }
        Calendar calendar = Calendar.getInstance();
        int thisyear = calendar.get(Calendar.YEAR);
        int thismonth = calendar.get(Calendar.MONTH);
        int thisday = calendar.get(Calendar.DAY_OF_MONTH);
        for(int i=0;i<7;i++){
            int m = dates.get(i).getMonth()+1;
            int v = IsContain(myDates,dates.get(i).getDate(),dates.get(i).getMonth(),dates.get(i).getYear());
            String label="";
            if(i!=0){
                int dateFormat = sharedPref.getInt("currentDateFormat",0);
                if(dateFormat==1) label = m+"/"+dates.get(i).getDate();
                else label = dates.get(i).getDate()+"/"+m;
            }
            else label=context.getString(R.string.today);
            if(v!=-1) {
                list.add(new BarEntry(6-i,myDates.get(v).amount*-1,label));
            }
            else list.add(new BarEntry(6-i,0,label));

        }
        Collections.reverse(list);
        res.close();
        return list;
    }
    List<java.util.Date> Last7Days(){
        List<java.util.Date> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        list.add(calendar.getTime());
        for (int  i=0;i<6;i++){
            calendar.add(Calendar.DAY_OF_YEAR,-1);
            list.add(calendar.getTime());
        }
        calendar.add(Calendar.DAY_OF_YEAR,+6);
        return  list;
    }
    int IsContain(List<MyDate> cursors,int date,int month,int year){
        for (int i=0;i<cursors.size();i++){
            if(cursors.get(i).date==date&&
                    cursors.get(i).month==month+1&&
                    cursors.get(i).year==year+1900)
                return i;
        }
        return -1;
    }
    public class MyDate{
        public int year;
        public int month;
        public int date;
        public float amount;
        public MyDate(int year,int month,int date, float amount){
            this.year = year;
            this.month = month;
            this.date = date;
            this.amount = amount;
        }
    }
}
