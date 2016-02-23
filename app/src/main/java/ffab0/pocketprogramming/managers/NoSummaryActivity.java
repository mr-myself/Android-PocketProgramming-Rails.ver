package ffab0.pocketprogramming.managers;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.facebook.appevents.AppEventsLogger;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.fragments.NoSummaryFragment;
import ffab0.pocketprogramming.util.AnimationEffect;
import ffab0.pocketprogramming.util.GlobalNavigation;
import ffab0.pocketprogramming.util.NetWorkChecker;


public class NoSummaryActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NetWorkChecker.check()) {
            setContentView(R.layout.activity_no_summary);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            setGlobalNavigation(fragmentManager);

            NoSummaryFragment noSummaryFragment = new NoSummaryFragment();
            fragmentTransaction.add(R.id.mainContents, noSummaryFragment);
            fragmentTransaction.commit();

            setAnimation();

        } else {
            Intent intent = new Intent(this, NoNetworkActivity.class);
            startActivity(intent);
        }
    }

    private void setAnimation() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainContents);
        AnimationEffect.fadeIn(this, linearLayout);
    }

    private void setGlobalNavigation(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        GlobalNavigation.set(transaction);
        transaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
