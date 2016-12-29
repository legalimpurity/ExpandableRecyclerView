package com.legalimpurity.expandablerecyclerview.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.legalimpurity.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.legalimpurity.expandablerecyclerview.demo.databinding.ActivityMainBinding;
import com.legalimpurity.expandablerecyclerview.demo.objects.Level1Item;
import com.legalimpurity.expandablerecyclerview.demo.objects.Level2Item;
import com.legalimpurity.expandablerecyclerview.demo.objects.Level3Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private ExpandableRecyclerViewAdapter mExpandableRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(mainBinding.toolbar);

        Eventhandlers handlers = new Eventhandlers();
        mainBinding.setHandlers(handlers);
        setRecyclerView();
    }

    private void setRecyclerView()
    {
        ArrayList mDataList = Utility.generateFirstLevelData(this);

        mExpandableRecyclerViewAdapter = new ExpandableRecyclerViewAdapter(this, mDataList) {
            @NonNull
            @Override
            public int getItemLayout(int level) {
                switch (level) {
                    case 1:
                        return R.layout.item_level1;
                    case 2:
                        return R.layout.item_level2;
                    case 3:
                        return R.layout.item_level3;
                }
                return -1;
            }

            @Override
            public int getVariable(Object o, int index) {
                if (o instanceof Level1Item)
                    return BR.Level1Item;
                else if (o instanceof Level2Item)
                    return BR.Level2Item;
                else if (o instanceof Level3Item)
                    return BR.Level3Item;
                return -1;
            }

            @Override
            public int getItemViewType(Object t) {
                if (t instanceof Level1Item)
                    return 1;
                if (t instanceof Level2Item)
                    return 2;
                if (t instanceof Level3Item)
                    return 3;
                return super.getItemViewType(t);
            }
        };

        mainBinding.listData.setAdapter(mExpandableRecyclerViewAdapter);
        mainBinding.listData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        mExpandableRecyclerViewAdapter.setExpandCollapseListener(new ExpandableRecyclerViewAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {

            }

            @Override
            public void onListItemCollapsed(int position) {
                // Implement any action to be performed after list is collapsed
            }
        });
    }
}
