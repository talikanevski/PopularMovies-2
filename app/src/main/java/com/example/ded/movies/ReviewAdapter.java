package com.example.ded.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private Review[] mReviews;

    // Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener
    public ReviewAdapter(DetailActivity detailActivity) {
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);

        return new ReviewAdapter.ReviewAdapterViewHolder(view);
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView reviewAuthor;
        public final TextView reviewContext;

        public ReviewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.tv_author);
            reviewContext = itemView.findViewById(R.id.tv_content);
        }
    }
    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder reviewAdapterViewHolder, int position) {
        reviewAdapterViewHolder.reviewAuthor.setText(mReviews[position].getAuthor());
        reviewAdapterViewHolder.reviewContext.setText(mReviews[position].getContent());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setReviewData(Review[] reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }
}
