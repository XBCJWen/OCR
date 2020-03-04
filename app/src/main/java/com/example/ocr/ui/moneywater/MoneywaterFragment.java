package com.example.ocr.ui.moneywater;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.ocr.R;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MoneywaterFragment extends Fragment {


    private View root;
    private Button btnJi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_moneywater, container, false);
        init();
        event();

        return root;
    }

    public void getPermission() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_PHONE_STATE)
                != PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_PHONE_STATE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_PHONE_STATE,}, 1);
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_PHONE_STATE,}, 1);
            }
        }
    }

    private void init() {
        btnJi = root.findViewById(R.id.btn_ji);
    }

    private void event() {
        btnJi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });
    }

}