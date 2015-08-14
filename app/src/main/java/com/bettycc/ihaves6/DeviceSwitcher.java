package com.bettycc.ihaves6;

import android.content.Context;

/**
 * Created by cc on 8/10/15.
 */
public abstract class DeviceSwitcher {

    public Context getContext() {
        return mContext;
    }

    private Context mContext;

    public DeviceSwitcher(Context context) {
        mContext = context;
    }

    abstract boolean execute();
}
