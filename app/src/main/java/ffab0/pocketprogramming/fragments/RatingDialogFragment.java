package ffab0.pocketprogramming.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.environments.EnvironmentConstants;


public class RatingDialogFragment extends DialogFragment implements View.OnClickListener {

    public static RatingDialogFragment newInstance() {
        RatingDialogFragment fragment = new RatingDialogFragment();
        return fragment;
    }

    public RatingDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_dialog, null);

        Button toPlayStoreBtn = (Button) view.findViewById(R.id.toPlayStore);
        toPlayStoreBtn.setOnClickListener(this);
        Button closeShareModalBtn = (Button) view.findViewById(R.id.closeRatingModalBtn);
        closeShareModalBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.toPlayStore:
                Uri uri = Uri.parse(EnvironmentConstants.PLAY_STORE_URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                dismiss();
                break;
            case R.id.closeRatingModalBtn:
                dismiss();
                break;
        }
    }
}