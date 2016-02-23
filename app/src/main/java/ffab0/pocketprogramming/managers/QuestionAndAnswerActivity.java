package ffab0.pocketprogramming.managers;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.facebook.appevents.AppEventsLogger;

import org.json.JSONArray;
import org.json.JSONException;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.fragments.QuestionAndAnswerFragmentPagerAdapter;
import ffab0.pocketprogramming.models.Common;
import ffab0.pocketprogramming.util.NetWorkChecker;
import ffab0.pocketprogramming.util.Pager;

public class QuestionAndAnswerActivity extends ActionBarActivity implements OnClickListener {

    public JSONArray mDayQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NetWorkChecker.check()) {
            setContentView(R.layout.activity_question_and_answer);
            setPager();

            // 問題番号を初期化
            Common common = (Common) getApplication();
            common.init();

            Intent intent = getIntent();
            String mDayQuestionsString = intent.getStringExtra("mDayQuestions");
            try {
                mDayQuestions = new JSONArray(mDayQuestionsString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getFragmentManager();
            QuestionAndAnswerFragmentPagerAdapter questionAndAnswerFragmentPagerAdapter = new QuestionAndAnswerFragmentPagerAdapter(fragmentManager);
            questionAndAnswerFragmentPagerAdapter.addItem(mDayQuestions);

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            viewPager.setAdapter(questionAndAnswerFragmentPagerAdapter);
            viewPager.setCurrentItem(intent.getIntExtra("questionPosition", 0));
        } else {
            Intent intent = new Intent(this, NoNetworkActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
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
