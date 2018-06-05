/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.shell;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 *
 * @author
 */
public class RunShellUtil {

    public static String runScript(String[] cmd) {
        StringBuilder buf = new StringBuilder();
        String rt;
        try {
            Process pos = Runtime.getRuntime().exec(cmd);
            pos.waitFor();
            try (InputStreamReader ir = new InputStreamReader(pos.getInputStream()); LineNumberReader input = new LineNumberReader(ir)) {
                String ln;
                while ((ln = input.readLine()) != null) {
                    buf.append(ln).append("\n");
                }
                rt = buf.toString();
            }
        } catch (java.io.IOException | InterruptedException e) {
            rt = e.toString();
        }
        return rt;
    }
    
    public static String runScript(String cmd) {
        StringBuilder buf = new StringBuilder();
        String rt;
        try {
            Process pos = Runtime.getRuntime().exec(cmd);
            pos.waitFor();
            try (InputStreamReader ir = new InputStreamReader(pos.getInputStream()); LineNumberReader input = new LineNumberReader(ir)) {
                String ln;
                while ((ln = input.readLine()) != null) {
                    buf.append(ln).append("\n");
                }
                rt = buf.toString();
            }
        } catch (java.io.IOException | InterruptedException e) {
            rt = e.toString();
        }
        return rt;
    }
}
