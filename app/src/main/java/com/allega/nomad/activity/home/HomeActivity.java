package com.allega.nomad.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.Toast;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.fragment.category.CategoryFragment;
import com.allega.nomad.activity.home.fragment.discover.DiscoverFragment;
import com.allega.nomad.activity.home.fragment.navigation.NavigationFragment;
import com.allega.nomad.activity.sign.SignActivity;
import com.allega.nomad.base.BaseActivity;

import java.util.concurrent.atomic.AtomicInteger;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by imnotpro on 5/30/15.
 */
public class HomeActivity extends BaseActivity {

    private static final String EXTRA_TO_LOGIN = "extra-to-login";

    @InjectView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    private HomeController controller;
    private boolean isClose = false;
    private boolean isHome = true;
    private AtomicInteger totalFragment = new AtomicInteger();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void startActivityToLogin(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(EXTRA_TO_LOGIN, true);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_container, DiscoverFragment.newInstance());
        fragmentTransaction.commit();

        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
        fragmentTransaction2.add(R.id.left_drawer, NavigationFragment.newInstance());
        fragmentTransaction2.commit();

        controller = new HomeController(this);

        if (getIntent().getExtras() != null) {
            if (getIntent().getBooleanExtra(EXTRA_TO_LOGIN, false)) {
                SignActivity.startActivity(this);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else if (totalFragment.get() > 0) {
            totalFragment.decrementAndGet();
            super.onBackPressed();
        } else if (isHome) {
            if (isClose) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, R.string.exit_dialog, Toast.LENGTH_SHORT).show();
                isClose = true;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isClose = false;
                    }
                }, 2000);
            }
        } else {
            goToCategory(0, -1, false);
        }
    }

    public void goToCategory(int currentMenu, int position, boolean isAd) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (position >= 0) {
            fragmentTransaction.replace(R.id.main_fragment_container, CategoryFragment.newInstance(currentMenu, position, isAd));
            isHome = false;
        } else {
            if (!isHome)
                fragmentTransaction.replace(R.id.main_fragment_container, DiscoverFragment.newInstance());
            isHome = true;
        }
//        fragmentTransaction.addToBackStack(CategoryFragment.class.getSimpleName());
        fragmentTransaction.commit();
        if (position >= 0)
            drawerLayout.closeDrawers();
    }

    public void goToFragment(Fragment fragment) {
        totalFragment.incrementAndGet();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_container, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
        drawerLayout.closeDrawers();
    }

    public void showDrawer() {
        boolean isOpen = drawerLayout.isDrawerOpen(Gravity.LEFT);
        if (isOpen) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    public void backHome() {
        goToCategory(0, -1, false);
        showDrawer();
    }

    public void backFragment() {
        super.onBackPressed();
    }
}
