package com.legalimpurity.expandablerecyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by rajatkhanna on 27/12/16.
 */

public class ExpandableItemViewHolder<MyView extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private MyView mBinding;

    public ExpandableItemViewHolder(MyView binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }

    public MyView getBinding() {
        return mBinding;
    }
}
