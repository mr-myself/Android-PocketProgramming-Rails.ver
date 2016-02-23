package ffab0.pocketprogramming.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.models.WeekSummary;
import ffab0.pocketprogramming.viewModel.SummaryViewModel;

public class SummaryAnalysisFragment extends Fragment {

    public static SummaryAnalysisFragment newInstance(JSONObject weekSummary) {
        SummaryAnalysisFragment fragment = new SummaryAnalysisFragment();
        Bundle args = new Bundle();
        args.putString("weekSummary", weekSummary.toString());
        fragment.setArguments(args);

        return fragment;
    }

    public SummaryAnalysisFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary_analysis, container, false);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(getArguments().getString("weekSummary"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WeekSummary mWeekSummary = new WeekSummary(jsonObject);
        SummaryViewModel.analysisList(inflater, view, mWeekSummary, 0);

        return view;
    }
}
