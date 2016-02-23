package ffab0.pocketprogramming.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import ffab0.pocketprogramming.R;

public class QuestionAndAnswerFragment extends Fragment {

    public static QuestionAndAnswerFragment newInstance(JSONObject questionAndExplanation, int position) {
        QuestionAndAnswerFragment fragment = new QuestionAndAnswerFragment();
        Bundle args = new Bundle();
        try {
            args.putInt("POSITION", position);
            args.putString("SENTENCE", questionAndExplanation.getString("text"));
            args.putString("EXPLANATION", questionAndExplanation.getString("explanation"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fragment.setArguments(args);

        return fragment;
    }

    public QuestionAndAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_and_answer, container, false);

        TextView questionNumber = (TextView) view.findViewById(R.id.questionNumber);
        questionNumber.setText(getString(R.string.nth_question, getArguments().getInt("POSITION") + 1));
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.post(new Runnable() {
            @Override
            public void run() {
                // Widthをちゃんと取るためにViewの描画後に実行
                TextView sentenceText = (TextView) view.findViewById(R.id.sentenceText);
                TextView explanationText = (TextView) view.findViewById(R.id.explanationText);
                sentenceText.setText(getArguments().getString("SENTENCE"));
                explanationText.setText(getArguments().getString("EXPLANATION"));
            }
        });
    }
}
