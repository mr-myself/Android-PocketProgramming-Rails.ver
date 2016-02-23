package ffab0.pocketprogramming.viewModel;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.models.WeekSummary;


public class SummaryViewModel {

    static public void analysisList(LayoutInflater inflater, View rootView, WeekSummary mWeekSummary, int week) {
        LinearLayout mResultAnalysisList = (LinearLayout) rootView.findViewById(R.id.resultAnalysisList);

        LinearLayout correctListWrap = (LinearLayout) rootView.findViewById(R.id.resultAnalysisCorrect);
        if (0 < mWeekSummary.ok.length()) {
            addResultAnalysisCorrectView(inflater, correctListWrap, mWeekSummary);
        } else {
            mResultAnalysisList.removeView(correctListWrap);
        }

        LinearLayout wrongListWrap = (LinearLayout) rootView.findViewById(R.id.resultAnalysisWrong);
        if (0 < mWeekSummary.no.length()) {
            addResultAnalysisWrongView(inflater, wrongListWrap, mWeekSummary);
        } else {
            mResultAnalysisList.removeView(wrongListWrap);
        }

        if (week != 0 && week != 4) {
            TextView toNextWeekTextView = (TextView) rootView.findViewById(R.id.toNextWeek);
            toNextWeekTextView.setText(Common.getContext().getString(R.string.challenge_nth_week, week + 1));
        } else if (week == 4) {
            LinearLayout nextWeekWrapView = (LinearLayout) rootView.findViewById(R.id.NextWeekWrap);
            mResultAnalysisList.removeView(nextWeekWrapView);
        }
    }

    static private void addResultAnalysisCorrectView(LayoutInflater inflater, LinearLayout correctListWrap, WeekSummary mWeekSummary) {
        for (int i = 0; i < mWeekSummary.ok.length(); i++) {
            try {
                View rowView = inflater.inflate(R.layout.row_result_analysis_correct, null);
                LinearLayout listItem = (LinearLayout) rowView.findViewById(R.id.correctItem);
                TextView correctTitle = (TextView) listItem.findViewById(R.id.correctTitle);
                correctTitle.setText(mWeekSummary.ok.getString(i));

                correctListWrap.addView(listItem);
            } catch (JSONException e) {
                Log.d("ResultAnalysisFragment", e.toString());
            }
        }
    }

    static private void addResultAnalysisWrongView(LayoutInflater inflater, LinearLayout wrongListWrap, final WeekSummary mWeekSummary) {
        for (int i = 0; i < mWeekSummary.no.length(); i++) {
            try {
                View rowView = inflater.inflate(R.layout.row_result_analysis_wrong, null);
                final LinearLayout listItem = (LinearLayout) rowView.findViewById(R.id.wrongItem);
                final TextView wrongTitle = (TextView) listItem.findViewById(R.id.wrongTitle);
                final TextView wrongSentence = (TextView) listItem.findViewById(R.id.wrongSentence);
                final int finalI = i;
                wrongListWrap.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        try {
                            wrongTitle.setText(mWeekSummary.no.getJSONObject(finalI).getString("title"));
                            wrongSentence.setText(mWeekSummary.no.getJSONObject(finalI).getString("sentence"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                LinearLayout referenceItems = (LinearLayout) listItem.findViewById(R.id.referenceItems);
                for (int t = 0; t < mWeekSummary.no.getJSONObject(i).getJSONArray("reference_title").length(); t++) {
                    View referenceItem = inflater.inflate(R.layout.row_result_analysis_reference, null);
                    final TextView referenceUrl = (TextView) referenceItem.findViewById(R.id.referenceUrl);
                    final int finalT = t;
                    final int finalI1 = i;
                    referenceItem.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            try {
                                referenceUrl.setText(Html.fromHtml(
                                        "<a href=" + mWeekSummary.no.getJSONObject(finalI1).getJSONArray("url").getString(finalT) + ">" +
                                                mWeekSummary.no.getJSONObject(finalI1).getJSONArray("reference_title").getString(finalT) + "</a>"
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    referenceUrl.setMovementMethod(LinkMovementMethod.getInstance());
                    referenceItems.addView(referenceItem);
                }
                wrongListWrap.addView(listItem);
            } catch (JSONException e) {
                Log.d("ResultAnalysisFragment", e.toString());
            }
        }
    }
}
