package org.smartregister.chw.core.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.listener.NavigationListener;
import org.smartregister.chw.core.model.NavigationOption;
import org.smartregister.chw.core.utils.CoreConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.MyViewHolder> {

    private List<NavigationOption> navigationOptionList;
    private String selectedView = CoreConstants.DrawerMenu.ALL_FAMILIES;
    private View.OnClickListener onClickListener;
    private Context context;
    private Map<String, Class> registeredActivities;
    private DrawerLayout drawerLayout;

    public NavigationAdapter(List<NavigationOption> navigationOptions, Activity context, Map<String, Class> registeredActivities) {
        this(navigationOptions, context, registeredActivities, null);
    }

    public NavigationAdapter(List<NavigationOption> navigationOptions, Activity context, Map<String, Class> registeredActivities, DrawerLayout drawerLayout) {
        this.navigationOptionList = navigationOptions;
        this.context = context;
        this.onClickListener = new NavigationListener(context, this);
        this.registeredActivities = registeredActivities;
        this.drawerLayout = drawerLayout;
    }

    public String getSelectedView() {
        if (selectedView == null || selectedView.equals(""))
            setSelectedView(CoreConstants.DrawerMenu.ALL_FAMILIES);

        return selectedView;
    }

    public void setSelectedView(String selectedView) {
        this.selectedView = selectedView;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.navigation_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NavigationOption model = navigationOptionList.get(position);
        holder.tvName.setText(context.getResources().getText(model.getTitleID()));
        if (model.getRegisterCount() >= 0) {
            holder.tvCount.setText(String.format(Locale.getDefault(), "%d", model.getRegisterCount()));
        }else{
            holder.tvCount.setText(null);
        }
        holder.ivIcon.setImageResource(model.getResourceID());

        holder.getView().setTag(model.getMenuTitle());


        if (selectedView != null && selectedView.equals(model.getMenuTitle()) && model.getResourceID() == model.getResourceActiveID()) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.navigation_item_selected));
            holder.tvCount.setTextColor(context.getResources().getColor(R.color.navigation_item_unselected));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.navigation_item_unselected));
            holder.ivIcon.setImageResource(model.getResourceID());
        }else if (selectedView != null && selectedView.equals(model.getMenuTitle())){
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            holder.tvCount.setTextColor(context.getResources().getColor(R.color.navigation_item_selected));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.navigation_item_selected));
            holder.ivIcon.setImageResource(model.getResourceActiveID());
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            holder.tvCount.setTextColor(context.getResources().getColor(R.color.navigation_item_unselected));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.navigation_item_unselected));
            holder.ivIcon.setImageResource(model.getResourceID());
        }
    }

    @Override
    public int getItemCount() {
        return navigationOptionList.size();
    }

    public Map<String, Class> getRegisteredActivities() {
        return registeredActivities;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvCount;
        public ImageView ivIcon;

        private View myView;

        private MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvCount = view.findViewById(R.id.tvCount);
            ivIcon = view.findViewById(R.id.ivIcon);

            if (onClickListener != null) {
                view.setOnClickListener(v -> {
                    if (drawerLayout != null) drawerLayout.closeDrawers();
                    onClickListener.onClick(v);
                });
            }

            myView = view;
        }

        public View getView() {
            return myView;
        }
    }

}


