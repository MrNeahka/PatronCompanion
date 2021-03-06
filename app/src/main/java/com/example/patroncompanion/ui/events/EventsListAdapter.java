package com.example.patroncompanion.ui.events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patroncompanion.R;

import org.w3c.dom.Text;

import java.util.List;

public class EventsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<EventsElement> data;
    private String username;
    private Context context;

    public EventsListAdapter(List<EventsElement> data, String username, Context context) {
        this.data = data;
        this.username = username;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View VHItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_fragment, parent, false);
            return new EventsViewHolder(VHItem);
        } else if (viewType == TYPE_HEADER) {
            View VHHeader = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_header, parent, false);
            return new HeaderViewHolder(VHHeader);
        }
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_fragment, parent, false);
        //return new EventsViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof EventsViewHolder) {
            EventsViewHolder a = new EventsViewHolder(holder.itemView);
            a.mTextView.setText(data.get(position-1).getText());
            a.mDataText.setText(data.get(position-1).getDate().toString());

            a.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventActivity.class);
                    intent.putExtra("date", data.get(position-1).getDate().toString());
                    intent.putExtra("text", data.get(position-1).getText());
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder b = new HeaderViewHolder(holder.itemView);
            b.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventAddActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    public EventsElement getItem(int position) {
        return data.get(position);
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTextView, mDataText;

        public EventsViewHolder(View itemView) {
            super(itemView);

            mCardView = (CardView) itemView.findViewById(R.id.events_cardView);
            mTextView = (TextView) itemView.findViewById(R.id.events_title);
            mDataText = (TextView) itemView.findViewById(R.id.events_date);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        Button mButton;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.event_insert_text);
            mButton = (Button) itemView.findViewById(R.id.event_insert_date);
        }
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
