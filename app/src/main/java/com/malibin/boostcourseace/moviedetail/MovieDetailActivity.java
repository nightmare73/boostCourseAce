package com.malibin.boostcourseace.moviedetail;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.malibin.boostcourseace.R;
import com.malibin.boostcourseace.moviedetail.adapter.ReviewListAdapter;
import com.malibin.boostcourseace.review.MovieReview;
import com.malibin.boostcourseace.util.LikeState;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private LikeState currentLikeState = LikeState.NOTHING;
    private int likeCount = 15;
    private int dislikeCount = 1;
    private float starRating = 8.2f;

    private ImageView btnLike;
    private ImageView btnDislike;
    private TextView tvLikeCount;
    private TextView tvDislikeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initView();
    }

    private void initView() {
        setStarRateScore();
        bindEvaluationView();
        initEvaluationButton();
        initReviewList();
        initReviewWriteBtn();
        initReviewMoreBtn();
    }

    private void setStarRateScore() {
        RatingBar ratingBar = findViewById(R.id.rating_movie_detail_act_star_rating);
        ratingBar.setRating(starRating / 2);
    }

    private void bindEvaluationView() {
        btnLike = findViewById(R.id.btn_movie_detail_act_like);
        btnDislike = findViewById(R.id.btn_movie_detail_act_dislike);
        tvLikeCount = findViewById(R.id.tv_movie_detail_act_like_count);
        tvDislikeCount = findViewById(R.id.tv_movie_detail_act_dislike_count);
    }

    private void initEvaluationButton() {
        tvLikeCount.setText(String.valueOf(likeCount));
        tvDislikeCount.setText(String.valueOf(dislikeCount));

        btnLike.setOnClickListener(view -> {
            if (currentLikeState == LikeState.LIKE) {
                rollbackCount(view);
                return;
            }
            modifyCount(view);
        });

        btnDislike.setOnClickListener(view -> {
            if (currentLikeState == LikeState.DISLIKE) {
                rollbackCount(view);
                return;
            }
            modifyCount(view);
        });
    }

    private void rollbackCount(View view) {
        view.setSelected(false);
        currentLikeState = LikeState.NOTHING;
        if (view.equals(btnLike)) {
            tvLikeCount.setText(String.valueOf(likeCount));
        }
        if (view.equals(btnDislike)) {
            tvDislikeCount.setText(String.valueOf(dislikeCount));
        }
    }

    private void modifyCount(View view) {
        if (view.equals(btnLike)) {
            likeClick();
        }
        if (view.equals(btnDislike)) {
            dislikeClick();
        }
    }

    private void likeClick() {
        currentLikeState = LikeState.LIKE;
        btnLike.setSelected(true);
        tvLikeCount.setText(String.valueOf(likeCount + 1));
        btnDislike.setSelected(false);
        tvDislikeCount.setText(String.valueOf(dislikeCount));
    }

    private void dislikeClick() {
        currentLikeState = LikeState.DISLIKE;
        btnLike.setSelected(false);
        tvLikeCount.setText(String.valueOf(likeCount));
        btnDislike.setSelected(true);
        tvDislikeCount.setText(String.valueOf(dislikeCount + 1));
    }

    private void initReviewList() {
        ListView reviewList = findViewById(R.id.rv_movie_detail_review_list);
        ReviewListAdapter adapter = new ReviewListAdapter(this, tempData());
        reviewList.setAdapter(adapter);
        setListViewHeightBasedOnChildren(reviewList);
    }

    private List<MovieReview> tempData() {
        ArrayList<MovieReview> result = new ArrayList<>();
        result.add(new MovieReview("", "모메", "10분전", 10f, "적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요.", 0));
        result.add(new MovieReview("", "모메", "10분전", 3.7f, "리뷰 내용용요용ㅇ용", 2));
        result.add(new MovieReview("", "모메", "10분전", 4.2f, "리뷰 내용용요용ㅇ용", 2));
        result.add(new MovieReview("", "모메", "10분전", 2f, "리뷰 내용용요용ㅇ용", 2));
        return result;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        int listWidth = listView.getMeasuredWidth();

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View mView = listAdapter.getView(i, null, listView);
            mView.measure(
                    MeasureSpec.makeMeasureSpec(listWidth, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 2));
    }

    private void initReviewWriteBtn() {
        LinearLayout btn = findViewById(R.id.btn_movie_detail_act_review_write);
        btn.setOnClickListener(view -> {
            Toast.makeText(this, "리뷰 쓰기 버튼 눌림", Toast.LENGTH_SHORT).show();
        });
    }

    private void initReviewMoreBtn() {
        ConstraintLayout btn = findViewById(R.id.btn_movie_detail_act_review_more);
        btn.setOnClickListener(view -> {
            Toast.makeText(this, "리뷰 더보기 버튼 눌림", Toast.LENGTH_SHORT).show();
        });
    }
}
