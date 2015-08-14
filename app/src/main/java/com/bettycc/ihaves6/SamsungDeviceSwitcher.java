package com.bettycc.ihaves6;

import android.content.Context;

/**
 * Created by cc on 8/10/15.
 */
public class SamsungDeviceSwitcher extends DeviceSwitcher {

    public SamsungDeviceSwitcher(Context context) {
        super(context);
    }

    @Override
    public boolean execute() {
        PropertyUtils.backup();
        return PropertyUtils.replace(getContext(), new DeviceInfo("TestSamsung", "SM-G9200"));
    }
}
