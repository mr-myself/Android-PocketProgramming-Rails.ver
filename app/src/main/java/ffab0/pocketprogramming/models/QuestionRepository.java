package ffab0.pocketprogramming.models;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.environments.EnvironmentConstants;
import ffab0.pocketprogramming.fragments.ChoiceFragment;
import ffab0.pocketprogramming.fragments.QuestionFragment;


public class QuestionRepository implements Serializable {

    public RequestQueue mQueue;
    public Question mQuestion;

    public void fetch(int day, int questionId, final QuestionFragment mQuestionFragment, final ChoiceFragment mChoiceFragment) {
        mQueue = VolleySingleton.getInstance(Common.getContext()).getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Method.GET,
                EnvironmentConstants.HOST_URL + "?lang=" + Common.getLanguage(), (String)null,
            new Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    mQuestion = new Question(response);
                    mQuestionFragment.mQuestion = mQuestion;
                    mChoiceFragment.mQuestion = mQuestion;

                    setTextOnQuestionFragment(mQuestionFragment);
                    setTextOnChoiceFragment(mChoiceFragment);
                    setClickListener(mChoiceFragment);
                }
            },

            new ErrorListener() {
                @Override public void onErrorResponse(VolleyError error) {
                    Log.d("QuestionRepo", error.toString());
                }
            }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }

    private void setClickListener(ChoiceFragment mChoiceFragment) {
        mChoiceFragment.answerBtn1.setOnClickListener(mChoiceFragment);
        mChoiceFragment.answerBtn2.setOnClickListener(mChoiceFragment);
        mChoiceFragment.answerBtn3.setOnClickListener(mChoiceFragment);
        mChoiceFragment.nextBtn.setOnClickListener(mChoiceFragment);
    }

    private void setTextOnQuestionFragment(QuestionFragment mQuestionFragment) {
        try {
            mQuestionFragment.common.increment();
            int weekStartDay;
            if (mQuestion.day%7 == 0) {
                weekStartDay = 7;
            } else {
                weekStartDay = mQuestion.day%7;
            }
            mQuestionFragment.questionNumberView.setText("DAY" + weekStartDay + "/" + Common.getContext().getString(R.string.nth_question, mQuestionFragment.common.questionNumberInDay));
            mQuestionFragment.questionTextView.setText(mQuestion.question.getString("sentence"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setTextOnChoiceFragment(ChoiceFragment mChoiceFragment) {
        try {
            mChoiceFragment.answerBtn1.setText(mQuestion.choices.getJSONObject(0).getString("title"));
            mChoiceFragment.answerBtn1.setTransformationMethod(null);
            mChoiceFragment.answerBtn1.setTag(mQuestion.choices.getJSONObject(0).getInt("id"));
            mChoiceFragment.answerBtn2.setText(mQuestion.choices.getJSONObject(1).getString("title"));
            mChoiceFragment.answerBtn2.setTransformationMethod(null);
            mChoiceFragment.answerBtn2.setTag(mQuestion.choices.getJSONObject(1).getInt("id"));
            mChoiceFragment.answerBtn3.setText(mQuestion.choices.getJSONObject(2).getString("title"));
            mChoiceFragment.answerBtn3.setTransformationMethod(null);
            mChoiceFragment.answerBtn3.setTag(mQuestion.choices.getJSONObject(2).getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
