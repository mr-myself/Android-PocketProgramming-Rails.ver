package ffab0.pocketprogramming.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.ImageButton;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.managers.MainActivity;
import ffab0.pocketprogramming.managers.ReviewActivity;
import ffab0.pocketprogramming.models.WeekSummaryRepository;


public class GlobalNavigationFragment extends Fragment implements OnClickListener {

    public static GlobalNavigationFragment newInstance() {
        return new GlobalNavigationFragment();
    }

    public GlobalNavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_global_navigation, container, false);
        ImageButton toPracticeButton = (ImageButton) view.findViewById(R.id.toPractice);
        ImageButton toReviewButton = (ImageButton) view.findViewById(R.id.toReview);
        ImageButton toSummaryButton = (ImageButton) view.findViewById(R.id.toSummary);

        toPracticeButton.setOnClickListener(this);
        toReviewButton.setOnClickListener(this);
        toSummaryButton.setOnClickListener(this);

        // 現在のActivityに合わせてメニューのボーダーをつける
        Activity activity = this.getActivity();
        if (activity.toString().contains("Main")) {
            toPracticeButton.setBackgroundResource(R.drawable.bg_border_bottom_red);
        } else if (activity.toString().contains("Review")){
            toReviewButton.setBackgroundResource(R.drawable.bg_border_bottom_red);
        } else if (activity.toString().contains("Summary")){
            toSummaryButton.setBackgroundResource(R.drawable.bg_border_bottom_red);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toPractice:
                Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                startActivityForResult(mainActivity, 0);
                getActivity().overridePendingTransition(0, 0);
                break;
            case R.id.toReview:
                Intent reviewActivity = new Intent(getActivity(), ReviewActivity.class);
                startActivityForResult(reviewActivity, 0);
                getActivity().overridePendingTransition(0, 0);
                break;
            case R.id.toSummary:
                WeekSummaryRepository mWeekSummaryRepository = new WeekSummaryRepository();
                mWeekSummaryRepository.fetchAll();
                break;
        }
    }
}
