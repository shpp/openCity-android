package me.kowo.opencity.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.kowo.opencity.R;


public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView suggestionText;
    public RelativeLayout itemContainer;

    public ViewHolder(View itemView) {
        super(itemView);
        suggestionText = (TextView) itemView.findViewById(R.id.suggestion_text);
        itemContainer = (RelativeLayout) itemView.findViewById(R.id.item_container);
    }
}
