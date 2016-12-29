package com.legalimpurity.expandablerecyclerview.demo.objects;

import android.content.Context;
import android.databinding.ObservableField;

import com.legalimpurity.expandablerecyclerview.ExpandableItemViewHolder;
import com.legalimpurity.expandablerecyclerview.ItemObservable;
import com.legalimpurity.expandablerecyclerview.demo.Utility;
import com.legalimpurity.expandablerecyclerview.demo.databinding.ItemLevel1Binding;

import java.util.ArrayList;

/**
 * Created by rajatkhanna on 27/12/16.
 */
//Every class should extend ItemObservable
public class Level1Item extends ItemObservable {
    public ObservableField<String> name;

    private Context _ctx;

    public Level1Item(Context _ctx, String title) {
        this.name = new ObservableField<>();
        this._ctx = _ctx;
        name.set(title);
    }

    //Binded to click event via DataBinding
    public void onLevel1ItemClick() {
        if (listListener != null) {
            if (expandable.get())
                listListener.onItemListCollapsed(this);
            else
            {
                // Implement necessary actions to be performed after the item is clicked to get the data of next level.
                // (E.g. : Hit API, wink wink)
                // After obtaining data, send the data back via the callback Object.
                ArrayList<Level2Item> secondLevelData = Utility.generateSecondLevelData(_ctx, name.get());
                listListener.onItemListExpanded(this,secondLevelData);
            }
        }
    }

    @Override
    public void onExpansionToggled(ExpandableItemViewHolder bindingExpandableItemViewHolder, int index, boolean expanded) {
        ItemLevel1Binding binding = (ItemLevel1Binding) bindingExpandableItemViewHolder.getBinding();
        Utility.rotateImage(binding.arrowLevel1, expanded);
    }

}
