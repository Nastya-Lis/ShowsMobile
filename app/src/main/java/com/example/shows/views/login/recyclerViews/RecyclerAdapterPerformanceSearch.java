package com.example.shows.views.login.recyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shows.R;
import com.example.shows.model.database.entity.Performance;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterPerformanceSearch extends RecyclerView.Adapter<RecyclerAdapterPerformanceSearch.RecyclerPerform>{
    List<Performance> performanceList;
    List<Performance> performanceListCopy;

    public void setPerformanceList(List<Performance> performanceList) {
        this.performanceList = performanceList;
        notifyDataSetChanged();
    }

    public class RecyclerPerform extends RecyclerView.ViewHolder {
        TextView name,price,date,rating;
        public RecyclerPerform(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameElementId);
            price = itemView.findViewById(R.id.priceElementId);
            date = itemView.findViewById(R.id.dateElementId);
            rating = itemView.findViewById(R.id.ratingElementId);
        }
    }


    public interface OnPerformanceClickListener {
        void onPerformanceClick(Performance performance);
    }
    public RecyclerAdapterPerformance.OnPerformanceClickListener onPerformanceClickListener;
    //   public OnPerformanceLongClickListener onPerformanceLongClickListener;



    public void setOnPerformanceClickListener(RecyclerAdapterPerformance.OnPerformanceClickListener onPerformanceClickListener){
        this.onPerformanceClickListener = onPerformanceClickListener;
    }


    @NonNull
    @Override
    public RecyclerAdapterPerformanceSearch.RecyclerPerform onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_performance_template,
                parent,false);
        return new RecyclerAdapterPerformanceSearch.RecyclerPerform(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterPerformanceSearch.RecyclerPerform holder, int position) {
        ArrayList<Performance> performancesArrayList = (ArrayList<Performance>) performanceList;
        Performance performance = performancesArrayList.get(position);

        holder.name.setText(performance.getName());
        holder.price.setText(String.valueOf(performance.getPrice()));
        holder.rating.setText(String.valueOf(performance.getRating()));
        holder.date.setText(String.valueOf(performance.getDate()));

        if(onPerformanceClickListener !=null){
            holder.itemView.setOnClickListener(view -> onPerformanceClickListener.onPerformanceClick(performance));
        }
    }

    @Override
    public int getItemCount() {
        return  performanceList == null ? 0 : performanceList.size();
    }
}

