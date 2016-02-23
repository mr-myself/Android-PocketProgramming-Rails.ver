package ffab0.pocketprogramming.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class SummaryPagerAdapter extends FragmentPagerAdapter {

    private JSONArray mList;

    public SummaryPagerAdapter(FragmentManager fragmentManager) {
        // Required empty public constructor
        super(fragmentManager);
    }

    public Fragment getItem(int position) {
        Fragment mSummaryAnalysisFragment = null;
        try {
            mSummaryAnalysisFragment = SummaryAnalysisFragment.newInstance(mList.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mSummaryAnalysisFragment;
    }

    @Override
    public int getCount() {
        return mList.length();
    }

    public void addItem(JSONArray summary) {
        mList = summary;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Week " + String.valueOf(position + 1);
    }
}
