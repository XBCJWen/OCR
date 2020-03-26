package com.example.ocr.ui.tabbar;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ocr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment{

    private Spinner spinerUser;
    private EditText edtMoney;
    private Button btnInput;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_my, container, false);
        initData();
        event();
        return root;
    }

    private void event() {

    }

    private void initData() {
        spinerUser = root.findViewById(R.id.spiner_user);
        edtMoney = root.findViewById(R.id.edt_money);
        btnInput = root.findViewById(R.id.btn_add_user);

    }
}
