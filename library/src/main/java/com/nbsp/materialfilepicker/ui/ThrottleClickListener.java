package com.nbsp.materialfilepicker.ui;

import android.os.SystemClock;
import android.view.View;

abstract class ThrottleClickListener implements View.OnClickListener {

    private static final long MIN_CLICK_INTERVAL = 600;

    private long mLastClickTime;

    abstract void onSingleClick(View v);

    @Override
    public final void onClick(View v) {
        final long currentClickTime = SystemClock.uptimeMillis();
        final long elapsedTime = currentClickTime - mLastClickTime;

        mLastClickTime = currentClickTime;

        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            return;
        }

        onSingleClick(v);
    }
}