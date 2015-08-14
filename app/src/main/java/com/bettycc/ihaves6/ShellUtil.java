package com.bettycc.ihaves6;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by cc on 8/10/15.
 */
public class ShellUtil {

    public static void mountSystem() {
        su("mount -o rw,remount -t yaffs2 /dev/block/mtdblock3 /system");
    }

    public static String su(String cmd) {
        return executeCommand(cmd);
    }

    private static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = null;
        Process p;
        try {
            p = Runtime.getRuntime().exec(new String[] {"su", "-c", command});
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
            p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ShellException(e);
        }
        return output.toString();
    }
}
