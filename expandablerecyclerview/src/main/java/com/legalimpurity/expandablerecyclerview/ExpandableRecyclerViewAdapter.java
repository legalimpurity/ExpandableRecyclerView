package com.legalimpurity.expandablerecyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajatkhanna on 27/12/16.
 */

public abstract class ExpandableRecyclerViewAdapter extends RecyclerView.Adapter<ExpandableItemViewHolder>
        implements ItemObservable.ListListener {

    // Vairables
    private Context _ctx;
    private List<Object> rootDataList;
    private ArrayList<RecyclerView> recyclerViews;

    private int itemType;

    private Utility util = new Utility();
    private ExpandCollapseListener mExpandCollapseListener;

    private LayoutInflater mLayoutInflater;

    //Constructor
    public ExpandableRecyclerViewAdapter(Context context, List<Object> rootDataList) {
        this._ctx = context;
        this.rootDataList = rootDataList;
        recyclerViews = new ArrayList<>();
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Override Functions
    @Override
    public ExpandableItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExpandableItemViewHolder(DataBindingUtil.inflate(mLayoutInflater, getItemLayout(itemType), parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        itemType = getItemViewType(rootDataList.get(position));
        return util.getIntType(itemType);
    }

    public int getItemViewType(Object t) {
        return -1;
    }

    @Override
    public void onBindViewHolder(ExpandableItemViewHolder holder, int position) {
        Object o = rootDataList.get(position);
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(getVariable(o, position), o);
        binding.executePendingBindings();
        if (o instanceof ItemObservable) {
            ItemObservable baseExpandableObservable = (ItemObservable) o;
            ObservableArrayList<Object> childList = baseExpandableObservable.getChildList();
            if (childList != null)
                baseExpandableObservable.setListListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return rootDataList == null ? 0 : rootDataList.size();
    }

    @Override
    public void onItemListExpanded(final ItemObservable baseExpandableObservable,Object levelData) {
        if(levelData!=null) {
            final int indexOf = rootDataList.indexOf(baseExpandableObservable);
            if (!isDataListEmpty() && indexOf >= 0 && indexOf < rootDataList.size()) {
                ObservableArrayList modifyingObject = ((ItemObservable) rootDataList.get(indexOf)).getChildList();
                modifyingObject.clear();
                ArrayList<Object> actualLevelData = (ArrayList<Object>) levelData;
                for (int i = 0; i < actualLevelData.size(); i++) {
                    modifyingObject.add(actualLevelData.get(i));
                }
            }
            expandItem(indexOf, baseExpandableObservable, true);
        }
    }

    @Override
    public void onItemListCollapsed(ItemObservable baseExpandableObservable) {
        int indexOf = rootDataList.indexOf(baseExpandableObservable);
        collapseListItem(indexOf, baseExpandableObservable, true);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerViews.add(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerViews.remove(recyclerView);
    }

    // Implemented Functions

    public void expandItem(int parentIndex, ItemObservable itemExpandableObservable, boolean expansionTriggeredByListItemClick) {
        if (parentIndex >= 0 && parentIndex < rootDataList.size() && !itemExpandableObservable.expandable.get()) {

            ObservableArrayList<Object> childList = itemExpandableObservable.getChildList();
            final int childSize = childList.size();
            for (int i = 0; i < childSize; i++) {
                Object o = childList.get(i);
                int newIndex = parentIndex + i + 1;
                rootDataList.add(newIndex, o);
            }
            notifyItemRangeInserted(parentIndex + 1, childSize);
            int positionStart = parentIndex + childSize;
            if (parentIndex != rootDataList.size() - 1)
                notifyItemRangeChanged(positionStart, rootDataList.size() - positionStart);
            itemExpandableObservable.setExpandable(true);
            if (!recyclerViews.isEmpty()) {
                ExpandableItemViewHolder expandableItemViewHolderForAdapterPosition = (ExpandableItemViewHolder) recyclerViews.get(0).findViewHolderForAdapterPosition(parentIndex);
                itemExpandableObservable.onExpansionToggled(expandableItemViewHolderForAdapterPosition, parentIndex, true);
            }
            if (expansionTriggeredByListItemClick && mExpandCollapseListener != null) {
                mExpandCollapseListener.onListItemExpanded(parentIndex);
            }
        }
    }

    private void collapseListItem(int parentIndex, ItemObservable itemExpandableObservable, boolean collapseTriggeredByListItemClick) {
        if (itemExpandableObservable.expandable.get()) {
            ObservableArrayList<Object> childItemList = itemExpandableObservable.getChildList();
            if (childItemList != null && !childItemList.isEmpty()) {
                int childListItemCount = childItemList.size();
                for (int i = childListItemCount - 1; i >= 0; i--) {
                    int index = parentIndex + i + 1;
                    Object o = rootDataList.get(index);
                    if (o instanceof ItemObservable) {
                        ItemObservable parentListItem;
                        try {
                            parentListItem = (ItemObservable) o;
                            collapseListItem(index, parentListItem, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    rootDataList.remove(index);
                }

                notifyItemRangeRemoved(parentIndex + 1, childListItemCount);
                itemExpandableObservable.setExpandable(false);
                if (!recyclerViews.isEmpty()) {
                    ExpandableItemViewHolder expandableItemViewHolderForAdapterPosition = (ExpandableItemViewHolder) recyclerViews.get(0).findViewHolderForAdapterPosition(parentIndex);
                    itemExpandableObservable.onExpansionToggled(expandableItemViewHolderForAdapterPosition, parentIndex, false);
                }
                notifyItemRangeChanged(parentIndex + 1, rootDataList.size() - parentIndex - 1);
            }

            if (collapseTriggeredByListItemClick && mExpandCollapseListener != null) {
                int expandedCountBeforePosition = getExpandedItemCount(parentIndex);
                mExpandCollapseListener.onListItemCollapsed(parentIndex - expandedCountBeforePosition);
            }
        }
    }

    private int getExpandedItemCount(int position) {
        if (position == 0) {
            return 0;
        }

        int expandedCount = 0;
        for (int i = 0; i < position; i++) {
            Object listItem = getListItem(i);
            if (!(listItem instanceof ItemObservable)) {
                expandedCount++;
            }
        }
        return expandedCount;
    }

    protected Object getListItem(int position) {
        boolean indexInRange = position >= 0 && position < rootDataList.size();
        if (indexInRange) {
            return rootDataList.get(position);
        } else {
            return null;
        }
    }

    private boolean isDataListEmpty() {
        return (rootDataList == null || rootDataList.isEmpty()) ? true : false;
    }

    public interface ExpandCollapseListener {

        void onListItemExpanded(int position);

        void onListItemCollapsed(int position);

    }

    // Abstract Function Required to be implemented
    public abstract int getVariable(Object o, int index);

    @LayoutRes
    @NonNull
    public abstract int getItemLayout(int level);

    //Setters

    public void setExpandCollapseListener(ExpandCollapseListener mExpandCollapseListener) {
        this.mExpandCollapseListener = mExpandCollapseListener;
    }
}
