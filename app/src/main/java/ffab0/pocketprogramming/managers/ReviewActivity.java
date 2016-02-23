package ffab0.pocketprogramming.managers;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.facebook.appevents.AppEventsLogger;

import ffab0.pocketprogramming.fragments.GlobalNavigationFragment;
import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.fragments.ReviewFragment;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.util.AnimationEffect;
import ffab0.pocketprogramming.util.NetWorkChecker;


public class ReviewActivity extends ActionBarActivity {
    public static String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NetWorkChecker.check()) {
            setContentView(R.layout.activity_review);
            PACKAGE_NAME = getApplicationContext().getPackageName();

            setAnimation();
            // 問題番号を初期化
            Common common = (Common) getApplication();
            common.init();

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            GlobalNavigationFragment global_navigation = new GlobalNavigationFragment();
            transaction.add(R.id.globalNavigation, global_navigation);

            ReviewFragment review_fragment = new ReviewFragment();
            transaction.add(R.id.mainContents, review_fragment);
            transaction.commit();
        } else {
            Intent intent = new Intent(this, NoNetworkActivity.class);
            startActivity(intent);
        }
    }

    private void setAnimation() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainContents);
        AnimationEffect.fadeIn(this, linearLayout);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
