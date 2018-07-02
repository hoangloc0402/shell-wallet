package com.example.shell.shellwallet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by QuocBao on 09/04/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    final int BALANCE = 0;
    final int PIECHART = 2;
    final int BARCHART =1;
    final int TYPE_HISTORY = 1;
    DatabaseHelper mydb;
    int currentwalletid;
    int recycleType = 0;
    public static int needupdateFlag = 0;
    List<HistoryCard> histories;
    FragmentActivity fragmentActivity;
    int currentDecimal = MainActivity.sharedPref.getInt("currentDecimal",0);
    Context context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolderPieChart extends RecyclerView.ViewHolder {
        public PieChart pieChart;
        ListView listView;
        ListViewAdapter listViewAdapter;
        public ViewHolderPieChart(View v) {
            super(v);
        }
    }
    public class ViewHolderBalance extends RecyclerView.ViewHolder {
        public TextView balance, inflow,outflow, total, walletName;
        public View seperator;
        public ViewHolderBalance(View v) {
            super(v);
        }
    }
    public class ViewHolderBarChart extends RecyclerView.ViewHolder {
        public BarChart barChart;
        public ViewHolderBarChart(View v) {
            super(v);
        }
    }
    public class ViewHolderHistory extends RecyclerView.ViewHolder{
        public ImageView icon, menu;
        public TextView date,note,name,amount;
        public ConstraintLayout layout;
        public View view;
        public ViewHolderHistory(View v) {
            super(v);
            view = v;
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(int currentwalletid) {
        this.currentwalletid = currentwalletid;
    }




    public RecyclerViewAdapter(int currentwalletid, Context context, FragmentActivity fragmentActivity) {
        this.currentwalletid = currentwalletid;
        this.recycleType = TYPE_HISTORY;
        DatabaseHelper mydb = new DatabaseHelper(context);
        this.context = context;
        this.fragmentActivity = fragmentActivity;
        histories = mydb.getAllHistory(currentwalletid);
    }
    @Override
    public int getItemViewType(int position) {
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(recycleType == TYPE_HISTORY){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_history, parent, false);

            mydb = new DatabaseHelper(v.getContext());
            ViewHolderHistory vh = new ViewHolderHistory(v);
            vh.icon = (ImageView) v.findViewById(R.id.history_icon);
            vh.menu = (ImageView) v.findViewById(R.id.history_menu);
            vh.name = (TextView) v.findViewById(R.id.history_name);
            vh.note = (TextView) v.findViewById(R.id.history_note);
            vh.date = (TextView) v.findViewById(R.id.history_date);
            vh.amount = (TextView) v.findViewById(R.id.history_amount);
            vh.layout = (ConstraintLayout) v.findViewById(R.id.history_layout);
            return vh;

        }
        else {
            if (viewType == PIECHART) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pie_chart, parent, false);

                ViewHolderPieChart vh = new ViewHolderPieChart(v);
                mydb = new DatabaseHelper(v.getContext());
                initpiechart(vh, v);

                return vh;
            }
            else if(viewType == BARCHART){
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bar_chart, parent, false);

                ViewHolderBarChart vh = new ViewHolderBarChart(v);
                mydb = new DatabaseHelper(v.getContext());
                initbarchart(vh,v);

                return vh;
            }
            else {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_balance, parent, false);

                ViewHolderBalance vh = new ViewHolderBalance(v);
                mydb = new DatabaseHelper(v.getContext());
                initbalance(vh,v);

                return vh;
            }
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(recycleType == TYPE_HISTORY){
            final ViewHolderHistory vh = (ViewHolderHistory) holder;
            vh.icon.setImageResource(histories.get(position).getIcon());
            String s = histories.get(position).getName();
            if (s.length() > 13) s = s.substring(0, 12) + "...";//display long name
            vh.name.setText(s);
            String note = histories.get(position).getNote();
            if (note.length()> 30) note = note.substring(0,30)+ "...";//display long note
            vh.note.setText(note);
            vh.date.setText(histories.get(position).getDate());
            final float amount = histories.get(position).getAmount();
            String currency = mydb.getWallet(currentwalletid).getCurrency();
            vh.amount.setText(currencyFormat(amount)+" "+ currency);
            if(amount <0)vh.amount.setTextColor(vh.view.getResources().getColor(R.color.outflow));
            else vh.amount.setTextColor(vh.view.getResources().getColor(R.color.inflow));
            vh.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            vh.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, v);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.menu_history, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.edit:
                                    Intent intent = new Intent(context,AddActivity.class);
                                    if(amount<0) intent.putExtra("type",1);
                                    else intent.putExtra("type",0);
                                    intent.putExtra("edit",1);
                                    intent.putExtra("history", histories.get(position));
                                    fragmentActivity.startActivityForResult(intent,1);
                                    break;
                                case R.id.delete:
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                    //dialog.setCancelable(false);
                                    dialog.setTitle(R.string.delete_title);
                                    dialog.setMessage(R.string.delete_message );
                                    dialog.setPositiveButton(R.string.delete_confirm, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            mydb.deleteRow("history",histories.get(position).getId());
                                            histories.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, histories.size());
                                            needupdateFlag = 1;
                                            //MainActivity.result.setSelection(1,true);

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
                                    break;
                                default:break;
                            }
                            return true;
                        }
                    });
                }
            });
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(recycleType == TYPE_HISTORY){
            return histories.size();
        }
        else {
            return 3;
        }
    }
    private void initpiechart( ViewHolderPieChart vh,  View v){
        final List<PieEntry> entries = mydb.getPieChartData(currentwalletid);
        vh.pieChart = (PieChart) v.findViewById(R.id.piechart);
        PieDataSet dataSet = new PieDataSet(entries,"Pie Chart");
        dataSet.setColors(new int[]{R.color.a1, R.color.a2,R.color.a3, R.color.a4, R.color.a5, R.color.a6, R.color.a7
        },v.getContext());
        dataSet.setValueTextColor(Color.TRANSPARENT);
        dataSet.setDrawIcons(false);
        PieData data = new PieData(dataSet);
        vh.pieChart.setData(data);
        vh.pieChart.setEntryLabelColor(Color.TRANSPARENT);
        vh.pieChart.getLegend().setEnabled(false);
        vh.pieChart.setHoleRadius(50);
        vh.pieChart.setTransparentCircleColor(Color.BLACK);
        vh.pieChart.setTransparentCircleRadius(60);
        vh.pieChart.setTransparentCircleAlpha(40);
        vh.pieChart.getDescription().setEnabled(false);
        final String currency = mydb.getWallet(currentwalletid).getCurrency();
        final float total = mydb.getTotalSpentThisMonth(currentwalletid);
        vh.pieChart.setCenterText(MainActivity.sContext.getString(R.string.totalspent)+"\n"+currencyFormat(total)+" "+currency);
        vh.pieChart.setCenterTextSize(15);
        vh.pieChart.setCenterTextColor(Color.BLACK);
        vh.pieChart.setTouchEnabled(false);
        LegendEntry[] legendEntries = vh.pieChart.getLegend().getEntries();
        vh.listView = (ListView) v.findViewById(R.id.legend);
        vh.listViewAdapter = new ListViewAdapter(v.getContext(),entries,legendEntries,total,currency);
        vh.listView.setAdapter(vh.listViewAdapter);
        setListViewHeightBasedOnChildren(vh.listView);
        vh.listView.setEnabled(false);
    }
    void initbarchart( ViewHolderBarChart vh,  View v){

        final List<BarEntry> entries = mydb.getBarChartData(currentwalletid);
        vh.barChart = (BarChart) v.findViewById(R.id.barchart);
        BarDataSet dataSet = new BarDataSet(entries,"Barcaht");
        dataSet.setValueTextSize(12f);
        dataSet.setColors(new int[]{
                R.color.a7,
                R.color.a6,
                R.color.a5,
                R.color.a4,
                R.color.a3,
                R.color.a2,
                R.color.a1
        },v.getContext());
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return currencyFormat(value);
            }
        });
        BarData data = new BarData(dataSet);
        vh.barChart.setData(data);
        vh.barChart.setTouchEnabled(false);
        vh.barChart.getLegend().setEnabled(false);
        vh.barChart.getDescription().setEnabled(false);
        vh.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        vh.barChart.getAxisLeft().setEnabled(false);
        vh.barChart.getAxisRight().setEnabled(false);
        vh.barChart.getAxisLeft().setDrawGridLines(false);
        vh.barChart.getAxisLeft().setStartAtZero(true);
        vh.barChart.getXAxis().setDrawGridLines(false);
        vh.barChart.getXAxis().setTextSize(13f);
        vh.barChart.setExtraBottomOffset(5f);
        vh.barChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (String) entries.get((int)value).getData();
            }
        });

    }
    void initbalance(ViewHolderBalance vh,View v){
        vh.balance = (TextView) v.findViewById(R.id.balance);
        vh.inflow = (TextView) v.findViewById(R.id.inflowAmount);
        vh.outflow = (TextView) v.findViewById(R.id.outflowAmount);
        vh.total = (TextView) v.findViewById(R.id.totalAmount);
        vh.walletName = (TextView) v.findViewById(R.id.wallet_name);
        vh.seperator = v.findViewById(R.id.seperator);


        Wallet w = mydb.getWallet(currentwalletid);
        vh.walletName.setText(w.getName());
        float income = mydb.getTotalIncomeThisMonth(currentwalletid);
        float spent = mydb.getTotalSpentThisMonth(currentwalletid);
        float total = mydb.getTotalIncome(currentwalletid) - mydb.getTotalSpent(currentwalletid);
        float balance = w.getInitbalance() + total;
        vh.balance.setText(currencyFormat(balance) + " " + w.getCurrency());
        vh.inflow.setText(currencyFormat(income) + " " + w.getCurrency());
        vh.outflow.setText(currencyFormat(spent) + " " + w.getCurrency());
        vh.total.setText(currencyFormat(total) + " " + w.getCurrency());
        vh.outflow.measure(0, 0);
        vh.inflow.measure(0, 0);
        int maxlenght;
        if (vh.outflow.getMeasuredWidth() > vh.inflow.getMeasuredWidth())
            maxlenght = vh.outflow.getMeasuredWidth();
        else maxlenght = vh.inflow.getMeasuredWidth();
        vh.seperator.getLayoutParams().width = maxlenght + 150;
    }
    String currencyFormat(float d){
        String s = new DecimalFormat("###,###,###").format(d);
        if(currentDecimal==1) s = s.replace(",",".");
        return s;
    }
    void setListViewHeightBasedOnChildren(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight=0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        RecyclerView.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }
};

