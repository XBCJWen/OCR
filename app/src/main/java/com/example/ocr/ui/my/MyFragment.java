package com.example.ocr.ui.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.ocr.R;
import com.example.ocr.ui.moneywater.MoneywaterViewModel;

public class MyFragment extends Fragment {

    private MoneywaterViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(MoneywaterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my, container, false);

        return root;
    }

}
