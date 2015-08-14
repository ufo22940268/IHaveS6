package com.bettycc.ihaves6;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by cc on 8/10/15.
 */
public class SamsungDeviceSwitcherTest extends AndroidTestCase {

    public void setUp() throws Exception {
        super.setUp();
        Shell.SU.available();
    }

    public void testExecute() throws Exception {
        System.out.println(executeCommand("su -c id"));
//        Runtime.getRuntime().exec(new String[] {"su", "-c", "mount -o rw,remount -t yaffs2 /dev/block/mtdblock3 /system"});
//        executeCommand("su -c 'touch /sdcard/k'");
//        Runtime.getRuntime().exec(new String[]{"su", "-c", "printf '\nasdfasdf=12312' >> /system/build.prop"}).waitFor();
        PropertyUtils.replace(getContext(), new DeviceInfo("a", "b"));
    }

    private String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = null;
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
            p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("output = " + output);
        return output.toString();
    }
}