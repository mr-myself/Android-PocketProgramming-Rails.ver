package ffab0.pocketprogramming.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.models.DayQuestionsRepository;
import ffab0.pocketprogramming.models.Question;
import ffab0.pocketprogramming.models.TrueOrFalse;
import ffab0.pocketprogramming.util.SoundEffect;


public class ChoiceFragment extends Fragment implements OnClickListener {

    public Question mQuestion;
    public Button answerBtn1;
    public Button answerBtn2;
    public Button answerBtn3;
    public Button nextBtn;
    private View view;

    public static ChoiceFragment newInstance(int practiceFragmentId) {
        ChoiceFragment fragment = new ChoiceFragment();
        Bundle args = new Bundle();
        args.putInt("PRACTICE_FRAGMENT_ID", practiceFragmentId);
        fragment.setArguments(args);

        return fragment;
    }

    public ChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_choice, container, false);

        answerBtn1 = (Button) view.findViewById(R.id.answer1);
        answerBtn2 = (Button) view.findViewById(R.id.answer2);
        answerBtn3 = (Button) view.findViewById(R.id.answer3);
        nextBtn = (Button) view.findViewById(R.id.answer4);
        nextBtn.setAllCaps(false);

        // フェードインアニメーション
        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.choiceContents);
        final Animation animationFadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        tableLayout.startAnimation(animationFadeIn);

        return view;
    }

    @Override
    public void onClick(View v) {
        // フェードインアニメーション
        TableLayout table_layout = (TableLayout) view.findViewById(R.id.choiceContents);
        final Animation animationFadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        table_layout.startAnimation(animationFadeIn);

        if (v.getTag() != null && v.getTag().equals(mQuestion.answer)) {
            TrueOrFalse.postToServer(mQuestion.questionId, true);
            MediaPlayer bgmRight = MediaPlayer.create(this.getActivity(), R.raw.correct);
            if (SoundEffect.check()) bgmRight.start();
            System.out.println("true answer");
        } else {
            TrueOrFalse.postToServer(mQuestion.questionId, false);
            MediaPlayer bgmWrong = MediaPlayer.create(this.getActivity(), R.raw.wrong);
            if (SoundEffect.check()) bgmWrong.start();
            System.out.println("false answer");
        }

        setNoListenerAndUnclickable();
        showExplanation();
        adaptCorrectAndWrongImages();

        nextBtn.setText(getText(R.string.next_question));
        nextBtn.setBackgroundResource(R.drawable.element_card_d_next);
        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextQuestionOrCompleteDayFragment();
            }
        });
    }

    private void setNoListenerAndUnclickable() {
        // リスナーを発火させないように無効化
        answerBtn1.setOnClickListener(null);
        answerBtn2.setOnClickListener(null);
        answerBtn3.setOnClickListener(null);
        nextBtn.setOnClickListener(null);

        // ボタンのクリックを無効化
        answerBtn1.setClickable(false);
        answerBtn2.setClickable(false);
        answerBtn3.setClickable(false);
    }

    private void showExplanation() {
        // 解説を表示
        int practiceFragmentId = getArguments().getInt("PRACTICE_FRAGMENT_ID");
        PracticeFragment mPracticeFragment = (PracticeFragment) getFragmentManager().findFragmentById(practiceFragmentId);
        mPracticeFragment.showExplanation();
    }

    private void adaptCorrectAndWrongImages() {
        // カードを正解・不正解に応じて変更
        if (answerBtn1.getTag().equals(mQuestion.answer)) {
            answerBtn1.setBackgroundResource(R.drawable.element_card_a_right);
            answerBtn2.setBackgroundResource(R.drawable.element_card_b_wrong);
            answerBtn3.setBackgroundResource(R.drawable.element_card_c_wrong);
        } else if (answerBtn2.getTag().equals(mQuestion.answer)){
            answerBtn1.setBackgroundResource(R.drawable.element_card_a_wrong);
            answerBtn2.setBackgroundResource(R.drawable.element_card_b_right);
            answerBtn3.setBackgroundResource(R.drawable.element_card_c_wrong);
        } else if (answerBtn3.getTag().equals(mQuestion.answer)){
            answerBtn1.setBackgroundResource(R.drawable.element_card_a_wrong);
            answerBtn2.setBackgroundResource(R.drawable.element_card_b_wrong);
            answerBtn3.setBackgroundResource(R.drawable.element_card_c_right);
        }
    }

    private void setNextQuestionOrCompleteDayFragment() {
        FragmentManager fragment_manager = getFragmentManager();
        FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();

        if (mQuestion.nextId == 0) {
            DayQuestionsRepository.completeDay(mQuestion.day);
            CompleteDayFragment complete_day_fragment = CompleteDayFragment.newInstance(mQuestion.day);
            fragment_transaction.replace(R.id.mainContents, complete_day_fragment);
            fragment_transaction.commit();
        } else {
            PracticeFragment practice_fragment = PracticeFragment.newInstance(mQuestion.day, mQuestion.nextId);
            fragment_transaction.replace(R.id.mainContents, practice_fragment);
            fragment_transaction.commit();
        }
    }
}
