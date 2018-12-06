package com.example.ded.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {
    private Trailer[] mTrailers;
    public static final String YOUTUBE_BASE_URL = "https://img.youtube.com/vi/";
    public static final String YOUTUBE_DEFAULT_THUMBNAIL = "/default.jpg";

    /**
     * An on-click handler that  defined to make it easy for an Activity to interface with
     * RecyclerView
     */
    final private ListItemClickListener mOnClickListener;

    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onClick(Trailer clickedItem);
    }

    // Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener
    public TrailerAdapter(ListItemClickListener trailerAdapterOnClickHandler) {
        mOnClickListener = trailerAdapterOnClickHandler;
    }

    @NonNull
    @Override
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);

        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.TrailerAdapterViewHolder trailerAdapterViewHolder, int position) {
        String thumbnailUrl = YOUTUBE_BASE_URL + mTrailers[position].getTrailerKey() + YOUTUBE_DEFAULT_THUMBNAIL;
        Picasso.with(trailerAdapterViewHolder.ivTrailer.getContext())
                .load(thumbnailUrl)
                .into(trailerAdapterViewHolder.ivTrailer);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final ImageView ivTrailer;

        public TrailerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTrailer = (ImageView) itemView.findViewById(R.id.iv_trailer_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onClick(mTrailers[getAdapterPosition()]);

        }
    }

    public void setTrailerData(Trailer[] trailers) {
        mTrailers = trailers;
        notifyDataSetChanged();
    }
}