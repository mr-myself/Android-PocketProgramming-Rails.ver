package ffab0.pocketprogramming.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.models.DayQuestionsRepository;
import ffab0.pocketprogramming.util.AnimationEffect;


public class MainFragment extends Fragment implements OnClickListener {

    private DayQuestionsRepository mDayQuestionsRepository;

    public static MainFragment newInstance(String UUID) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString("UUID", UUID);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // フェードインアニメーション
        LinearLayout linear_layout = (LinearLayout) view.findViewById(R.id.mainContents);
        AnimationEffect.fadeIn(getActivity(), linear_layout);

        mDayQuestionsRepository = new DayQuestionsRepository();
        mDayQuestionsRepository.startDayFromMain(this, view);

        Button toSecondButton = (Button) view.findViewById(R.id.btnToSecond);
        toSecondButton.setOnClickListener(this);
        setRubyAndRailsLevelImages(view);

        return view;
    }

    private void setRubyAndRailsLevelImages(View view) {
        SharedPreferences levelData = getActivity().getSharedPreferences("level_data", 0);
        String currentRubyLevel = levelData.getString("ruby_level", "e");
        String currentRailsLevel = levelData.getString("rails_level", "e");

        String packageName = Common.getContext().getPackageName();
        ImageView rubyScoreImage = (ImageView) view.findViewById(R.id.OverviewRubyScoreImage);
        rubyScoreImage.setImageResource(getResources().getIdentifier("rank_" + currentRubyLevel, "drawable", packageName));

        ImageView railsScoreImage = (ImageView) view.findViewById(R.id.OverviewRailsScoreImage);
        railsScoreImage.setImageResource(getResources().getIdentifier("rank_" + currentRailsLevel, "drawable", packageName));

        // アニメーションを設定
        AnimationEffect.animatePropertyValuesHolderSample(rubyScoreImage);
        AnimationEffect.animatePropertyValuesHolderSample(railsScoreImage);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragment_manager = getFragmentManager();
        FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();

        switch(v.getId()) {
            case R.id.btnToSecond:
                PracticeFragment practice_fragment = PracticeFragment.newInstance(mDayQuestionsRepository.mStartDay, 0);
                fragment_transaction.replace(R.id.mainContents, practice_fragment);
                fragment_transaction.commit();
                Common common = (Common) getActivity().getApplication();
                common.init();
                break;
        }
    }
}
