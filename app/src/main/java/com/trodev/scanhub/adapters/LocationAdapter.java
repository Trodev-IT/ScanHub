package com.trodev.scanhub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trodev.scanhub.R;
import com.trodev.scanhub.detail_activity.EmailQrFullActivity;
import com.trodev.scanhub.detail_activity.LocationQrFullActivity;
import com.trodev.scanhub.models.EmailModel;
import com.trodev.scanhub.models.LocationModel;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<LocationModel> list;
    private String category;

    public LocationAdapter(Context context, ArrayList<LocationModel> list, String category) {
        this.context = context;
        this.list = list;
        this.category = category;
    }


    @NonNull
    @Override
    public LocationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_qr_item, parent, false);
        return new LocationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.MyViewHolder holder, int position) {

        /*get data from database model*/
        LocationModel models = list.get(position);

        holder.from.setText(models.getLoc_from());
        holder.to.setText(models.getLoc_to());
        holder.time.setText(models.getTime());
        holder.date.setText(models.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, LocationQrFullActivity.class);
                intent.putExtra("mFrom", models.getLoc_from());
                intent.putExtra("mTo", models.getLoc_to());
                intent.putExtra("mText", models.getLoc_sms());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView from, to, date, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Here its a Time & Date Section
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);

            from = itemView.findViewById(R.id.fromTv);
            to = itemView.findViewById(R.id.toTv);


        }
    }
}
