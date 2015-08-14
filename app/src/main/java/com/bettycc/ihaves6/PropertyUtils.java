package com.bettycc.ihaves6;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;


/**
 * Created by cc on 8/10/15.
 */
public class PropertyUtils {

    public static final String SYSTEM_BUILD_FILE = "/system/build.prop";
    public static final String SYSTEM_BUILD_TMP_FILE = "/system/tmpbuild.prop";
    public static final String SDCARD_BACKUPBUILD_PROP = "/sdcard/backupbuild.prop";

    public static boolean replace(Context context, DeviceInfo deviceInfo) {
        return replace(context, deviceInfo.getBrand(), deviceInfo.getModel());
    }

    private static boolean replace(Context context, String brand, String model) {
        List<String> catList = Arrays.asList(ShellUtil.su("cat " + SYSTEM_BUILD_FILE).split("\n"));
        if (catList != null && catList.size() > 0) {
            String originalModel = null;
            String originalBrand = null;
            for (int i = 0; i < catList.size(); i++) {
                String line = catList.get(i);
                String newLine = line;
                if (line.contains("ro.product.model")) {
                    originalModel = getValue(line);
                    newLine = line.replaceFirst("(.+=)(.+)", "$1" + model);
                } else if (line.contains("ro.product.brand")) {
                    originalBrand = getValue(line);
                    newLine = line.replaceFirst("(.+=)(.+)", "$1" + brand);
                }

                if (!newLine.equals(line)) {
                    catList.set(i, newLine);
                }
            }

            saveDeviceInfo(context, new DeviceInfo(originalBrand, originalModel));
            writeToFile(catList);

            return true;
        }
        return false;
    }

    private static void saveDeviceInfo(Context context, DeviceInfo deviceInfo) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Constants.PREF_CONFIG, Context.MODE_PRIVATE).edit();
        edit.putString(Constants.KEY_MODEL, deviceInfo.getModel());
        edit.putString(Constants.KEY_BRAND, deviceInfo.getBrand());
        edit.commit();
    }

    private static String getValue(String line) {
        return line.substring(line.indexOf("=") + 1);
    }

    private static void writeToFile(List<String> catList) {
        ShellUtil.mountSystem();
        ShellUtil.su(String.format("printf \"\" > %s", SYSTEM_BUILD_TMP_FILE));
        for (String s : catList) {
            ShellUtil.su(String.format("printf \"%s\n\" >> %s", s, SYSTEM_BUILD_TMP_FILE));
        }
        ShellUtil.su(String.format("rm %s; mv %s %s", SYSTEM_BUILD_FILE, SYSTEM_BUILD_TMP_FILE, SYSTEM_BUILD_FILE));
    }


    public static void backup() {
        ShellUtil.su(String.format("cp %s %s", SYSTEM_BUILD_FILE, SDCARD_BACKUPBUILD_PROP));
    }
}
