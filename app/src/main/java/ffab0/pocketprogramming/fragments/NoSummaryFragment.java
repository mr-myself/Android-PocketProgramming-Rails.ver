package ffab0.pocketprogramming.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.models.DayQuestionsRepository;


public class NoSummaryFragment extends Fragment implements View.OnClickListener {

    private DayQuestionsRepository mDayQuestionsRepository;

    public NoSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_no_summary, container, false);
        Button toNextWeekButton = (Button) view.findViewById(R.id.toNextWeek);
        toNextWeekButton.setOnClickListener(this);

        mDayQuestionsRepository = new DayQuestionsRepository();
        mDayQuestionsRepository.startDayFromNoSummary(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragment_manager = getFragmentManager();
        FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();

        switch(v.getId()) {
            case R.id.toNextWeek:
                PracticeFragment practiceFragment = PracticeFragment.newInstance(mDayQuestionsRepository.mStartDay, 0);
                fragment_transaction.replace(R.id.mainContents, practiceFragment);
                fragment_transaction.commit();
                Common common = (Common) getActivity().getApplication();
                common.init();
                break;
        }
    }

}
