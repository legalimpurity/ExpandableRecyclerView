package com.legalimpurity.expandablerecyclerview;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;

/**
 * Created by rajatkhanna on 27/12/16.
 */

public abstract class ItemObservable extends BaseObservable {

    //List of Child Objects for this Item, if list size is zero or null then the item will not be expandable
    public ObservableArrayList<Object> childList;

    // Is item expandable
    public ObservableBoolean expandable;

    //Listner
    protected ListListener listListener;

    public ItemObservable() {
        childList = new ObservableArrayList<>();
        expandable = new ObservableBoolean(false);
    }

    public interface ListListener {

        // Called when a list item is expanded.
        void onItemListExpanded(ItemObservable baseExpandableObservable, Object ItemObservable);

        // Called when a list item is collapsed.
        void onItemListCollapsed(ItemObservable baseExpandableObservable);

    }

    public abstract void onExpansionToggled(ExpandableItemViewHolder bindingExpandableItemViewHolder, int index, boolean expanded);

    //Getters and Setters

    public ObservableBoolean getExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expand) {
        this.expandable.set(expand);
    }

    public ObservableArrayList<Object> getChildList() {
        return childList;
    }

    public void setListListener(ListListener listListener) {
        this.listListener = listListener;
    }

}
