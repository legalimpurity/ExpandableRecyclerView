package com.legalimpurity.expandablerecyclerview;

import android.util.SparseArray;

/**
 * Created by rajatkhanna on 27/12/16.
 */

public class Utility {
    private SparseArray<Object> typeSArr = new SparseArray<>();
    public int getIntType(Object type) {
        int index = typeSArr.indexOfValue(type);
        if (index == -1) {
            index = typeSArr.size();
            typeSArr.put(index, type);
        }
        return index;
    }
}
