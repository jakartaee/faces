/*
 * Copyright (c) 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package ee.jakarta.tck.faces.signaturetest;

import java.io.*;
import java.util.*;
import java.net.*;
import java.text.SimpleDateFormat;

/**
 * TestUtil is a final utility class responsible for implementing logging across
 * multiple VMs. It also contains many convenience methods for logging property
 * object contents, stacktraces, and header lines.
 *
 */
public final class TestUtil {
  public static boolean traceflag = true;

  public static String NEW_LINE = System.getProperty("line.separator", "\n");

  private static PrintWriter out = null;

  private static PrintWriter err = null;

  private static PrintWriter additionalWriter = null;

  private static ObjectOutputStream objectOutputStream = null;

  private static ObjectOutputStream objectInputStream = null;

  private static int portOfHarness = 2000;

  // debug flag for printing test debug output
  public static boolean logDebugFlag;

  // hang onto the props that are passed in during logging init calls
  private static Properties testProps = null;

  private static SimpleDateFormat df = new SimpleDateFormat(
      "MM-dd-yyyy HH:mm:ss");

  static {
    logDebugFlag = Boolean.getBoolean("tck.faces.log");
    traceflag = Boolean.getBoolean("tck.faces.trace");
  }

  /**
   * used by harness to log debug output to the standard output stream
   * 
   * @param s
   *          the output string
   */
  public static void logTCKDebug(String s) {
    if (logDebugFlag) {
      logTCK(s, null);
    }
  }

  /**
   * used by TSTestFinder and TSScript to log output to the standard output
   * stream
   *
   * @param s
   *          the output string
   * @param t
   *          a Throwable whose stacktrace gets printed
   */
  public static void logTCK(String s, Throwable t) {
    synchronized (System.out) {
      System.out.println(df.format(new Date()) + ":" + s);
      //logToAdditionalWriter(s, t);
      if (t != null) {
        t.printStackTrace();
      }
    }
  }

  public static void logTCK(String s) {
    logTCK(s, null);
  }


  /**
   * prints a string to the log stream. All tests should use this method for
   * standard logging messages
   *
   * @param s
   *          string to print to the log stream
   */
  public static void logMsg(String s) {
    logTCK(s);
  }

  /**
   * prints a string as well as the provided Throwable's stacktrace to the log
   * stream. All tests should use this method for standard logging messages
   *
   * @param s
   *          string to print to the log stream
   * @param t
   *          - throwable whose stacktrace gets printed*
   *
   */
  public static void logMsg(String s, Throwable t) {
    logTCKDebug(s);
    if (t != null) {
      t.printStackTrace();
    }
  }

  /**
   * turns on/off debugging. Once on, all calls to the logTrace method result in
   * messages being printed to the log stream. If off, all logTrace calls are
   * not printed.
   *
   * @param b
   *          If <code>true</code>, debugging is on. If false, debugging is
   *          turned off.
   */
  public static void setTrace(boolean b) {
    traceflag = b;
  }

  /**
   * prints a debug string to the log stream. All tests should use this method
   * for verbose logging messages. Whether or not the string is printed is
   * determined by the last call to the setTrace method.
   *
   * @param s
   *          string to print to the log stream
   */
  public static void logTrace(String s) {
    logTrace(s, null);
  }

  /**
   * Prints a debug string as well as the provided Throwable's stacktrace. Use
   * this if certain exceptions are only desired while tracing.
   *
   * @param s
   *          - string to print to the log stream
   * @param t
   *          - throwable whose stactrace gets printed
   */
  public static void logTrace(String s, Throwable t) {
    if (traceflag) {
      logTCKDebug(s);
      if (t != null) {
        t.printStackTrace();
      }
    }
  }

  /**
   * prints an error string to the error stream. All tests should use this
   * method for error messages.
   *
   * @param s
   *          string to print to the error stream
   * @param e
   *          a Throwable whose stacktrace gets printed
   */
  public static void logErr(String s, Throwable e) {
    logTCK(s);
    if (e != null) {
      e.printStackTrace();
    }
  }

  /**
   * prints an error string to the error stream. All tests should use this
   * method for error messages.
   *
   * @param s
   *          string to print to the error stream
   */
  public static void logErr(String s) {
    logErr(s, null);
  }

  /**
   * prints the stacktrace of a Throwable to the logging stream
   *
   * @param e
   *          exception to print the stacktrace of
   */
  public static void printStackTrace(Throwable e) {
    if (e == null) {
      return;
    }
    try {
      StringWriter sw = new StringWriter();
      PrintWriter writer = new PrintWriter(sw);
      e.printStackTrace(writer);
      logErr(sw.toString());
      writer.close();
    } catch (Exception E) {
    }
  }

  /**
   * prints the stacktrace of a Throwable to a string
   *
   * @param e
   *          exception to print the stacktrace of
   */
  public static String printStackTraceToString(Throwable e) {
    String sTrace = "";
    if (e == null)
      return "";
    try {
      StringWriter sw = new StringWriter();
      PrintWriter writer = new PrintWriter(sw);
      e.printStackTrace(writer);
      sTrace = sw.toString();
      writer.close();
    } catch (Exception E) {
    }
    return sTrace;
  }

  /**
   * prints a line of asterisks to the logging stream
   */
  public static void separator2() {
    logMsg("**************************************************"
        + "******************************");
  }

  /**
   * prints a line of dashes to the logging stream
   */
  public static void separator1() {
    logMsg("--------------------------------------------------"
        + "------------------------------");
  }

}
