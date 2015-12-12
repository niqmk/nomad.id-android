package com.allega.nomad.activity.home.fragment.navigation;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.ArrayRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.HomeActivity;
import com.allega.nomad.activity.home.fragment.point.PointFragment;
import com.allega.nomad.activity.home.fragment.profile.ProfileFragment;
import com.allega.nomad.activity.sign.SignActivity;
import com.allega.nomad.adapter.MenuAdapter;
import com.allega.nomad.base.BaseFragmentController;
import com.allega.nomad.bus.event.UpdateMemberEvent;
import com.allega.nomad.dialog.SettingDialog;
import com.allega.nomad.entity.AdCategory;
import com.allega.nomad.entity.EdutainmentCategory;
import com.allega.nomad.entity.EventCategory;
import com.allega.nomad.entity.LiveChannelCategory;
import com.allega.nomad.entity.Member;
import com.allega.nomad.entity.MovieCategory;
import com.allega.nomad.entity.SerialCategory;
import com.allega.nomad.entity.TvShowCategory;
import com.allega.nomad.model.Menu;
import com.allega.nomad.storage.DatabaseManager;
import com.allega.nomad.storage.PreferenceProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import butterknife.OnItemClick;
import io.realm.Realm;

/**
 * Created by imnotpro on 5/30/15.
 */
public class NavigationFragmentController extends BaseFragmentController<NavigationFragment> {

    private final Realm realm;
    private final MenuAdapter menuAdapter;
    private final List<Menu> menuList = new ArrayList<>();
    private final List<Menu> tempMenuList = new ArrayList<>();
    private final PreferenceProvider preferenceProvider;
    private int currentMenu = -1;
    private int tempMenu = 0;
    private boolean isAd = false;

    public NavigationFragmentController(NavigationFragment fragment, View view) {
        super(fragment, view);
        realm = DatabaseManager.getInstance(context);
        preferenceProvider = new PreferenceProvider(context);
        menuAdapter = new MenuAdapter(context, menuList);
        menuList.addAll(generateMenuList(R.array.navigation_menu, R.array.navigation_menu_icon));
        fragment.setupMenu(menuAdapter);
        Member member = preferenceProvider.getMember();
        if (member != null) {
            fragment.updateMember(member);
            fragment.setupMenu(true);
        } else {
            fragment.setupMenu(false);
        }
    }

    @OnItemClick(R.id.category_list_view)
    protected void onItemClick(AdapterView<MenuAdapter> parent, View view, int position, long id) {
        if (menuList.size() <= position) {
            return;
        }
        if (isAd) {
            menuAdapter.setCurrentSelected(position);
            fragment.goToCategory(currentMenu, (int) menuList.get(position).getId(), true);
        } else if (currentMenu >= 0) {
            menuAdapter.setCurrentSelected(position);
            fragment.goToCategory(currentMenu, (int) menuList.get(position).getId(), false);
        } else {
            menuAdapter.setCurrentSelected(-1);
            String menu = menuList.get(position).getName();
            if (menu.equals("Musics")) {
                Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show();
                return;
            }
            if (menu.equals("Earn Points")) {
                onAd();
                return;
            }
            menuList.clear();
            switch (menu) {
                case "Movies":
                    currentMenu = 0;
                    List<MovieCategory> movieCategoryList = realm.where(MovieCategory.class).findAll();
                    for (MovieCategory edutainmentCategory : movieCategoryList) {
                        Menu item = new Menu();
                        item.setId(edutainmentCategory.getId());
                        item.setName(edutainmentCategory.getName());
                        try {
                            int drawable = context.getResources().getIdentifier("ic_" + edutainmentCategory.getImgString().replace("-", "_"), "drawable", context.getPackageName());
                            item.setDrawable(context.getResources().getDrawable(drawable));
                            menuList.add(item);
                        } catch (Resources.NotFoundException exception) {
                            //Resource not found skip
                        }
                    }
                    break;
                case "Musics":
                    currentMenu = 1;
                    break;
                case "Events":
                    currentMenu = 2;
                    List<EventCategory> eventCategoryList = realm.where(EventCategory.class).findAll();
                    for (EventCategory eventCategory : eventCategoryList) {
                        Menu item = new Menu();
                        item.setId(eventCategory.getId());
                        item.setName(eventCategory.getName());
                        try {
                            int drawable = context.getResources().getIdentifier("ic_" + eventCategory.getImgString().replace("-", "_"), "drawable", context.getPackageName());
                            item.setDrawable(context.getResources().getDrawable(drawable));
                            menuList.add(item);
                        } catch (Resources.NotFoundException exception) {
                            //Resource not found skip
                        }
                    }
                    break;
                case "Edutainments":
                    currentMenu = 3;
                    List<EdutainmentCategory> edutainmentCategoryList = realm.where(EdutainmentCategory.class).findAll();
                    for (EdutainmentCategory edutainmentCategory : edutainmentCategoryList) {
                        Menu item = new Menu();
                        item.setId(edutainmentCategory.getId());
                        item.setName(edutainmentCategory.getName());
                        try {
                            int drawable = context.getResources().getIdentifier("ic_" + edutainmentCategory.getImgString().replace("-", "_"), "drawable", context.getPackageName());
                            item.setDrawable(context.getResources().getDrawable(drawable));
                            menuList.add(item);
                        } catch (Resources.NotFoundException exception) {
                            //Resource not found skip
                        }
                    }
                    break;
                case "Series":
                    currentMenu = 4;
                    List<SerialCategory> serialCategoryList = realm.where(SerialCategory.class).findAll();
                    for (SerialCategory serialCategory : serialCategoryList) {
                        Menu item = new Menu();
                        item.setId(serialCategory.getId());
                        item.setName(serialCategory.getName());
                        try {
                            int drawable = context.getResources().getIdentifier("ic_" + serialCategory.getImgString().replace("-", "_"), "drawable", context.getPackageName());
                            item.setDrawable(context.getResources().getDrawable(drawable));
                            menuList.add(item);
                        } catch (Resources.NotFoundException exception) {
                            //Resource not found skip
                        }
                    }
                    break;
                case "Tv Shows":
                    currentMenu = 5;
                    List<TvShowCategory> tvShowCategoryList = realm.where(TvShowCategory.class).findAll();
                    for (TvShowCategory tvShowCategory : tvShowCategoryList) {
                        Menu item = new Menu();
                        item.setId(tvShowCategory.getId());
                        item.setName(tvShowCategory.getName());
                        try {
                            int drawable = context.getResources().getIdentifier("ic_" + tvShowCategory.getImgString().replace("-", "_"), "drawable", context.getPackageName());
                            item.setDrawable(context.getResources().getDrawable(drawable));
                            menuList.add(item);
                        } catch (Resources.NotFoundException exception) {
                            //Resource not found skip
                        }
                    }
                    break;
                case "Live Channels":
                    currentMenu = 6;
                    List<LiveChannelCategory> liveChannelCategoryList = realm.where(LiveChannelCategory.class).findAll();
                    for (LiveChannelCategory liveChannelCategory : liveChannelCategoryList) {
                        Menu item = new Menu();
                        item.setId(liveChannelCategory.getId());
                        item.setName(liveChannelCategory.getName());
                        try {
                            int drawable = context.getResources().getIdentifier("ic_" + liveChannelCategory.getImgString().replace("-", "_"), "drawable", context.getPackageName());
                            item.setDrawable(context.getResources().getDrawable(drawable));
                            menuList.add(item);
                        } catch (Resources.NotFoundException exception) {
                            //Resource not found skip
                        }
                    }
                    break;
                default:
                    menuList.addAll(generateMenuList(R.array.navigation_menu, R.array.navigation_menu_icon));
                    break;
            }
            fragment.updateBreadcrumb(menu);
        }
        menuAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.home_button, R.id.home_button_2})
    protected void onHome() {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.backHome();
        }
    }

    @OnClick(R.id.currency_button)
    protected void onCurency() {
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(PointFragment.newInstance());
        }
    }

    @OnClick({R.id.profile_button, R.id.profile_button_2})
    protected void onProfile() {
        if (preferenceProvider.getToken() == null) {
            SignActivity.startActivity(context);
        } else if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(ProfileFragment.newInstance(0));
        }
    }

    @OnClick(R.id.favorite_button)
    protected void onFavorite() {
        if (preferenceProvider.getToken() == null) {
            SignActivity.startActivity(context);
        } else if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(ProfileFragment.newInstance(2));
        }
    }

    @OnClick(R.id.download_button)
    protected void onDownload() {
        if (preferenceProvider.getToken() == null) {
            SignActivity.startActivity(context);
        } else if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(ProfileFragment.newInstance(3));
        }
    }

    @OnClick(R.id.message_button)
    protected void onMessage() {
        if (preferenceProvider.getToken() == null) {
            SignActivity.startActivity(context);
        } else if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.goToFragment(ProfileFragment.newInstance(1));
        }
    }

    protected void onAd() {
        isAd = true;
        tempMenuList.clear();
        tempMenuList.addAll(menuList);
        menuList.clear();
        List<AdCategory> adCategoryList = realm.where(AdCategory.class).findAll();
        for (AdCategory edutainmentCategory : adCategoryList) {
            Menu item = new Menu();
            item.setId(edutainmentCategory.getId());
            item.setName(edutainmentCategory.getName());
            try {
                int drawable = context.getResources().getIdentifier("ic_" + edutainmentCategory.getImgString().replace("-", "_"), "drawable", context.getPackageName());
                item.setDrawable(context.getResources().getDrawable(drawable));
                menuList.add(item);
            } catch (Resources.NotFoundException exception) {
                //Resource not found skip
            }
        }
        tempMenu = menuAdapter.getCurrentSelected();
        menuAdapter.setCurrentSelected(-1);
        menuAdapter.notifyDataSetChanged();
        fragment.setupAd(true);
    }

    @OnClick(R.id.ad_back_button)
    protected void onBackAd() {
        isAd = false;
        menuList.clear();
        menuAdapter.setCurrentSelected(tempMenu);
        menuList.addAll(tempMenuList);
        menuAdapter.notifyDataSetChanged();
        fragment.setupAd(false);
    }

    @OnClick({R.id.setting_button, R.id.setting_button_2})
    protected void onSetting() {
        SettingDialog.show(fragment.getChildFragmentManager());
    }

    public void removeCategory() {
        currentMenu = -1;
        menuList.clear();
        menuList.addAll(generateMenuList(R.array.navigation_menu, R.array.navigation_menu_icon));
        menuAdapter.notifyDataSetChanged();
        fragment.updateBreadcrumb(null);
        fragment.goToCategory(-1, -1, false);
    }

    private List<Menu> generateMenuList(@ArrayRes int titleArray, @ArrayRes int drawableArray) {
        List<Menu> menuList = new ArrayList<>();
        TypedArray icons = context.getResources().obtainTypedArray(drawableArray);
        String[] titles = context.getResources().getStringArray(titleArray);
        for (int i = 0; i < titles.length; ++i) {
            Menu menu = new Menu(i, titles[i], icons.getDrawable(i));
            menuList.add(menu);
        }
        icons.recycle();
        return menuList;
    }


    public void onEventMainThread(UpdateMemberEvent updateMemberEvent) {
        Member member = preferenceProvider.getMember();
        if (member != null)
            fragment.updateMember(member);
    }
}