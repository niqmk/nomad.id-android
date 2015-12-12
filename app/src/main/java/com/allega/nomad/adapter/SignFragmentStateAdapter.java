package com.allega.nomad.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allega.nomad.R;
import com.allega.nomad.activity.sign.login.LoginFragment;
import com.allega.nomad.activity.sign.register.RegisterFragment;

/**
 * Created by imnotpro on 5/30/15.
 */
public class SignFragmentStateAdapter extends FragmentStatePagerAdapter {

    private String[] titles;

    public SignFragmentStateAdapter(Context context, FragmentManager fm) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.sign_tab);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return RegisterFragment.newInstance();
            default:
                return LoginFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
