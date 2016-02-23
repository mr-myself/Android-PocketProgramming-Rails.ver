package ffab0.pocketprogramming.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.models.QuestionRepository;


public class PracticeFragment extends Fragment {

    public static View view;
    public QuestionRepository mQuestionRepository;
    private static final String DAY = "day";
    private static final String QUESTION_ID = "questionId";

    public static PracticeFragment newInstance(int day, int questionId) {
        PracticeFragment fragment = new PracticeFragment();
        Bundle args = new Bundle();
        args.putInt(DAY, day);
        args.putInt(QUESTION_ID, questionId);
        fragment.setArguments(args);

        return fragment;
    }

    public PracticeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentManager fragment_manager = getFragmentManager();
        FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();

        QuestionFragment mQuestionFragment = QuestionFragment.newInstance();
        ChoiceFragment mChoiceFragment = ChoiceFragment.newInstance(this.getId());

        mQuestionRepository = new QuestionRepository();
        mQuestionRepository.fetch(getArguments().getInt(DAY), getArguments().getInt(QUESTION_ID), mQuestionFragment, mChoiceFragment);

        fragment_transaction.replace(R.id.Question, mQuestionFragment);
        fragment_transaction.replace(R.id.Choice, mChoiceFragment);
        fragment_transaction.commit();

        return inflater.inflate(R.layout.fragment_practice, container, false);
    }

    public void showExplanation() {
        FragmentManager fragment_manager = getFragmentManager();
        FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();

        AnswerFragment answer_fragment = AnswerFragment.newInstance(mQuestionRepository.mQuestion.explanation);
        fragment_transaction.replace(R.id.Question, answer_fragment);
        fragment_transaction.commit();
    }
}
