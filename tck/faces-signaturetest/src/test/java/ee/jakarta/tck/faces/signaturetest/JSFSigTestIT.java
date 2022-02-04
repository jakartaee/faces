/*
 * Copyright (c) 2007, 2022 Oracle and/or its affiliates. All rights reserved.
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

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.PrintStream;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/*
 * This class is a simple example of a signature test that extends the
 * SigTest framework class.  This signature test is run outside of the
 * EE containers.
 */

public class JSFSigTestIT extends SigTestEE {

  public JSFSigTestIT(){
    setup();
  }
  /***** Abstract Method Implementation *****/

  /**
   * Returns a list of strings where each string represents a package name. Each
   * package name will have it's signature tested by the signature test
   * framework.
   *
   * @return String[] The names of the packages whose signatures should be
   *         verified.
   */
  protected String[] getPackages() {
    return new String[] { "jakarta.faces", "jakarta.faces.application",
        "jakarta.faces.component",
        "jakarta.faces.component.behavior", "jakarta.faces.component.html",
        "jakarta.faces.component.visit", "jakarta.faces.context",
        "jakarta.faces.convert", "jakarta.faces.el", "jakarta.faces.event",
        "jakarta.faces.flow", "jakarta.faces.flow.builder", "jakarta.faces.lifecycle",
        "jakarta.faces.model", "jakarta.faces.render", "jakarta.faces.validator",
        "jakarta.faces.view", "jakarta.faces.view.facelets", "jakarta.faces.webapp" };
  }


  public File writeStreamToTempFile(InputStream inputStream, String tempFilePrefix, String tempFileSuffix) throws IOException {
    FileOutputStream outputStream = null;

    try {
        File file = File.createTempFile(tempFilePrefix, tempFileSuffix);
        outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            outputStream.write(buffer, 0, bytesRead);
        }
        return file;
    }

    finally {
        if (outputStream != null) {
            outputStream.close();
        }
    }
  }

  public File writeStreamToSigFile(InputStream inputStream, String packageVersion) throws IOException {
    FileOutputStream outputStream = null;
    String tmpdir = System.getProperty("java.io.tmpdir");
    try {
        File sigfile = new File(tmpdir+File.separator+"jakarta.faces.sig_"+packageVersion);
        if(sigfile.exists()){
          sigfile.delete();
          TestUtil.logMsg("Existing signature file deleted to create new one");
        }
        if(!sigfile.createNewFile()){
          TestUtil.logErr("signature file is not created");
        }
        outputStream = new FileOutputStream(sigfile);
        byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            outputStream.write(buffer, 0, bytesRead);
        }
        return sigfile;
    }

    finally {
        if (outputStream != null) {
            outputStream.close();
        }
    }
  }

  /** 
   * @testName: signatureTest
   * 
   * @assertion: A JSF platform must implement the required classes and and APIs
   * specified in the JSF Specification.
   * 
   * @test_Strategy: Using reflection, gather the implementation specific
   * classes and APIs. Compare these results with the expected (required)
   * classes and APIs.
   *
   * This method utilizes the state information set in the setup method to 
   * run the signature tests. 
   *
   * @throws Fault
   *           When an error occurs executing the signature tests.
   */
  @Test
  public void signatureTest() throws Fault {
    TestUtil.logMsg("$$$ SigTestEE.signatureTest() called");
    SigTestResult results = null;
    String mapFile = null;
    String packageFile = null;
    String repositoryDir = null;
    Properties mapFileAsProps = null;
    try {

    InputStream inStreamMapfile = JSFSigTestIT.class.getClassLoader().getResourceAsStream("ee/jakarta/tck/faces/signaturetest/sig-test.map");
    File mFile = writeStreamToTempFile(inStreamMapfile, "sig-test", ".map");
    mapFile = mFile.getCanonicalPath();
    TestUtil.logMsg("mapFile location is :"+mapFile);

    InputStream inStreamPackageFile = JSFSigTestIT.class.getClassLoader().getResourceAsStream("ee/jakarta/tck/faces/signaturetest/sig-test-pkg-list.txt");
    File pFile = writeStreamToTempFile(inStreamPackageFile, "sig-test-pkg-list", ".txt");
    packageFile = pFile.getCanonicalPath();
    TestUtil.logMsg("packageFile location is :"+packageFile);
  
    mapFileAsProps = getSigTestDriver().loadMapFile(mapFile);
    String packageVersion = mapFileAsProps.getProperty("jakarta.faces");
    TestUtil.logMsg("Package version from mapfile :"+packageVersion);

    InputStream inStreamSigFile = JSFSigTestIT.class.getClassLoader().getResourceAsStream("ee/jakarta/tck/faces/signaturetest/jakarta.faces.sig_"+packageVersion);
    File sigFile = writeStreamToSigFile(inStreamSigFile, packageVersion);
    TestUtil.logMsg("signature File location is :"+sigFile.getCanonicalPath());
    repositoryDir = System.getProperty("java.io.tmpdir");


    } catch(IOException ex){
        TestUtil.logMsg("Exception while creating temp files :"+ex);
    }

    String[] packages = getPackages();
    String[] classes = getClasses();
    String testClasspath = System.getProperty("signature.sigTestClasspath");
    String optionalPkgToIgnore = testInfo.getOptionalTechPackagesToIgnore();

    // unlisted optional packages are technology packages for those optional
    // technologies (e.g. jsr-88) that might not have been specified by the
    // user.
    // We want to ensure there are no full or partial implementations of an
    // optional technology which were not declared
    ArrayList<String> unlistedTechnologyPkgs = getUnlistedOptionalPackages();

    // If testing with Java 9+, extract the JDK's modules so they can be used
    // on the testcase's classpath.
    Properties sysProps = System.getProperties();
    String version = (String) sysProps.get("java.version");
    if (!version.startsWith("1.")) {
      String jimageDir = testInfo.getJImageDir();
      File f = new File(jimageDir);
      f.mkdirs();

      String javaHome = (String) sysProps.get("java.home");
      TestUtil.logMsg("Executing JImage");

      try {
        ProcessBuilder pb = new ProcessBuilder(javaHome + "/bin/jimage", "extract", "--dir=" + jimageDir, javaHome + "/lib/modules");
        TestUtil.logMsg(javaHome + "/bin/jimage extract --dir=" + jimageDir + " " + javaHome + "/lib/modules");
        pb.redirectErrorStream(true);
        Process proc = pb.start();
        BufferedReader out = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line = null;
        while ((line = out.readLine()) != null) {
          TestUtil.logMsg(line);
        }

        int rc = proc.waitFor();
        TestUtil.logMsg("JImage RC = " + rc);
        out.close();
      } catch (Exception e) {
        TestUtil.logMsg("Exception while executing JImage!  Some tests may fail.");
        e.printStackTrace();
      }
    }

    try {
      results = getSigTestDriver().executeSigTest(packageFile, mapFile,
          repositoryDir, packages, classes, testClasspath,
          unlistedTechnologyPkgs, optionalPkgToIgnore);
      
      TestUtil.logMsg(results.toString());
      if (!results.passed()) {
        TestUtil.logErr("results.passed() returned false");
        throw new Exception();
      }

      TestUtil.logMsg("$$$ SigTestEE.signatureTest() returning");
    } catch (Exception e) {
      if (results != null && !results.passed()) {
        throw new Fault("SigTestEE.signatureTest() failed!, diffs found");
      } else {
        TestUtil.logErr("Unexpected exception " + e.getMessage());
        throw new Fault("signatureTest failed with an unexpected exception", e);
      }
    }
  }

} 