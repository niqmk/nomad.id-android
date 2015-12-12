package com.allega.nomad.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allega.nomad.R;
import com.allega.nomad.base.BaseActivity;
import com.allega.nomad.bus.Bus;
import com.allega.nomad.bus.event.BuyVideoEvent;
import com.allega.nomad.bus.event.UpdateMemberEvent;
import com.allega.nomad.entity.Member;
import com.allega.nomad.service.rest.app.AppRestController;
import com.allega.nomad.service.rest.app.AppRestService;
import com.allega.nomad.service.rest.app.ResponseCallback;
import com.allega.nomad.service.rest.app.response.Response;
import com.allega.nomad.storage.PreferenceProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;

/**
 * Created by imnotpro on 7/6/15.
 */
public class BuyConfirmationDialog extends DialogFragment {

    private static final String EXTRA_ID = "extra-id";
    private static final String EXTRA_TITLE = "extra-title";
    private static final String EXTRA_PRICE = "extra-price";
    private static final String EXTRA_EXPIRITY = "extra-expirity";

    private static final String TAG = BuyConfirmationDialog.class.getSimpleName();

    private final AppRestService appRestService = AppRestController.getAppRestService();

    @InjectView(R.id.description_text_view)
    protected TextView descriptionTextView;
    @InjectView(R.id.before_text_view)
    protected TextView beforeTextView;
    @InjectView(R.id.after_text_view)
    protected TextView afterTextView;

    private PreferenceProvider preferenceProvider;
    private long id;
    private String title;
    private String price;
    private String expirity;
    private boolean isBuy = false;


    public static void show(FragmentManager fragmentManager, long videoId, String title, String price, String expirity) {
        BuyConfirmationDialog buyDialog = new BuyConfirmationDialog();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID, videoId);
        bundle.putString(EXTRA_TITLE, title);
        bundle.putString(EXTRA_PRICE, price);
        bundle.putString(EXTRA_EXPIRITY, expirity);
        buyDialog.setArguments(bundle);
        buyDialog.show(fragmentManager, TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong(EXTRA_ID);
        title = getArguments().getString(EXTRA_TITLE);
        price = getArguments().getString(EXTRA_PRICE);
        expirity = getArguments().getString(EXTRA_EXPIRITY);
        preferenceProvider = new PreferenceProvider(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_buy_confirmation, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Member member = preferenceProvider.getMember();
        descriptionTextView.setText(Html.fromHtml(getString(R.string.buy_confirmation, title, expirity, price)));
        beforeTextView.setText(getString(R.string.x_points, member.getTotalPoints()));
        afterTextView.setText(getString(R.string.x_points, (member.getTotalPoints() - Integer.valueOf(price))));
    }

    @OnClick(R.id.buy_button)
    protected void onBuy() {
        if (!isBuy) {
            isBuy = true;
            if (getActivity() instanceof BaseActivity) {
                BaseActivity activity = (BaseActivity) getActivity();
                activity.showProgress();
            }
            appRestService.postVideoPurchase(id, price, expirity, new ResponseCallback<Response>(getActivity()) {
                @Override
                public void success(Response responseServer) {
                    if (responseServer.getStatus() == 1) {
                        Member member = preferenceProvider.getMember();
                        member.setTotalPoints(member.getTotalPoints() - Integer.valueOf(price));
                        preferenceProvider.setMember(member);
                        Bus.getInstance().post(new UpdateMemberEvent());
                        Bus.getInstance().post(new BuyVideoEvent(id));
                    }
                    dismiss();
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    if (getActivity() instanceof BaseActivity) {
                        BaseActivity activity = (BaseActivity) getActivity();
                        activity.hideProgress();
                    }
                    dismiss();
                }
            });
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

}
