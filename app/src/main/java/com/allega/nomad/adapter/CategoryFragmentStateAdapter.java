package com.allega.nomad.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allega.nomad.R;
import com.allega.nomad.activity.home.fragment.category.child.ChildCategoryFragment;

/**
 * Created by imnotpro on 5/30/15.
 */
public class CategoryFragmentStateAdapter extends FragmentStatePagerAdapter {

    private String[] titles;
    private int category;
    private int genre;
    private boolean isAd;

    public CategoryFragmentStateAdapter(Context context, FragmentManager fm, int menu, boolean isAd) {
        super(fm);
        if (isAd) {
            titles = context.getResources().getStringArray(R.array.category_ad);
        } else {
            switch (menu) {
                case 1:
                    titles = context.getResources().getStringArray(R.array.category_event);
                    break;
                case 2:
                    titles = context.getResources().getStringArray(R.array.category_edutainment);
                    break;
                case 3:
                    titles = context.getResources().getStringArray(R.array.category_tv_show);
                    break;
                case 4:
                    titles = context.getResources().getStringArray(R.array.category_live_channel);
                    break;
                case 5:
                    titles = context.getResources().getStringArray(R.array.category_series);
                    break;
                default:
                    titles = context.getResources().getStringArray(R.array.category_movie);
                    break;
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return ChildCategoryFragment.newInstance(category, genre, isAd, position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public void setup(int category, int genre, boolean isAd) {
        this.category = category;
        this.genre = genre;
        this.isAd = isAd;
    }

}
