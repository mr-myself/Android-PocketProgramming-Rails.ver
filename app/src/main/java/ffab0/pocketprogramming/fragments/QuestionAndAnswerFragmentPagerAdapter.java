package ffab0.pocketprogramming.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.models.Common;


public class QuestionAndAnswerFragmentPagerAdapter extends FragmentPagerAdapter {

    private JSONArray mList;

    public QuestionAndAnswerFragmentPagerAdapter(FragmentManager fragmentManager) {
        // Required empty public constructor
        super(fragmentManager);
    }

    public Fragment getItem(int position) {
        Fragment questionAndAnswerFragment = null;
        try {
            questionAndAnswerFragment = QuestionAndAnswerFragment.newInstance(mList.getJSONObject(position), position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questionAndAnswerFragment;
    }

    @Override
    public int getCount() {
        return mList.length();
    }

    public void addItem(JSONArray questions) {
        mList = questions;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Common.getContext().getString(R.string.nth_question, position + 1);
    }
}
