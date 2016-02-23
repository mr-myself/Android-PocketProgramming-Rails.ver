package ffab0.pocketprogramming.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.models.ReviewRepository;

public class ReviewFragment extends Fragment implements View.OnClickListener {

    public static ReviewFragment newInstance() {
        ReviewFragment fragment = new ReviewFragment();
        return fragment;
    }

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        ReviewRepository mReviewRepository = new ReviewRepository();
        mReviewRepository.fetchAll(this, inflater, view);

        return view;
    }

    public void onClick(View v) {
        FragmentManager fragment_manager = getFragmentManager();
        FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();

        int day = (int) v.getTag();
        PracticeFragment practice_fragment = PracticeFragment.newInstance(day, 0);
        fragment_transaction.replace(R.id.mainContents, practice_fragment);
        fragment_transaction.commit();
    }
}
