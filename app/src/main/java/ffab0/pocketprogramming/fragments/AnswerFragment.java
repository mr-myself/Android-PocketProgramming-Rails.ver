package ffab0.pocketprogramming.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import ffab0.pocketprogramming.R;


public class AnswerFragment extends Fragment {
    final private static String EXPLANATION = "explanation";
    private String mExplanation;

    public static AnswerFragment newInstance(String explanation) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putString(EXPLANATION, explanation);
        fragment.setArguments(args);
        return fragment;
    }

    public AnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mExplanation = getArguments().getString(EXPLANATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answer, container, false);

        // フェードインアニメーション
        LinearLayout linear_layout = (LinearLayout) view.findViewById(R.id.answerContents);
        final Animation animationFadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        linear_layout.startAnimation(animationFadeIn);

        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.post(new Runnable() {
            @Override
            public void run() {
                // Widthをちゃんと取るためにViewの描画後に実行
                TextView explanationTextView = (TextView) view.findViewById(R.id.explanationText);
                explanationTextView.setText(mExplanation);
            }
        });
    }
}
