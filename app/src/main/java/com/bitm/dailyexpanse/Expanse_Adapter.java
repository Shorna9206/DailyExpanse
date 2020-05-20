package com.bitm.dailyexpanse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Expanse_Adapter extends RecyclerView.Adapter<Expanse_Adapter.ViewHolder> {
    List<Expanse> expanseList;
    Context context;

    public Expanse_Adapter(List<Expanse> expanseList, Context context) {
        this.expanseList = expanseList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_expenselistrecycleview, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Expanse expanse = expanseList.get(position);


        holder.expanseTypeTv.setText(expanse.getExpnanseType());
        holder.expanseDateTv.setText(expanse.getDate());
        holder.expanseAmountTv.setText("৳ " + expanse.getAmount());
        final int id = expanse.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "hello"+id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Data_Edit.class);
                intent.putExtra("id", String.valueOf(id));
                //   intent.putExtra("image",expanse.getImage());

                expanseList.clear();
                context.startActivity(intent);

            }
        });

        holder.goIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Data_Edit.class);
                intent.putExtra("id", String.valueOf(id));
                //   intent.putExtra("image",expanse.getImage());

                expanseList.clear();
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return expanseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView expanseTypeTv, expanseDateTv, expanseAmountTv;
        private ImageView goIV;


        public ViewHolder(@NonNull View itemView) {


            super(itemView);

            expanseTypeTv = itemView.findViewById(R.id.expansetypeRTV);
            expanseAmountTv = itemView.findViewById(R.id.amountTV);
            expanseDateTv = itemView.findViewById(R.id.dateofExpanceRTV);
            goIV = itemView.findViewById(R.id.goIV);

        }
    }
}
