package com.allega.nomad.activity.home.fragment.movie.child.detail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.adapter.ClipAdapter;
import com.allega.nomad.base.BaseFragment;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.constant.Constant;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.MovieCast;
import com.allega.nomad.entity.MovieClip;
import com.allega.nomad.entity.MovieDirector;
import com.allega.nomad.entity.MovieGenre;
import com.allega.nomad.viewgroup.ClipViewGroup;
import com.allega.nomad.viewgroup.PlayerViewGroup;
import com.allega.nomad.viewgroup.VideoDetailViewGroup;

import org.apache.commons.lang3.StringUtils;
import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.Iterator;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class DetailFragment extends BaseFragment {

    private static final String ARGUMENT_ID = "argument-id";

    @InjectView(R.id.player_view_group)
    protected PlayerViewGroup playerViewGroup;
    @InjectView(R.id.description_text_view)
    protected TextView descriptionTextView;
    @InjectView(R.id.genre_text_view)
    protected TextView genreTextView;
    @InjectView(R.id.release_text_view)
    protected TextView releaseTextView;
    @InjectView(R.id.director_text_view)
    protected TextView directorTextView;
    @InjectView(R.id.cast_text_view)
    protected TextView castTextView;
    @InjectView(R.id.runtime_text_view)
    protected TextView runtimeTextView;
    @InjectView(R.id.language_text_view)
    protected TextView languageTextView;
    @InjectView(R.id.subtitle_text_view)
    protected TextView subtitleTextView;
    @InjectView(R.id.trailer_list_view)
    protected TwoWayView trailerListView;
    @InjectView(R.id.video_detail_view_group)
    protected VideoDetailViewGroup videoDetailViewGroup;
    @InjectView(R.id.title_text_view)
    protected TextView titleTextView;
    @InjectView(R.id.trailer_layout)
    protected LinearLayout trailerLayout;
    @InjectView(R.id.scroll_view)
    protected ScrollView scrollView;
    @InjectView(R.id.single_trailer_video_view)
    protected ClipViewGroup singleTrailerVideoView;
    @InjectView(R.id.single_trailer_layout)
    protected LinearLayout singleTrailerLayout;
    long id;
    private DetailFragmentController controller;

    public static DetailFragment newInstance(long id) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_ID, id);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(ARGUMENT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            scrollView.setVisibility(View.GONE);
            videoDetailViewGroup.setVisibility(View.GONE);
            titleTextView.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            scrollView.setVisibility(View.VISIBLE);
            videoDetailViewGroup.setVisibility(View.VISIBLE);
            titleTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        playerViewGroup.stopTracking();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new DetailFragmentController(this, view);
        Bus.getInstance().register(controller);
        videoDetailViewGroup.setVideoDetailListener(controller);
        playerViewGroup.setListener(controller, getChildFragmentManager());
    }

    @Override
    public void onDestroyView() {
        Bus.getInstance().unregister(controller);
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    void bind(Movie movie) {
        setupVideo(movie);
        titleTextView.setText(movie.getTitle());
        descriptionTextView.setText(movie.getDescription());
        videoDetailViewGroup.bind(movie);
        StringBuilder genre = new StringBuilder();
        Iterator<MovieGenre> movieGenreIterator = movie.getMovieGenres().iterator();
        while (movieGenreIterator.hasNext()) {
            genre.append(movieGenreIterator.next().getName());
            if (movieGenreIterator.hasNext())
                genre.append(Constant.COMMA);
        }
        if (StringUtils.isEmpty(genre.toString()))
            genreTextView.setText(R.string.empty_information);
        else
            genreTextView.setText(genre.toString());

        if (StringUtils.isEmpty(movie.getReleaseDate()))
            releaseTextView.setText(R.string.empty_information);
        else
            releaseTextView.setText(movie.getReleaseDate());

        StringBuilder cast = new StringBuilder();
        Iterator<MovieCast> movieCastIterator = movie.getMovieCasts().iterator();
        while (movieCastIterator.hasNext()) {
            cast.append(movieCastIterator.next().getName());
            if (movieCastIterator.hasNext())
                cast.append(Constant.COMMA);
        }
        if (StringUtils.isEmpty(cast))
            castTextView.setText(R.string.empty_information);
        else
            castTextView.setText(cast);

        StringBuilder director = new StringBuilder();
        Iterator<MovieDirector> movieDirectorIterator = movie.getMovieDirectors().iterator();
        while (movieDirectorIterator.hasNext()) {
            director.append(movieDirectorIterator.next().getName());
            if (movieDirectorIterator.hasNext())
                director.append(Constant.COMMA);
        }
        if (StringUtils.isEmpty(director))
            directorTextView.setText(R.string.empty_information);
        else
            directorTextView.setText(director);

        if (movie.getRunTimeInMinute() > 0)
            runtimeTextView.setText(getResources().getString(R.string.minutes, movie.getRunTimeInMinute()));
        else
            runtimeTextView.setText(R.string.empty_information);

        if (StringUtils.isEmpty(movie.getLanguages()))
            languageTextView.setText(R.string.empty_information);
        else
            languageTextView.setText(movie.getLanguages());

        if (StringUtils.isEmpty(movie.getSubtitles()))
            subtitleTextView.setText(R.string.empty_information);
        else
            subtitleTextView.setText(movie.getSubtitles());
    }

    void setupVideo(Movie movie) {
        playerViewGroup.bind(movie);
        videoDetailViewGroup.bind(movie);
        playerViewGroup.bind(movie.getVideos());
    }

    void updateLike(Movie movie) {
        videoDetailViewGroup.bind(movie);
    }

    void setupTrailer(ClipAdapter adapter) {
        trailerListView.setOrientation(TwoWayLayoutManager.Orientation.HORIZONTAL);
        trailerListView.setAdapter(adapter);
    }

    void showTrailer() {
        trailerLayout.setVisibility(View.VISIBLE);
    }

    public void showTrailer(MovieClip movieClip) {
        singleTrailerVideoView.setupSingle();
        singleTrailerVideoView.bind(movieClip);
        singleTrailerLayout.setVisibility(View.VISIBLE);
    }

    public void forcePlay() {
        playerViewGroup.onWatch();
    }

}
