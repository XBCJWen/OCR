package com.example.ocr.Funtion;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ocr.DB.DataBaseOpenHelper;
import com.example.ocr.R;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spnnerDigler;
    private Button tvSure;
    private DataBaseOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initView();
    }

    private void initView() {
        db = DataBaseOpenHelper.getInstance(DialogActivity.this,"ocrSql",1,new ArrayList<String>());

        spnnerDigler = (Spinner) findViewById(R.id.spnnerDigler);
        tvSure = (Button) findViewById(R.id.tvSure);

        tvSure.setOnClickListener(this);
        List<String> list1 = new ArrayList<>();
        Cursor cursor = db.query("money","money");
        while (cursor.moveToNext()){
            list1.add(cursor.getString(0));
        }
        Log.d("lllllist", String.valueOf(list1));
        ArrayAdapter adapter =new ArrayAdapter(getApplicationContext(),R.layout.spinner_items_1,list1);
        spnnerDigler.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSure:

                break;
        }
    }
}
