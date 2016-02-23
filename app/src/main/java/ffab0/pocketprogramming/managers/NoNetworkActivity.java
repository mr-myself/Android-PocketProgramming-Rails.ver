package ffab0.pocketprogramming.managers;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.util.GlobalNavigation;
import ffab0.pocketprogramming.util.NetWorkChecker;

public class NoNetworkActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NetWorkChecker.check()) {
            Intent intent = new Intent(this, MainActivity.class);

            // 問題番号を初期化
            Common common = (Common) getApplication();
            common.init();

            startActivity(intent);

        } else {
            setContentView(R.layout.activity_no_network);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            setGlobalNavigation(fragmentManager);
            fragmentTransaction.commit();
        }
    }

    private void setGlobalNavigation(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        GlobalNavigation.set(transaction);
        transaction.commit();
    }
}
