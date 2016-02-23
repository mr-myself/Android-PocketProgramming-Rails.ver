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
import android.widget.ImageView;
import android.widget.TextView;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.models.TwitterShare;


public class LevelUpDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String MAIN_GENRE = "MAIN_GENRE";
    private static final String NEW_LEVEL = "NEW_LEVEL";
    private static final String OLD_LEVEL = "OLD_LEVEL";

    public static LevelUpDialogFragment newInstance(String mainGenre, String oldLevel, String newLevel) {
        LevelUpDialogFragment fragment = new LevelUpDialogFragment();
        Bundle args = new Bundle();
        args.putString(MAIN_GENRE, mainGenre);
        args.putString(NEW_LEVEL, newLevel);
        args.putString(OLD_LEVEL, oldLevel);
        fragment.setArguments(args);

        return fragment;
    }

    public LevelUpDialogFragment() {
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
        String packageName = Common.getContext().getPackageName();
        View view = inflater.inflate(R.layout.fragment_level_up_dialog, null);

        TextView levelUpTargetText = (TextView) view.findViewById(R.id.levelUpTarget);
        levelUpTargetText.setText(getString(R.string.level_up, getArguments().getString(MAIN_GENRE)));

        ImageView originalLevelImage = (ImageView) view.findViewById(R.id.originalLevel);
        originalLevelImage.setImageResource(getResources().getIdentifier("rank_" + getArguments().getString(OLD_LEVEL), "drawable", packageName));

        ImageView newLevelImage = (ImageView) view.findViewById(R.id.newLevel);
        newLevelImage.setImageResource(getResources().getIdentifier("rank_" + getArguments().getString(NEW_LEVEL), "drawable", packageName));

        Button shareToTwitterBtn = (Button) view.findViewById(R.id.shareToTwitter);
        shareToTwitterBtn.setOnClickListener(this);
        Button closeShareModalBtn = (Button) view.findViewById(R.id.closeShareModalBtn);
        closeShareModalBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.shareToTwitter:
                String mainGenre = getArguments().getString(MAIN_GENRE);
                String newLevel = getArguments().getString(NEW_LEVEL).toUpperCase();
                String oldLevel = getArguments().getString(OLD_LEVEL).toUpperCase();
                String url = TwitterShare.levelUp(mainGenre, oldLevel, newLevel);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                dismiss();
                break;
            case R.id.closeShareModalBtn:
                dismiss();
                break;
        }
    }
}