package com.example.ded.movies.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ded.movies.DetailActivity;
import com.example.ded.movies.R;
import com.example.ded.movies.Models.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private Review[] mReviews;

    public ReviewAdapter(DetailActivity detailActivity) {
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);

        return new ReviewAdapter.ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder reviewAdapterViewHolder, int position) {
        reviewAdapterViewHolder.reviewAuthor.setText(mReviews[position].getAuthor());
        reviewAdapterViewHolder.reviewContext.setText(mReviews[position].getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews != null ? mReviews.length : 0;
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

    public void setReviewData(Review[] reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }
}
