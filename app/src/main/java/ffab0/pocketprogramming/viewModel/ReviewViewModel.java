package ffab0.pocketprogramming.viewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.fragments.ReviewFragment;
import ffab0.pocketprogramming.managers.ReviewActivity;
import ffab0.pocketprogramming.models.Review;


public class ReviewViewModel {

    static public void list(ReviewFragment reviewFragment, LayoutInflater inflater, View fragmentRootView, JSONArray mReviews) {
        LinearLayout reviewSectionListView = (LinearLayout) fragmentRootView.findViewById(R.id.reviewSectionList);
        int week = 0;
        View rowView = null;

        for (int i = 1; i <= 28; i++) {
            if (i%7 == 1) {
                week += 1;
                rowView = inflater.inflate(R.layout.row_reviews, null);
                TextView weekNumberText = (TextView) rowView.findViewById(R.id.weekNumber);
                weekNumberText.setText("WEEK" + (i/7 + 1));
                ImageView weekNumberImageView = (ImageView) rowView.findViewById(R.id.weekNumberImage);
                weekNumberImageView.setImageResource(rowView.getResources().getIdentifier("icon_bird" + week, "drawable", ReviewActivity.PACKAGE_NAME));
                LinearLayout weekNumberArea = (LinearLayout) rowView.findViewById(R.id.weekNumberWrap);
                if (i == 8) {
                    weekNumberArea.setBackgroundResource(R.drawable.bg_border_right_bottom_blue);
                } else if (i == 15) {
                    weekNumberArea.setBackgroundResource(R.drawable.bg_border_right_bottom_red);
                }
            }

            int day = (i%7 != 0 ? i%7 : 7);
            int scoreViewId = rowView.getResources().getIdentifier("day" + day + "ScoreImage", "id", ReviewActivity.PACKAGE_NAME);
            ImageView scoreImageView = (ImageView) rowView.findViewById(scoreViewId);

            if (i > mReviews.length()) {
                scoreImageView.setImageResource(R.drawable.score_question);
            } else {
                try {
                    Review mReview = new Review(mReviews.getJSONObject(i-1));
                    scoreImageView.setImageResource(rowView.getResources().getIdentifier("score_" + mReview.score, "drawable", ReviewActivity.PACKAGE_NAME));

                    int dayRootViewId = rowView.getResources().getIdentifier("day" + day, "id", ReviewActivity.PACKAGE_NAME);
                    LinearLayout dayRootView = (LinearLayout) rowView.findViewById(dayRootViewId);
                    dayRootView.setTag(i);
                    dayRootView.setClickable(true);
                    dayRootView.setOnClickListener(reviewFragment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (i%7 == 0) {
                reviewSectionListView.addView(rowView);
            }
        }
    }
}
