package com.legalimpurity.expandablerecyclerview.demo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.legalimpurity.expandablerecyclerview.demo.objects.Level1Item;
import com.legalimpurity.expandablerecyclerview.demo.objects.Level2Item;
import com.legalimpurity.expandablerecyclerview.demo.objects.Level3Item;

import java.util.ArrayList;

/**
 * Created by rajatkhanna on 28/12/16.
 */

public class Utility {

    public static ArrayList<Level2Item> generateFirstLevelData(Context _ctx)
    {
        ArrayList mDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Level1Item companyVm = new Level1Item(_ctx, "Level1 : Entry " + i + "; ");
            mDataList.add(companyVm);
        }
        return mDataList;
    }

    public static ArrayList<Level2Item> generateSecondLevelData(Context _ctx, String Prefix)
    {
        ArrayList mDataList = new ArrayList<Level2Item>();
        for (int i = 0; i < 5; i++) {
            Level2Item companyVm = new Level2Item(_ctx, Prefix + "Level2 : Entry " + i + "; ");
            mDataList.add(companyVm);
        }
        return mDataList;
    }

    public static ArrayList<Level3Item> generateThirdLevelData(Context _ctx, String Prefix)
    {
        ArrayList mDataList = new ArrayList<Level3Item>();
        for (int i = 0; i < 2; i++) {
            Level3Item companyVm = new Level3Item(_ctx, Prefix + "Level3 : Entry " + i + "; ");
            mDataList.add(companyVm);
        }
        return mDataList;
    }

    public static void rotateImage(ImageView img, boolean clockAnticlock) {
        float start, target;
        if (clockAnticlock) {
            start = 0f;
            target = 90f;
        } else {
            start = 90f;
            target = 0f;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img, View.ROTATION, start, target);
        objectAnimator.setDuration(200);
        objectAnimator.start();
    }

}
