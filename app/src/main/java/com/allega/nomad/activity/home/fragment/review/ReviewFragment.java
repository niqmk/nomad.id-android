package com.allega.nomad.activity.home.fragment.review;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.entity.MovieComment;
import com.allega.nomad.model.Type;
import com.allega.nomad.service.analytic.GAConstant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ReviewFragment extends BaseFragment implements RatingBar.OnRatingBarChangeListener {

    private static final String EXTRA_ID = "extra-id";
    private static final String EXTRA_TYPE = "extra-type";
    public long id;
    @InjectView(R.id.rating_bar)
    protected RatingBar ratingBar;
    @InjectView(R.id.review_edit_text)
    protected EditText reviewEditText;
    @InjectView(R.id.counter_text_view)
    protected TextView counterTextView;
    @InjectView(R.id.send_button)
    protected Button sendButton;
    @InjectView(R.id.rating_layout)
    protected LinearLayout ratingLayout;
    Type type;
    private ReviewController reviewController;

    public static ReviewFragment newInstance(long id, int requestCode, Type type) {
        ReviewFragment reviewFragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID, id);
        bundle.putSerializable(EXTRA_TYPE, type);
        reviewFragment.setArguments(bundle);
        return reviewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(EXTRA_ID);
        type = (Type) getArguments().getSerializable(EXTRA_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sacvedInstanceState) {
        View view = inflater.inflate(R.layout.activity_review, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (type) {
            case TVSHOW:
            case EVENT:
            case EDUTAINMENT:
            case AD:
            case LIVECHANNEL:
            case SERIAL:
                ratingLayout.setVisibility(View.GONE);
                break;
            case MOVIE:
            default:
                ratingLayout.setVisibility(View.VISIBLE);
                break;
        }
        reviewController = new ReviewController(this, view);
        ratingBar.setOnRatingBarChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendGAScreenTracker(GAConstant.REVIEW_SCREEN);
    }

    public float getRating() {
        return ratingBar.getRating();
    }

    public void updateCounter(int length) {
        counterTextView.setText(String.valueOf(length));
    }

    public String getComment() {
        return reviewEditText.getText().toString();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (rating <= 1) {
            ratingBar.setRating(1.0f);
        }
    }

    public void fillReview(MovieComment movieComment) {
        ratingBar.setRating(movieComment.getRate());
        reviewEditText.setText(movieComment.getDescription());
    }
}
