package com.example.ocr.ui.moneywater;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.ocr.JiActivity;
import com.example.ocr.R;

public class MoneywaterFragment extends Fragment {

    private MoneywaterViewModel dashboardViewModel;
    private View root;
    private Button btn_ji;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(MoneywaterViewModel.class);
        root = inflater.inflate(R.layout.fragment_moneywater, container, false);
        init();
        event();

        return root;
    }

    private void init() {
        btn_ji = root.findViewById(R.id.btn_ji);
    }
    private void event() {
        btn_ji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), JiActivity.class);
                startActivity(intent);
            }
        });
    }


}