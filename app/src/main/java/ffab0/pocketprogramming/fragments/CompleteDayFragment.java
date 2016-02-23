package ffab0.pocketprogramming.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;


import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.managers.QuestionAndAnswerActivity;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.models.DayQuestionsRepository;
import ffab0.pocketprogramming.models.LevelManager;
import ffab0.pocketprogramming.models.TwitterShare;

public class CompleteDayFragment extends Fragment implements OnClickListener {
    private static final String DAY = "DAY";
    private static DayQuestionsRepository mDayQuestionsRepository;

    public static CompleteDayFragment newInstance(int day) {
        CompleteDayFragment fragment = new CompleteDayFragment();
        Bundle args = new Bundle();
        args.putInt(DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    public CompleteDayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences levelData = getActivity().getSharedPreferences("level_data", 0);
        LevelManager mLevelManager = new LevelManager();
        FragmentManager fragmentManager = getFragmentManager();
        mLevelManager.updateLevel(fragmentManager, levelData);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_day, container, false);
        mDayQuestionsRepository = new DayQuestionsRepository();
        mDayQuestionsRepository.fetchSummary(this, inflater, view, getArguments().getInt(DAY));

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toNextDay0:
                toNextDay(v, 0);
                break;
            case R.id.toNextDay1:
                toNextDay(v, 1);
                break;
            case R.id.toRetry0:
                retrySameDay(v, 0);
                break;
            case R.id.toRetry1:
                retrySameDay(v, 1);
                break;
            case R.id.shareToTwitter0:
                shareToTwitter(v);
                break;
            case R.id.shareToTwitter1:
                shareToTwitter(v);
                break;
            default: // リストの問題をクリックした時
                Intent intent = new Intent(getActivity(), QuestionAndAnswerActivity.class);
                int tagInt = (int) v.findViewById(R.id.toDetail).getTag();
                intent.putExtra("questionPosition", tagInt);
                intent.putExtra("mDayQuestions", mDayQuestionsRepository.mDayQuestions.questions.toString());
                startActivityForResult(intent, 0);
                break;
        }
    }

    private void toNextDay(View v, int i) {
        FragmentManager fragment_manager = getFragmentManager();
        FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();

        Common common = (Common) getActivity().getApplication();
        common.init();

        int resourceIds[] = { R.id.toNextDay0, R.id.toNextDay1 };
        int nextDay = (Integer) v.findViewById(resourceIds[i]).getTag();

        if (getArguments().getInt("DAY")%7 == 0) {
            int thisWeek = ((getArguments().getInt("DAY")-1)/7)+1;
            ResultAnalysisFragment mResultAnalysisFragment = ResultAnalysisFragment.newInstance(thisWeek);
            fragment_transaction.replace(R.id.mainContents, mResultAnalysisFragment);
            fragment_transaction.commit();
        } else {
            PracticeFragment mPracticeFragmentNextDay = PracticeFragment.newInstance(nextDay, 0);
            fragment_transaction.replace(R.id.mainContents, mPracticeFragmentNextDay);
            fragment_transaction.commit();
        }
    }

    private void retrySameDay(View v, int i) {
        FragmentManager fragment_manager = getFragmentManager();
        FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();

        Common common = (Common) getActivity().getApplication();
        common.init();

        int resourceIds[] = { R.id.toRetry0, R.id.toRetry1 };
        PracticeFragment mPracticeFragmentRetry = PracticeFragment.newInstance((Integer) v.findViewById(resourceIds[i]).getTag(), 0);
        fragment_transaction.replace(R.id.mainContents, mPracticeFragmentRetry);
        fragment_transaction.commit();
    }

    private void shareToTwitter(View v) {
        int thisWeek = ((getArguments().getInt("DAY")-1)/7)+1;
        int thisDay = (getArguments().getInt("DAY")%7 == 0) ? 7 : getArguments().getInt("DAY")%7;
        int score = (int) v.getTag();

        String url = TwitterShare.dayScore(thisWeek, thisDay, score);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
