package com.example.ordermadeeasy.utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class CustomEditTextWithFont extends AppCompatEditText {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public CustomEditTextWithFont(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomEditTextWithFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public CustomEditTextWithFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getResources().getAssets(), "fonts/Roboto-Regular.ttf");
        this.setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "fonts/Roboto-Regular.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "fonts/Roboto-Regular.ttf");
        super.setTypeface(tf);
    }

}