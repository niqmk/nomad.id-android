package com.allega.nomad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allega.nomad.base.BaseAbstractExpendableAdapter;
import com.allega.nomad.entity.TvShowEpisode;
import com.allega.nomad.entity.TvShowSeason;
import com.allega.nomad.viewgroup.EpisodeChildViewGroup;
import com.allega.nomad.viewgroup.EpisodeGroupViewGroup;

import java.util.List;

/**
 * Created by imnotpro on 8/9/15.
 */
public class TvShowEpisodeListAdapter extends BaseAbstractExpendableAdapter<TvShowSeason, TvShowEpisode> {

    public TvShowEpisodeListAdapter(Context context, List<TvShowSeason> itemList) {
        super(context, itemList);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getGroup(groupPosition).getData().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getGroup(groupPosition).getData().get(childPosition).getId();
    }

    @Override
    protected void onBindGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        EpisodeGroupViewGroup episodeGroupViewGroup = (EpisodeGroupViewGroup) convertView;
        episodeGroupViewGroup.bind(getGroup(groupPosition));
    }

    @Override
    protected View onCreateGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return new EpisodeGroupViewGroup(context);
    }

    @Override
    protected void onBindChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        EpisodeChildViewGroup episodeChildViewGroup = (EpisodeChildViewGroup) convertView;
        episodeChildViewGroup.bind(getChild(getGroup(groupPosition), childPosition));
    }

    @Override
    protected View onCreateChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return new EpisodeChildViewGroup(context);
    }

    @Override
    public TvShowEpisode getChild(TvShowSeason group, int childPosition) {
        return group.getData().get(childPosition);
    }
}
