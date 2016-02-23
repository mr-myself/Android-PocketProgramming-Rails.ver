package ffab0.pocketprogramming.viewModel;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.fragments.MainFragment;
import ffab0.pocketprogramming.fragments.RatingDialogFragment;
import ffab0.pocketprogramming.models.Common;

public class MainViewModel {

    public static void setProgress(final View rootView, final int startDay) {
        int week = (startDay > 28 ? 4 : (startDay-1)/7+1);
        int weekStartDay = (startDay%7 == 0 ? 7 : startDay%7);

        setWeekProgressImage(rootView, week);
        setDayProgress(rootView, startDay, weekStartDay);
    }

    private static void setDayProgress(View rootView, int startDay, int weekStartDay) {
        // DAYプログレスの画像を描画
        if (startDay == 29) {
            setCompleteAllDaysView(rootView);
        } else {
            setChallengeNextDayView(rootView, weekStartDay);
        }
    }

    private static void setCompleteAllDaysView(View rootView) {
        LinearLayout progressWrap = (LinearLayout) rootView.findViewById(R.id.ProgressWrap);
        TextView dayText = (TextView) rootView.findViewById(R.id.dayText);
        TextView weekText = (TextView) rootView.findViewById(R.id.weekText);
        LinearLayout weekProgressWrap = (LinearLayout) rootView.findViewById(R.id.weekProgressWrap);
        LinearLayout buttonWrap = (LinearLayout) rootView.findViewById(R.id.NextDayWrap);
        Button toPracticeButton = (Button) rootView.findViewById(R.id.btnToSecond);

        progressWrap.removeView(dayText);
        progressWrap.removeView(weekText);
        progressWrap.removeView(weekProgressWrap);
        buttonWrap.removeView(toPracticeButton);

        ImageView dayProgressImageArea = (ImageView) rootView.findViewById(R.id.dayProgress);
        dayProgressImageArea.setImageResource(R.drawable.icon_all_finished);
    }

    private static void setChallengeNextDayView(View rootView, int weekStartDay) {
        ImageView dayProgressImageArea = (ImageView) rootView.findViewById(R.id.dayProgress);
        String dayResourceId = "icon_eggs1_" + weekStartDay;
        dayProgressImageArea.setImageResource(rootView.getResources().getIdentifier(dayResourceId, "drawable", Common.getContext().getPackageName()));

        Button toPracticeButton = (Button) rootView.findViewById(R.id.btnToSecond);
        toPracticeButton.setText(Common.getContext().getString(R.string.challenge_nth_day, weekStartDay));
    }

    private static void setWeekProgressImage(View rootView, int week) {
        ImageView weekProgressImageArea = (ImageView) rootView.findViewById(R.id.weekProgress);
        String weekResourceId = "element_birds1_" + week;
        weekProgressImageArea.setImageResource(rootView.getResources().getIdentifier(weekResourceId, "drawable", Common.getContext().getPackageName()));
    }

    public static void showRateDialog(MainFragment mMainFragment, int startDay) {
        SharedPreferences ratingFlag = mMainFragment.getActivity().getSharedPreferences("rating_flag", 0);
        SharedPreferences.Editor editor = ratingFlag.edit();
        Boolean firstRating = ratingFlag.getBoolean("first_rating", true);
        Boolean secondRating = ratingFlag.getBoolean("second_rating", true);

        if ((15 <= startDay && startDay <= 20) && firstRating == true) {
            DialogFragment ratingDialogFragment = RatingDialogFragment.newInstance();
            ratingDialogFragment.show(mMainFragment.getFragmentManager(), null);
            editor.putBoolean("first_rating", false);
            editor.commit();
        } else if (startDay == 29 && secondRating == true) {
            DialogFragment ratingDialogFragment = RatingDialogFragment.newInstance();
            ratingDialogFragment.show(mMainFragment.getFragmentManager(), null);
            editor.putBoolean("second_rating", false);
            editor.commit();
        }
    }
}
