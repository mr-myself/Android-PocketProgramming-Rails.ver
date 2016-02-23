package ffab0.pocketprogramming.managers;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.crashlytics.android.Crashlytics;
import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.fragments.MainFragment;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.models.UserId;
import ffab0.pocketprogramming.util.GlobalNavigation;
import ffab0.pocketprogramming.util.NetWorkChecker;
import io.fabric.sdk.android.Fabric;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class MainActivity extends ActionBarActivity {

    public static String UUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        FacebookSdk.sdkInitialize(getApplicationContext());

        if (NetWorkChecker.check()) {
            UserId.postToServer();

            // 問題番号を初期化
            Common common = (Common) getApplication();
            common.init();

            setContentView(R.layout.activity_main);
            FragmentManager fragment_manager = getFragmentManager();
            FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();

            setGlobalNavigation(fragment_transaction);

            MainFragment main_fragment = MainFragment.newInstance(UUID);
            fragment_transaction.add(R.id.mainContents, main_fragment);

            fragment_transaction.commit();
        } else {
            Intent intent = new Intent(this, NoNetworkActivity.class);
            startActivity(intent);
        }
    }

    private void setGlobalNavigation(FragmentTransaction fragment_transaction) {
        GlobalNavigation.set(fragment_transaction);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events. (For Facebook)
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
