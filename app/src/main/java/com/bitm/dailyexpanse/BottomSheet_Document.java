package com.bitm.dailyexpanse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheet_Document extends BottomSheetDialogFragment {

    //String expanseType;
 //   double amount



    private Button showDocBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bottomsheet_showdocument, container, false);

        showDocBtn=view.findViewById(R.id.showDoc);
        showDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Add_Expanse.class);
                startActivity(intent);
            }
        });




        return view;
    }
}