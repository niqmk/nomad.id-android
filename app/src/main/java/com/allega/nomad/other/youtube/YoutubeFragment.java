package com.allega.nomad.other.youtube;

import android.os.Bundle;
import android.widget.Toast;

import com.allega.nomad.R;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.PlayCountYoutubeEvent;
import com.allega.nomad.bus.event.UpdateMemberEvent;
import com.allega.nomad.entity.Member;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.PreferenceProvider;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by imnotpro on 6/21/15.
 */
public class YoutubeFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener {

    private static final String ARG_VIDEO_CODE = "arg-video-code";
    private static final String ARG_POINT_REWARD = "arg-point-reward";

    private static final AppRestService appRestService = AppRestController.getAppRestService();

    private String videoCode;
    private long pointReward;
    private boolean isCalled = false;
    private PreferenceProvider preferenceProvider;

    public static YoutubeFragment newInstance(String videoCode, long pointReward) {
        YoutubeFragment youtubeFragment = new YoutubeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_VIDEO_CODE, videoCode);
        bundle.putLong(ARG_POINT_REWARD, pointReward);
        youtubeFragment.setArguments(bundle);
        return youtubeFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        videoCode = getArguments().getString(ARG_VIDEO_CODE);
        if (videoCode.contains("/")) {
            String[] split = videoCode.split("/");
            videoCode = split[split.length - 1];
        }
        preferenceProvider = new PreferenceProvider(getActivity());
        pointReward = getArguments().getLong(ARG_POINT_REWARD);
        initialize(getString(R.string.google_android_api_key), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION | YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
        if (!wasRestored) {

            youTubePlayer.loadVideo(videoCode);
            if (pointReward > 0) {
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
                youTubePlayer.setPlaybackEventListener(this);
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onPlaying() {
        Bus.getInstance().post(new PlayCountYoutubeEvent(videoCode));
    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {
        if (!isCalled && pointReward > 0 && preferenceProvider.getMember() != null) {
            isCalled = true;
            appRestService.postMemberPoint(preferenceProvider.getMember().getId(), "1", pointReward, new ResponseCallback<Response>(getActivity()) {
                @Override
                public void success(Response response) {
                    if (response.getStatus() == 1) {
                        Member member = preferenceProvider.getMember();
                        member.setTotalPoints(member.getTotalPoints() + pointReward);
                        preferenceProvider.setMember(member);
                        Bus.getInstance().post(new UpdateMemberEvent());
                        if (getActivity() != null)
                            Toast.makeText(getActivity(), getString(R.string.earn_point_confirm, pointReward), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}
