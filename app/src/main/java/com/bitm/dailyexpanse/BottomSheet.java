package com.bitm.dailyexpanse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheet extends BottomSheetDialogFragment {

    private TextView expanseTypeTV,amountTV,dateTV;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.bottom_sheet,container,false);

      init(view);



        return view;
    }

    public void showData(String type,double amount,String toDate,String fromDate) {





        expanseTypeTV.setText(type);
        amountTV.setText(String.valueOf(amount));
        dateTV.setText(fromDate +" to " + toDate);







    }

    private void init(View view) {
       expanseTypeTV=view.findViewById(R.id.expanseTypeTv);
       amountTV=view.findViewById(R.id.amountTV);
       dateTV=view.findViewById(R.id.dateTv);


    }
}
