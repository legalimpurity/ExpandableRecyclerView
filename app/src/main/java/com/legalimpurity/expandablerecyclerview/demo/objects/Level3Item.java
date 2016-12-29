package com.legalimpurity.expandablerecyclerview.demo.objects;

import android.content.Context;
import android.databinding.ObservableField;

import com.legalimpurity.expandablerecyclerview.ExpandableItemViewHolder;
import com.legalimpurity.expandablerecyclerview.ItemObservable;
import com.legalimpurity.expandablerecyclerview.demo.databinding.ItemLevel3Binding;

/**
 * Created by rajatkhanna on 27/12/16.
 */

//Every class should extend ItemObservable
public class Level3Item extends ItemObservable {
    public ObservableField<String> name;

    private Context _ctx;

    public Level3Item(Context _ctx, String title) {
        this.name = new ObservableField<>();
        this._ctx = _ctx;
        name.set(title);
    }

    //Binded to click event via DataBinding
    public void onLevel3ItemClick() {
        if (listListener != null) {
            if (expandable.get())
                listListener.onItemListCollapsed(this);
            else{
                // We dont have anymore sublevels beyond this, also in the constructor constructor for number of leves, we wrote three,
                // So here we give second levelData argument as null.
                listListener.onItemListExpanded(this,null);
            }
        }
    }

    @Override
    public void onExpansionToggled(ExpandableItemViewHolder bindingExpandableItemViewHolder, int index, boolean expanded) {
        ItemLevel3Binding binding = (ItemLevel3Binding) bindingExpandableItemViewHolder.getBinding();
    }
}