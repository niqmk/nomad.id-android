package com.allega.nomad.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.allega.nomad.R;
import com.allega.nomad.entity.Edutainment;
import com.allega.nomad.entity.Event;
import com.allega.nomad.entity.LiveChannel;
import com.allega.nomad.entity.Movie;
import com.allega.nomad.entity.Price;
import com.allega.nomad.entity.SerialEpisode;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.model.Type;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.PreferenceProvider;
import com.allega.nomad.viewgroup.PriceViewGroup;

import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;

/**
 * Created by imnotpro on 7/6/15.
 */
public class BuyDialog extends DialogFragment implements View.OnClickListener {

    private static final String EXTRA_ID = "extra-id";
    private static final String EXTRA_TYPE = "extra-type";

    private static final String TAG = BuyDialog.class.getSimpleName();

    private final AppRestService appRestService = AppRestController.getAppRestService();

    @InjectView(R.id.radio_group)
    protected LinearLayout radioGroup;

    private PreferenceProvider preferenceProvider;
    private String title;
    private boolean isBuy = false;
    private long id;
    private Type type;
    private List<Price> priceList;
    private Price selectedPrice;

    public static void show(FragmentManager fragmentManager, long videoId, Type type) {
        BuyDialog buyDialog = new BuyDialog();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID, videoId);
        bundle.putSerializable(EXTRA_TYPE, type);
        buyDialog.setArguments(bundle);
        buyDialog.show(fragmentManager, TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(EXTRA_ID);
        Realm realm = DatabaseManager.getInstance(getActivity());
        type = (Type) getArguments().getSerializable(EXTRA_TYPE);
        switch (type) {
            case MOVIE:
                Movie movie = realm.where(Movie.class).equalTo(Movie.FIELD_VIDEO_ID, id).findFirst();
                priceList = movie.getPrices();
                title = movie.getTitle();
                break;
            case EDUTAINMENT:
                Edutainment edutainment = realm.where(Edutainment.class).equalTo(Edutainment.FIELD_VIDEO_ID, id).findFirst();
                priceList = edutainment.getPrices();
                title = edutainment.getTitle();
                break;
            case EVENT:
                Event event = realm.where(Event.class).equalTo(Event.FIELD_VIDEO_ID, id).findFirst();
                priceList = event.getPrices();
                title = event.getTitle();
                break;
            case TVSHOW:
                TvShowEpisode tvShowEpisode = realm.where(TvShowEpisode.class).equalTo(TvShowEpisode.FIELD_VIDEO_ID, id).findFirst();
                priceList = tvShowEpisode.getPrices();
                title = tvShowEpisode.getTitle();
                break;
            case LIVECHANNEL:
                LiveChannel liveChannel = realm.where(LiveChannel.class).equalTo(LiveChannel.FIELD_VIDEO_ID, id).findFirst();
                priceList = liveChannel.getPrices();
                title = liveChannel.getTitle();
                break;
            case SERIAL:
                SerialEpisode serialEpisode = realm.where(SerialEpisode.class).equalTo(SerialEpisode.FIELD_VIDEO_ID, id).findFirst();
                priceList = serialEpisode.getPrices();
                title = serialEpisode.getTitle();
                break;
        }
        preferenceProvider = new PreferenceProvider(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_buy, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Iterator<Price> it = priceList.iterator();
        boolean isFirst = true;
        while (it.hasNext()) {
            Price price = it.next();
            PriceViewGroup priceViewGroup = new PriceViewGroup(getActivity());
            priceViewGroup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            priceViewGroup.bind(price);
            priceViewGroup.setOnClickListener(this);
            priceViewGroup.setTag(price);
            if (isFirst) {
                isFirst = false;
                selectedPrice = price;
            }
            radioGroup.addView(priceViewGroup);
            if (it.hasNext()) {
                View separator = new View(getActivity());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                separator.setLayoutParams(layoutParams);
                separator.setBackgroundColor(getResources().getColor(R.color.gray));
                radioGroup.addView(separator);
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.AppDialog);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onClick(View v) {
        selectedPrice = (Price) v.getTag();
        BuyConfirmationDialog.show(getActivity().getSupportFragmentManager(), id, title, selectedPrice.getPrice(), selectedPrice.getExpiryDays());
        dismiss();
    }
}
