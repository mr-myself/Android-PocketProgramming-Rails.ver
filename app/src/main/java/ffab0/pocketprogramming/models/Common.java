package ffab0.pocketprogramming.models;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.Locale;

public class Common extends Application {
    public int questionNumberInDay;
    private static Context mContext;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    public void init(){
        questionNumberInDay = 0;
    }

    public void increment() {
        if (this.questionNumberInDay > 10) {
            this.init();
        }
        this.questionNumberInDay++;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-45186381-8"); // Replace with actual tracker/property Id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
    }

    public static synchronized Context getContext() {
        return mContext;
    }

    public static String getLanguage() {
        Locale locale = mContext.getResources().getConfiguration().locale;
        return locale.getLanguage().equals("ja") ? "ja" : "en";
    }
}
