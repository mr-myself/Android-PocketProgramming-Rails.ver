package ffab0.pocketprogramming.managers;

import android.content.Intent;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.facebook.appevents.AppEventsLogger;

import org.json.JSONArray;
import org.json.JSONException;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.fragments.SummaryPagerAdapter;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.util.GlobalNavigation;
import ffab0.pocketprogramming.util.AnimationEffect;
import ffab0.pocketprogramming.util.NetWorkChecker;
import ffab0.pocketprogramming.util.Pager;


public class SummaryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NetWorkChecker.check()) {
            setContentView(R.layout.activity_summary);
            FragmentManager fragmentManager = getFragmentManager();

            setAnimation();
            setGlobalNavigation(fragmentManager);
            setPager();

            // 問題番号を初期化
            Common common = (Common) getApplication();
            common.init();

            JSONArray mAllWeekSummary = null;
            try {
                mAllWeekSummary = new JSONArray(getIntent().getExtras().getString("allWeekSummary"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SummaryPagerAdapter summaryPagerAdapter = new SummaryPagerAdapter(fragmentManager);
            summaryPagerAdapter.addItem(mAllWeekSummary);

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            viewPager.setAdapter(summaryPagerAdapter);

        } else {
            Intent intent = new Intent(this, NoNetworkActivity.class);
            startActivity(intent);
        }
    }

    private void setAnimation() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.summaryActivity);
        AnimationEffect.fadeIn(this, linearLayout);
    }

    private void setGlobalNavigation(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        GlobalNavigation.set(transaction);
        transaction.commit();
    }

    private void setPager() {
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        Pager.set(pagerTabStrip, getResources());
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
