package ffab0.pocketprogramming.models;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.environments.EnvironmentConstants;


public class TwitterShare {
    private static final String PRODUCT_URL = EnvironmentConstants.HOST_URL + "?src=share_tw";

    public static String levelUp(String mainGenre, String oldLevel, String newLevel) {
        String shareText = Common.getContext().getString(R.string.tweet_level_up, mainGenre, oldLevel, newLevel) + "%0a%0a";
        return completeUrl(shareText);
    }

    public static String dayScore(int week, int day, int score) {
        String shareText = Common.getContext().getString(R.string.tweet_day_score, week, day, score) + "%0a%0a";
        return completeUrl(shareText);
    }

    private static String completeUrl(String shareText) {
        return "http://twitter.com/share?text=" + shareText + "&via=" + Common.getContext().getString(R.string.twitter_account) + "&hashtags=" + Common.getContext().getString(R.string.twitter_hashtag) + "&url=" + PRODUCT_URL;
    }
}
