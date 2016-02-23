package ffab0.pocketprogramming.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.models.WeekSummaryRepository;
import ffab0.pocketprogramming.util.SoundEffect;

public class ResultAnalysisFragment extends Fragment implements View.OnClickListener {
    private static WeekSummaryRepository mWeekSummaryRepository;

    public static ResultAnalysisFragment newInstance(int week) {
        ResultAnalysisFragment fragment = new ResultAnalysisFragment();
        Bundle args = new Bundle();
        args.putInt("WEEK", week);
        fragment.setArguments(args);
        return fragment;
    }

    public ResultAnalysisFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_analysis, container, false);
        Button toNextWeekButton = (Button) view.findViewById(R.id.toNextWeek);
        toNextWeekButton.setOnClickListener(this);

        mWeekSummaryRepository = new WeekSummaryRepository();
        mWeekSummaryRepository.fetch(inflater, view, getArguments().getInt("WEEK"));

        MediaPlayer bgm = MediaPlayer.create(getActivity(), R.raw.up);
        if (SoundEffect.check()) bgm.start();

        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragment_manager = getFragmentManager();
        FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();
        int nextWeekStartDay = (getArguments().getInt("WEEK")*7)+1;

        switch (v.getId()) {
            case R.id.toNextWeek:
                PracticeFragment practice_fragment = PracticeFragment.newInstance(nextWeekStartDay, 0);
                fragment_transaction.replace(R.id.mainContents, practice_fragment);
                fragment_transaction.commit();

                Common common = (Common) getActivity().getApplication();
                common.init();
                break;
        }
    }
}
