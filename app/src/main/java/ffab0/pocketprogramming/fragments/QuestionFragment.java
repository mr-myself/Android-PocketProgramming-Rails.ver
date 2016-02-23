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
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.models.Question;


public class QuestionFragment extends Fragment {

    public Common common;
    public TextView questionTextView;
    public TextView questionNumberView;
    public Question mQuestion;

    public static QuestionFragment newInstance() {
        QuestionFragment fragment = new QuestionFragment();
        return fragment;
    }

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        common = (Common) getActivity().getApplication();
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        questionTextView = (TextView) view.findViewById(R.id.questionText);
        questionNumberView = (TextView) view.findViewById(R.id.questionNumber);

        // フェードインアニメーション
        LinearLayout linear_layout = (LinearLayout) view.findViewById(R.id.questionContents);
        final Animation animationFadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        linear_layout.startAnimation(animationFadeIn);

        return view;
    }
}
