package com.example.shows.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.shows.MainActivity;
import com.example.shows.R;
import com.example.shows.databinding.ActivitySearchingBinding;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.viewModel.PerformanceViewModel;
import com.example.shows.views.login.recyclerViews.RecyclerAdapterPerformance;
import com.example.shows.views.login.recyclerViews.RecyclerAdapterPerformanceSearch;

import java.util.ArrayList;
import java.util.List;

public class SearchingActivity extends AppCompatActivity {

    ActivitySearchingBinding searchingBinding;
    PerformanceViewModel performanceViewModel;

    String criteria = " ";

    RecyclerAdapterPerformanceSearch recyclerAdapterPerformanceSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);


        searchingBinding = DataBindingUtil.setContentView(this, R.layout.activity_searching);
        searchingBinding.setLifecycleOwner(this);

        recyclerAdapterPerformanceSearch = new RecyclerAdapterPerformanceSearch();

        performanceViewModel = ViewModelProviders.of(this).get(PerformanceViewModel.class);

        performanceViewModel.returnCurrentLiveDataPerfSearch().observeForever( new Observer<List<Performance>>() {
            @Override
            public void onChanged(List<Performance> performanceList) {
                if(performanceList!=null){
                    recyclerAdapterPerformanceSearch = new RecyclerAdapterPerformanceSearch();
                    recyclerAdapterPerformanceSearch.setPerformanceList(performanceList);
                    searchingBinding.searchingRecycler.setAdapter(recyclerAdapterPerformanceSearch);
                    searchingBinding.searchingRecycler.setLayoutManager(new LinearLayoutManager(SearchingActivity.this));
                }
            }
        });

   //     performanceViewModel.chaaaaange();
        adapterSpinner();

    }

    public void adapterSpinner(){
        List<String> collectionValueSpinner = new ArrayList<>();
        collectionValueSpinner.add("Actor");
        collectionValueSpinner.add("Scenarist");
        collectionValueSpinner.add("Gener");


        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySortSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,collectionValueSpinner);
        categorySpinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener onItemSelectedListener =
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedItem = adapterView.getSelectedItem().toString();
                        //searchingBinding.searchingText.setText(selectedItem);
                        criteria = selectedItem;

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                };

        categorySpinner.setOnItemSelectedListener(onItemSelectedListener);

    }


    public void searchFunc(View view){
        if(searchingBinding.searchingText.getText().toString() != ""){
           List<Performance> performanceList =  performanceViewModel.
                   getPerformancesByCriteriaSearch(criteria, searchingBinding.searchingText.getText().toString());
            updateUi(performanceList);
        }
    }

    public void updateUi(List<Performance> performances){
        recyclerAdapterPerformanceSearch = new RecyclerAdapterPerformanceSearch();
        recyclerAdapterPerformanceSearch.setPerformanceList(performances);
        searchingBinding.searchingRecycler.setAdapter(recyclerAdapterPerformanceSearch);
        searchingBinding.searchingRecycler.setLayoutManager(new LinearLayoutManager(SearchingActivity.this));
    }

}