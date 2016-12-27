package com.legalimpurity.expandablerecyclerview.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.legalimpurity.expandablerecyclerview.demo.databinding.ActivityMainBinding;
import com.legalimpurity.expandablerecyclerview.demo.objects.BaseItem;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private BaseItem baseItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setItem(baseItem);

        setSupportActionBar(mainBinding.toolbar);

        Eventhandlers handlers = new Eventhandlers();
        mainBinding.setHandlers(handlers);
        setRecyclerView();
    }

    private void setRecyclerView()
    {

    }

}
