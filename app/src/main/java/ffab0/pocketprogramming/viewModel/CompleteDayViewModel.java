package ffab0.pocketprogramming.viewModel;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.fragments.CompleteDayFragment;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.models.DayQuestions;
import ffab0.pocketprogramming.util.AnimationEffect;
import ffab0.pocketprogramming.util.SoundEffect;

public class CompleteDayViewModel {
    public static DayQuestions mDayQuestions;
    public static View rootView;

    public static void buildView(final CompleteDayFragment mCompleteDayFragment, final LayoutInflater inflater, final View view, final DayQuestions dayQuestions) {
        mDayQuestions = dayQuestions;
        rootView = view;

        setHeaderText();
        setScore();
        toggleNextDayOrWeekSummary();
        setSegue(mCompleteDayFragment);
        buildQuestionResultList(mCompleteDayFragment, inflater);
    }

    private static void buildQuestionResultList(final CompleteDayFragment mCompleteDayFragment, LayoutInflater inflater) {
        LinearLayout listWrap = (LinearLayout) rootView.findViewById(R.id.dayQuestionsListWrap);

        for (int i=0; i<mDayQuestions.questions.length(); i++) {
            View rowView = inflater.inflate(R.layout.row_practice_results, null);
            final LinearLayout listItem = (LinearLayout) rowView.findViewById(R.id.dayQuestionsListItem);
            setListItem(mCompleteDayFragment, listItem, i);
            listWrap.addView(listItem);
        }
    }

    private static void setHeaderText() {
        int day = (mDayQuestions.day%7 != 0 ? mDayQuestions.day%7 : 7);
        TextView questionListHeader = (TextView) rootView.findViewById(R.id.questionListHeader);
        TextView scoreHeader = (TextView) rootView.findViewById(R.id.scoreHeader);
        questionListHeader.setText(Common.getContext().getString(R.string.what_they_learn_in_the_day, day));
        scoreHeader.setText(Common.getContext().getString(R.string.finished_day, day));
    }

    private static void toggleNextDayOrWeekSummary() {
        if (mDayQuestions.nextDay == 0 || (mDayQuestions.nextDay-1)%7 == 0) {
            Button toNextDay0View = (Button) rootView.findViewById(R.id.toNextDay0);
            Button toNextDay1View = (Button) rootView.findViewById(R.id.toNextDay1);
            toNextDay0View.setText(Common.getContext().getString(R.string.to_week_result));
            toNextDay0View.setBackgroundResource(R.drawable.button_next_star_large);
            toNextDay1View.setText(Common.getContext().getString(R.string.to_week_result));
            toNextDay1View.setBackgroundResource(R.drawable.button_next_star_large);
        }
    }

    private static void setScore() {
        ImageView scoreImageArea = (ImageView) rootView.findViewById(R.id.scoreImage);

        // スコアイメージを描画
        String score = "score_" + mDayQuestions.score;
        scoreImageArea.setImageResource(rootView.getResources().getIdentifier(score, "drawable", Common.getContext().getPackageName()));
        AnimationEffect.animatePropertyValuesHolderSample(scoreImageArea);

        // スコアに合わせて音声を流す
        if (mDayQuestions.score >= 80) {
            MediaPlayer bgm = MediaPlayer.create(rootView.getContext(), R.raw.good);
            if (SoundEffect.check()) bgm.start();
        } else if (mDayQuestions.score <= 30) {
            MediaPlayer bgm = MediaPlayer.create(rootView.getContext(), R.raw.shock);
            if (SoundEffect.check()) bgm.start();
        } else {
            MediaPlayer bgm = MediaPlayer.create(rootView.getContext(), R.raw.normal);
            if (SoundEffect.check()) bgm.start();
        }
    }

    private static void setListItem(CompleteDayFragment mCompleteDayFragment, LinearLayout listItem, final int i) {
        TextView trueOrFalse = (TextView) listItem.findViewById(R.id.trueOrFalse);
        TextView questionNumber = (TextView) listItem.findViewById(R.id.questionNumber);
        final TextView questionText = (TextView) listItem.findViewById(R.id.questionText);
        TextView toDetail = (TextView) listItem.findViewById(R.id.toDetail);

        try {
            Boolean result = mDayQuestions.questions.getJSONObject(i).getBoolean("result");
            if(result == true) {
                trueOrFalse.setText(Common.getContext().getString(R.string.correct));
                trueOrFalse.setBackgroundResource(R.drawable.label_right);
                listItem.removeView(toDetail);
            } else {
                trueOrFalse.setText(Common.getContext().getString(R.string.wrong));
                trueOrFalse.setBackgroundResource(R.drawable.label_wrong);
                toDetail.setTag(i);

                // クリックを有効にする
                listItem.setClickable(true);
                listItem.setOnClickListener(mCompleteDayFragment);
            }
            questionNumber.setText(Common.getContext().getString(R.string.nth_question, i+1));
            listItem.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    try {
                        questionText.setText(mDayQuestions.questions.getJSONObject(i).getString("text"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            Log.d("CompleteDayFragment", e.toString());
        }
    }

    private static void setSegue(CompleteDayFragment mCompleteDayFragment) {
        Button nextDayButton = (Button) rootView.findViewById(R.id.toNextDay0);
        Button nextDayButton2 = (Button) rootView.findViewById(R.id.toNextDay1);
        Button retryButton = (Button) rootView.findViewById(R.id.toRetry0);
        Button retryButton2 = (Button) rootView.findViewById(R.id.toRetry1);
        Button shareToTwitterButton = (Button) rootView.findViewById(R.id.shareToTwitter0);
        Button shareToTwitterButton2 = (Button) rootView.findViewById(R.id.shareToTwitter1);

        nextDayButton.setAllCaps(false);
        nextDayButton2.setAllCaps(false);
        retryButton.setAllCaps(false);
        retryButton2.setAllCaps(false);
        shareToTwitterButton.setAllCaps(false);
        shareToTwitterButton2.setAllCaps(false);

        nextDayButton.setTag(mDayQuestions.nextDay);
        nextDayButton.setClickable(true);
        nextDayButton.setOnClickListener(mCompleteDayFragment);

        nextDayButton2.setTag(mDayQuestions.nextDay);
        nextDayButton2.setClickable(true);
        nextDayButton2.setOnClickListener(mCompleteDayFragment);

        retryButton.setTag(mDayQuestions.day);
        retryButton.setClickable(true);
        retryButton.setOnClickListener(mCompleteDayFragment);

        retryButton2.setTag(mDayQuestions.day);
        retryButton2.setClickable(true);
        retryButton2.setOnClickListener(mCompleteDayFragment);

        shareToTwitterButton.setTag(mDayQuestions.score);
        shareToTwitterButton.setClickable(true);
        shareToTwitterButton.setOnClickListener(mCompleteDayFragment);

        shareToTwitterButton2.setTag(mDayQuestions.score);
        shareToTwitterButton2.setClickable(true);
        shareToTwitterButton2.setOnClickListener(mCompleteDayFragment);
    }
}