package com.example.ocr.ui.money;

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

public class MoneyFragment extends Fragment {

    private MoneyViewModel homeViewModel;
    private Button btn_ji;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(MoneyViewModel.class);
        root = inflater.inflate(R.layout.fragment_money, container, false);
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
                Intent intent=new Intent(getContext(), JiActivity.class);
                startActivity(intent);
            }
        });
    }

}