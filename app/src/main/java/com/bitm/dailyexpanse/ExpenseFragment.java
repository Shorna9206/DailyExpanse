package com.bitm.dailyexpanse;


import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    private FloatingActionButton fab;
    private String[] expaceTypes;
    Spinner expanceTypespn;
    ArrayAdapter<String> adapter;
    List<Expanse> expanseList;
    SqlHelper helper;
    RecyclerView recyclerView;
    Expanse_Adapter dataAdapter;
    private TextView fromDateTv, toDateTv;
    String sql;
    double totalAmount = 0.0;
    BottomSheet bottomSheet;

    int check = 0;


    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense2, container, false);


        init(view);
        expanseList.clear();
        String sql1 = "SELECT * FROM " + helper.TABLE_NAME;
        sqlquary(sql1);
        //Toast.makeText(getContext(), "amount: ", Toast.LENGTH_SHORT).show();
        setDateNTime();

        //geDataFromDatabase();
        //Toast.makeText(getContext(), "helllo", Toast.LENGTH_SHORT).show();


        return view;


    }


    public void geDataFromDatabase() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        expanseList.clear();

        String types = expanceTypespn.getSelectedItem().toString();

        String toDataExpanse = toDateTv.getText().toString().trim();
        String fromDateExpanse = fromDateTv.getText().toString().trim();
        Date todate = null;

        Date fromdate = null;
        try {

            todate = sdf.parse(toDataExpanse);
            fromdate = sdf.parse(fromDateExpanse);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long toDate = todate.getTime();
        long fromDate = fromdate.getTime();


        //Toast.makeText(getContext(), "to " +fromDate, Toast.LENGTH_SHORT).show();

        // Toast.makeText(getContext(), "from " +fromDate, Toast.LENGTH_SHORT).show();


        //sql="SELECT * FROM "+helper.TABLE_NAME+ " WHERE "+helper.COL_DATE+" LIKE '%"+toDataExpanse+"%' ";// AND Amount BETWEEN "+10+" AND "+20;

        sql = "SELECT * FROM " + helper.TABLE_NAME + " WHERE " + helper.COL_EXPANSE_TYPE + " LIKE '%" + types + "%'";


        Cursor cursor = helper.showData(sql);


        while (cursor.moveToNext()) {


            int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));


            String date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
            Date datefromSql = null;

            try {
                datefromSql = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long sqldate = datefromSql.getTime();


            if (fromDate <= sqldate && sqldate <= toDate) {

                // Toast.makeText(getContext(), "to " + fromDateExpanse, Toast.LENGTH_SHORT).show();

                //  Toast.makeText(getContext(), "from " + toDataExpanse, Toast.LENGTH_SHORT).show();
                String sql1 = "SELECT * FROM " + helper.TABLE_NAME + " WHERE " + helper.COL_EXPANSE_TYPE + " LIKE '%" + types + "%' AND Id =" + id;

                sqlquary(sql1);
                //Toast.makeText(getContext(), "List" + id, Toast.LENGTH_SHORT).show();


            }


        }


    }

    public void sqlquary(String sql) {


        Cursor cursor = helper.showData(sql);
        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
            String expanseType = cursor.getString(cursor.getColumnIndex(helper.COL_EXPANSE_TYPE));
            String amount = cursor.getString(cursor.getColumnIndex(helper.COL_AMOUNT));
            //   double newAmountAdd=Double.parseDouble(amount);
            // totalAmount=totalAmount+newAmountAdd;

            String date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
            String time = cursor.getString(cursor.getColumnIndex(helper.COL_TIME));
            byte[] image = cursor.getBlob(5);

            expanseList.add(new Expanse(id, expanseType, amount, date, time, image));

            // Toast.makeText(getContext(), "amount: " + amount, Toast.LENGTH_SHORT).show();
        }

        dataAdapter.notifyDataSetChanged();


    }

    private void setDateNTime() {

        fromDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expanseList.clear();
                dataAdapter.notifyDataSetChanged();
                fromDate();

            }
        });


        toDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expanseList.clear();
                dataAdapter.notifyDataSetChanged();

                toDate();


            }
        });


    }

    private void init(View view) {


        expanceTypespn = view.findViewById(R.id.expansetypeSP);

        expanceTypespn = view.findViewById(R.id.expansetypeSP);
        expaceTypes = getResources().getStringArray(R.array.Select_expence_type);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, expaceTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        expanceTypespn.setAdapter(adapter);

        fromDateTv = view.findViewById(R.id.fromDateTV);
        toDateTv = view.findViewById(R.id.toDateTv);

        helper = new SqlHelper(getContext());
        expanseList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataAdapter = new Expanse_Adapter(expanseList, getContext());
        recyclerView.setAdapter(dataAdapter);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheet_Document bottomSheet_document = new BottomSheet_Document();
                bottomSheet_document.show(getFragmentManager(), "Bottom Sheet");
            }
        });


    }

    private void toDate() {

        DatePickerDialog.OnDateSetListener dateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month + 1;
                        String currentDate = year + "/" + month + "/" + day + " 00:00:00";
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                        Date date = null;

                        try {
                            date = dateFormat.parse(currentDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        toDateTv.setText(dateFormat.format(date));
                        long dateInmilis = date.getTime();
                        geDataFromDatabase();

                    }
                };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dateSetListener, year, month, day);
        datePickerDialog.show();
    }


    public void fromDate() {
        DatePickerDialog.OnDateSetListener dateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month + 1;
                        String currentDate = year + "/" + month + "/" + day + " 00:00:00";
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                        Date date = null;

                        try {
                            date = dateFormat.parse(currentDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        fromDateTv.setText(dateFormat.format(date));
                        long dateInmilis = date.getTime();

                    }
                };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dateSetListener, year, month, day);
        datePickerDialog.show();
    }


}
