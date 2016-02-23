package ffab0.pocketprogramming.util;

import android.content.res.Resources;
import android.support.v4.view.PagerTabStrip;
import android.util.TypedValue;
import android.view.Gravity;

import ffab0.pocketprogramming.R;

public class Pager {

    static public void set(PagerTabStrip pagerTabStrip, Resources resources) {
        // 文字サイズを変更する
        pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        // ページタイトルの間隔を変更する
        pagerTabStrip.setTextSpacing(1);
        // 非表示のページタイトルの透過度を変更する
        pagerTabStrip.setNonPrimaryAlpha(0.3f);
        pagerTabStrip.setTextColor(resources.getColor(R.color.md_white_1000));
        pagerTabStrip.setBackgroundColor(resources.getColor(R.color.md_red_400));
        // ページタイトルのインジケータの色を変更する
        pagerTabStrip.setTabIndicatorColor(resources.getColor(R.color.md_white_1000));
        pagerTabStrip.setGravity(Gravity.LEFT);
    }
}
