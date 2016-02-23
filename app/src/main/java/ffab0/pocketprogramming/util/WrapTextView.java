package ffab0.pocketprogramming.util;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.TextView;

public class WrapTextView extends TextView{
    private CharSequence mOrgText = "";
    private BufferType mOrgBufferType = BufferType.NORMAL;

    public WrapTextView(Context context) {
        super(context);
        setFilters(new InputFilter[] { new WrapTextViewFilter(this) });
    }

    public WrapTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFilters(new InputFilter[] { new WrapTextViewFilter(this) });
    }

    public WrapTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFilters(new InputFilter[]{new WrapTextViewFilter(this)});
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        setText(mOrgText, mOrgBufferType);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        mOrgText = text;
        mOrgBufferType = type;
        super.setFilters(new InputFilter[]{new WrapTextViewFilter(this)});
        super.setText(text, type);
    }

    @Override
    public CharSequence getText() {
        return mOrgText;
    }

    @Override
    public int length() {
        return mOrgText.length();
    }
}
