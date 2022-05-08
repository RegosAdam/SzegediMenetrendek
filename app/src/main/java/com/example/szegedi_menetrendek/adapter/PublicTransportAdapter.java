package com.example.szegedi_menetrendek.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.szegedi_menetrendek.R;
import com.example.szegedi_menetrendek.model.PublicTransport;

import java.util.ArrayList;

public class PublicTransportAdapter extends RecyclerView.Adapter<PublicTransportAdapter.ViewHolder> implements Filterable {
    private ArrayList<PublicTransport> mPublicTransportData = new ArrayList<>();
    private ArrayList<PublicTransport> mPublicTransportDataAll = new ArrayList<>();
    private Context mContext;
    private int lastPosition = -1;

    public PublicTransportAdapter(Context context, ArrayList<PublicTransport> itemsData) {
        this.mPublicTransportData = itemsData;
        this.mPublicTransportDataAll = itemsData;
        this.mContext = context;
    }

    @Override
    public PublicTransportAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.list_public_transport, parent, false));
    }

    @Override
    public void onBindViewHolder(PublicTransportAdapter.ViewHolder holder, int position) {
        // Get current sport.
        PublicTransport currentPublicTransport = mPublicTransportData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentPublicTransport);


        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mPublicTransportData.size();
    }


    @Override
    public Filter getFilter() {
        return publicTransportFilter;
    }

    private Filter publicTransportFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<PublicTransport> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mPublicTransportDataAll.size();
                results.values = mPublicTransportDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(PublicTransport item : mPublicTransportDataAll) {
                    if(item.getNumber().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mPublicTransportData = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        // Member Variables for the TextViews
        private TextView mPublicTransportNumber;
        private ImageView mPublicTransportImage;

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mPublicTransportNumber = itemView.findViewById(R.id.itemNumber);
            mPublicTransportImage = itemView.findViewById(R.id.itemImage);
        }

        void bindTo(PublicTransport currentPublicTransport){
            mPublicTransportNumber.setText(currentPublicTransport.getNumber());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(currentPublicTransport.getImageResource()).into(mPublicTransportImage);
        }
    }
}
