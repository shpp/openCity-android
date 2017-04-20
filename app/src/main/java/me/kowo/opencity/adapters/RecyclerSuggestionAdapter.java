package me.kowo.opencity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import me.kowo.opencity.R;
import me.kowo.opencity.eventbus.Event;
import me.kowo.opencity.eventbus.EventMessage;
import me.kowo.opencity.holders.ViewHolder;
import me.kowo.opencity.models.Place;


public class RecyclerSuggestionAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<Place> places;
    private Context context;
    private RecyclerSuggestionAdapter recyclerSuggestionAdapter;

    public RecyclerSuggestionAdapter(Context context) {
        this.context = context;
        this.places = new ArrayList<>();
        recyclerSuggestionAdapter = this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_suggestion_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (places.get(position).getMap_lat() != null) {
            holder.itemContainer.setOnClickListener(v -> {
                EventBus.getDefault().post((new Event()).setEventMessage(EventMessage.SELECT_ITEM)
                        .setTransferObject(places.get(position)));
                recyclerSuggestionAdapter.clear();
            });
        }
        if (places.get(position).getShortName() == null || places.get(position).getShortName().equals("")) {
            holder.suggestionText.setText(places.get(position).getName());
        } else {
            holder.suggestionText.setText(places.get(position).getShortName());
        }
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public void clear() {
        places.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}
