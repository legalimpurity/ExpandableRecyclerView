# ExpandableRecyclerView
Expandable Recycler View is a recycler view which can help you design complex flows easily through its implementation. Its been designed in such a way that minimum input is supposed to be provided from the users side.

Here is a demo on how it works.

[![IMAGE ALT TEXT](http://img.youtube.com/vi/Mt1a9oie768/0.jpg)](http://www.youtube.com/watch?v=Mt1a9oie768 "Demo Video")

Setup
------
Add this to your build.gradle dependencies for the project where you want to use it.
```Android
dependencies {
  ...
  compile 'com.legalimpurity.expandablerecyclerview:expandablerecyclerview:1.0.2'
}
```

Also, add the following to your build.gradle file for the project to enable data binding.
```Android
android {
  ...
  dataBinding {
        enabled = true
    }
}
```

Usage
------
So you have multiple levels of data to display. Say you have countries then states and then districts to be shown in a sequential order. Countries will contain states which will contain districts. Therefore, you will create three classes, inheriting ItemObservable.

```Android
public void onLevel1ItemClick()
   {
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
    public void onExpansionToggled(ExpandableItemViewHolder bindingExpandableItemViewHolder, int index, boolean expanded)
    {
        ItemLevel1Binding binding = (ItemLevel1Binding) bindingExpandableItemViewHolder.getBinding();
        Utility.rotateImage(binding.arrowLevel1, expanded);
    }

    
```
It has one function to be implemented, onExpansionToggled. It notifies whenever that item is actually toggled. You will have to implement your own click listner on the whole item's UI or a particular part depending on your requirements.
On Click, the library expects you get the data for the next list and whenever it is available, call listListener.onItemListCollapsed. You also need to track that if its the second click, then you might wanna collapse that item. Again, that is all based on your requirement. Go through the demo project for a more detailed explanation.

The constructor of the Adapter is as follows.

```Android
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

```

The getItemLayout is to be implemented for the layout you want to inflate for a particular a particular level. In, getItemViewType you again return the level of the objects and in getVariable, you return their BR objects.
