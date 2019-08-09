package com.ssky.example.hairdesign.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ssky.example.hairdesign.R;
import com.ssky.example.hairdesign.fragment.MainFragment;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    RadioButton mRadioButton1;
    RadioButton mRadioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMainFragment();
//        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.rg_title);
//        mRadioButton1 = (RadioButton) findViewById(R.id.rb_recommend);
//        mRadioButton2 = (RadioButton) findViewById(R.id.rb_classify);
//        radioGroup.setOnCheckedChangeListener(this);
    }

    /**
     * 初始化内容Fragment
     *
     * @return void
     */
    public void initMainFragment() {
       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
       MainFragment mFragment = MainFragment.newInstance();
       transaction.replace(R.id.main_act_container, mFragment, mFragment.getClass().getSimpleName());
       transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == mRadioButton1.getId()){
            mRadioButton1.setBackgroundResource(R.drawable.btn_top_title_bac_selector);
            mRadioButton2.setBackgroundResource(0);
        } else if (checkedId == mRadioButton2.getId()) {
            mRadioButton2.setBackgroundResource(R.drawable.btn_top_title_bac_selector);
            mRadioButton1.setBackgroundResource(0);
        }
    }
}
