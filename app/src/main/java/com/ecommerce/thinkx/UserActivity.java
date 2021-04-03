package com.ecommerce.thinkx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    BottomSheetBehavior<RelativeLayout> relativeLayout; //Generics
    Button mAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        relativeLayout = BottomSheetBehavior.from(findViewById(R.id.bottomSheet));
        relativeLayout.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mAdd = findViewById(R.id.add_item);
        mAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_item) {
            relativeLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}