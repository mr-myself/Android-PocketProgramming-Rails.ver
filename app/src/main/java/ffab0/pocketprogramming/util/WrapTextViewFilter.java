package ffab0.pocketprogramming.util;

import android.text.InputFilter;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.widget.TextView;

public class WrapTextViewFilter implements InputFilter {
    private final TextView view;

    public WrapTextViewFilter(TextView view) {
        this.view = view;
    }

    //@Override
    public CharSequence filter(CharSequence source, int start, int end,    Spanned dest, int dstart, int dend) {
        TextPaint paint = view.getPaint();

        int w = view.getWidth();
        int wpl = view.getCompoundPaddingLeft();
        int wpr = view.getCompoundPaddingRight();
        int width = w - wpl - wpr;

        SpannableStringBuilder result = new SpannableStringBuilder();
        for (int index = start; index < end; index++) {
            // TextViewの設定幅を超えたら改行
            if (Layout.getDesiredWidth(source, start, index + 1, paint) > width) {
                result.append(source.subSequence(start, index));
                result.append("\n");
                start = index;
            // 改行コードがある所はそのまま改行
            } else if (source.charAt(index) == '\n') {
                result.append(source.subSequence(start, index));
                start = index;
            }
        }

        if (start < end) {
            result.append(source.subSequence(start, end));
        }

        return result;
    }
}
