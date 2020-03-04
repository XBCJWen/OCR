package com.example.ocr.ui.my;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.ocr.LoginUser.LoginActivity;
import com.example.ocr.R;

public class MyFragment extends Fragment {
    private Context mContext;
    private LinearLayout la_user;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_my, container, false);
        init();
        event();
        return root;
    }



    private void event() {
        la_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);

//                ((Activity)mContext).overridePendingTransition(R.anim.la_in,
//                        R.anim.la_out);
            }
        });
    }

    private void init() {
        la_user = root.findViewById(R.id.la_user);
        mContext = getContext();


    }

}
