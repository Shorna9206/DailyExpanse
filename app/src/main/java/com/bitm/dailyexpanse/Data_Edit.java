package com.bitm.dailyexpanse;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Data_Edit extends AppCompatActivity {

    private Spinner expanseTypeSp;
    private TextView types;
    private EditText amountEt, dateEt, timeEt;
    private ImageView imageIv;
    private Button updateBtn, imageBtn;
    ExpenseFragment expenseFragment;
    int id;
    List<Expanse> expanseList;
    Cursor cursor;
    SqlHelper helper;
    byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data__edit);
        setTitle("Data Edit");


        init();
        setData();


    }

    private void setData() {
        String sql = "SELECT * FROM " + helper.TABLE_NAME + " WHERE Id =" + id;

        cursor = helper.showData(sql);
        while (cursor.moveToNext()) {
            amountEt.setText(cursor.getString(cursor.getColumnIndex(helper.COL_AMOUNT)));
            dateEt.setText(cursor.getString(cursor.getColumnIndex(helper.COL_DATE)));
            timeEt.setText(cursor.getString(cursor.getColumnIndex(helper.COL_TIME)));
            types.setText(cursor.getString(cursor.getColumnIndex(helper.COL_EXPANSE_TYPE)));
            byte[] image = cursor.getBlob(5);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

            imageIv.setImageBitmap(bitmap);

  /*          ByteArrayInputStream imageStream = new ByteArrayInputStream(b);
//Bitmap theImage = BitmapFactory.decodeStream(imageStream);
//image.setImageBitmap(theImage);
            image.setImageBitmap(bp);*/


        }


    }

    private void init() {
        expanseList = new ArrayList<>();
        helper = new SqlHelper(this);
        String getId = getIntent().getStringExtra("id");
        id = Integer.parseInt(getId);
        Toast.makeText(this, "id :" + id, Toast.LENGTH_SHORT).show();
        amountEt = findViewById(R.id.expenseamoutET);
        types = findViewById(R.id.expanseTypeTV);
        dateEt = findViewById(R.id.expensedateET);
        timeEt = findViewById(R.id.expensetimeET);
        imageIv = findViewById(R.id.choosedocumentIV);
        expanseTypeSp = findViewById(R.id.expansetypeSP);
        imageBtn = findViewById(R.id.choosedocumentBTN);

        updateBtn = findViewById(R.id.editexpenceBTN);
        dateEt.setFocusable(false);
        timeEt.setFocusable(false);
        amountEt.setEnabled(false);
        expanseTypeSp.setVisibility(View.GONE);
        updateBtn.setVisibility(View.GONE);
        expenseFragment = new ExpenseFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.updatemenu:

                update();

                //Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();

                break;
            case R.id.deletemenu:
                String sql = "DELETE FROM " + helper.TABLE_NAME + " WHERE Id = " + id;
                helper.deleteRow(sql);
                Toast.makeText(this, "Successfully Delete", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("check", 1);
                startActivity(intent);

                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void update() {
        amountEt.setEnabled(true);
        expanseTypeSp.setVisibility(View.VISIBLE);
        updateBtn.setVisibility(View.VISIBLE);
        imageBtn.setVisibility(View.VISIBLE);

        types.setVisibility(View.GONE);
        String[] expaceTypes;
        ArrayAdapter<String> adapter;


        expaceTypes = getResources().getStringArray(R.array.Select_expence_type);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, expaceTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        expanseTypeSp.setAdapter(adapter);


        timeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });

        dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, 0);

                image = imageViewToByte(imageIv);
                //Toast.makeText(Data_Edit.this, "123", Toast.LENGTH_SHORT).show();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 0) {
                Uri uri = data.getData();
                imageIv.setImageURI(uri);

            }

        }

    }

    private void updateData() {
        String amount, time, date, expanseType;
        image = imageViewToByte(imageIv);
        amount = amountEt.getText().toString().trim();
        date = dateEt.getText().toString().trim();
        time = timeEt.getText().toString().trim();
        expanseType = expanseTypeSp.getSelectedItem().toString().trim();


        helper.updateValues(id, expanseType, amount, date, time, image);

        //String sql= "UPDATE "+helper.TABLE_NAME+" SET Expanse_type="+expanseType+" WHERE Id ="+id;
//     ,Amount ="+amount+",Date ="+date+",Time ="+time+",Image ="+image+"
        //  helper.updateValue(sql);

        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

        int i = 1;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("check", 1);
        startActivity(intent);

        //finish();


    }

    private byte[] imageViewToByte(ImageView imageIv) {
        Bitmap bitmap = ((BitmapDrawable) imageIv.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArrey = stream.toByteArray();
        return byteArrey;

    }


    private void openDatePicker() {
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
                        dateEt.setText(dateFormat.format(date));
                        long dateInmilis = date.getTime();

                    }
                };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Data_Edit.this, dateSetListener, year, month, day);
        datePickerDialog.show();


    }

    private void openTimePicker() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Data_Edit.this);
        View view = getLayoutInflater().inflate(R.layout.custom_time_picker, null);

        Button doneBtn = view.findViewById(R.id.doneBtn);
        final TimePicker timePicker = view.findViewById(R.id.timePicker);

        builder.setView(view);

        final Dialog dialog = builder.create();
        dialog.show();

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");

                @SuppressLint({"NewApi", "LocalSuppress"}) int hour = timePicker.getHour();
                @SuppressLint({"NewApi", "LocalSuppress"}) int min = timePicker.getMinute();

                Time time = new Time(hour, min, 0);

                timeEt.setText(timeFormat.format(time));
                dialog.dismiss();


            }
        });


    }


}
