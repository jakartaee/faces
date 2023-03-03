/*
 * Copyright (c) 2021 Contributors to Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.util.selenium;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * a simple regexp converter converting central patterns
 * of the tests on the quick
 */
public class Converter {
    public static void main(String... argv) {

        String sourceDir = "/Users/werpu2/IdeaProjects/faces/tck/faces40/ajax/src/test/java/ee/jakarta/tck/faces/test/servlet50/ajax/";
        String targetDir = "/Users/werpu2/IdeaProjects/faces/tck/faces40/ajax/src/test/java/ee/jakarta/tck/faces/test/servlet50/ajax_new/";
        try (Stream<Path> paths = Files.walk(Paths.get(sourceDir));) {
            paths.filter(path -> {
                        return path.getFileName().toString().endsWith(".java");
                    })
                    .map(path -> {
                        Path target = Path.of(targetDir, path.getFileName().toString());
                        return new Path[]{path, target};
                    }).filter(srcTarget -> !Files.exists(srcTarget[1]))
                    .forEach(srcTarget -> processFile(srcTarget[0], srcTarget[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void processFile(Path src, Path target) {
        try (
                FileReader fr = new FileReader(src.toFile());
                FileWriter fw = new FileWriter(target.toFile());
                BufferedReader br = new BufferedReader(fr);
                BufferedWriter wr = new BufferedWriter(fw);
        ) {
            StringBuilder fileBuf = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                fileBuf.append(line);
                fileBuf.append(System.lineSeparator());
            }
            wr.write(convert(fileBuf.toString()));
            wr.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String convert(String in) {
        in = in.replaceAll("package\\s+ee.jakarta.tck.faces.test.javaee8.ajax\\s*;", "package ee.jakarta.tck.faces.test.javaee8.ajax_new;");
        in = in.replaceAll("page\\.getHtmlElementById\\(([^\\)]+)\\)", "webDriver.findElement(By.id($1))");
        in = in.replaceAll("page\\.getElementById\\(([^\\)]+)\\)", "webDriver.findElement(By.id($1))");
        in = in.replaceAll("\\.asNormalizedText\\(\\)", ".getText()");
        in = in.replaceAll("HtmlPage\\s+page\\s*\\=\\s*", "");
        in = in.replaceAll("page\\s*=\\s*\\(\\s*HtmlPage\\s*\\)\\s*", "");
        in = in.replaceAll("page\\s*\\=\\s*", "");
        in = in.replaceAll("page\\.getWebResponse\\(\\).getStatusCode\\(\\)","webDriver.getResponseStatus()");
        in = in.replaceAll("page\\.getText\\(\\)", "webDriver.getPageSource()");
        in = in.replaceAll("\\.click\\(\\);", ".click();\n        waitForCurrentRequestEnd();");

        in = in.replaceAll("HtmlSubmitInput\\s+(\\w+)\\s*=\\s*\\(HtmlSubmitInput\\)\\s*", "WebElement $1 = ");
        in = in.replaceAll("HtmlTextInput\\s+(\\w+)\\s*=\\s*\\(HtmlTextInput\\)\\s*", "WebElement $1 = ");
        in = in.replaceAll("HtmlInput\\s+(\\w+)\\s*=\\s*\\(HtmlInput\\)\\s*", "WebElement $1 = ");

        in = in.replaceAll("\\(HtmlInput\\)(\\s*)", "$1");
        in = in.replaceAll("\\(HtmlSubmitInput\\)","");
        in = in.replaceAll("HtmlSubmitInput","WebElement");

        in = in.replaceAll("\\(HtmlCheckBoxInput\\)","");
        in = in.replaceAll("HtmlCheckBoxInput","WebElement");

        in = in.replaceAll("\\(HtmlRadioButtonInput\\)","");
        in = in.replaceAll("HtmlRadioButtonInput","WebElement");


        in = in.replaceAll("\\(HtmlSpan\\)","");
        in = in.replaceAll("HtmlSpan","WebElement");
        in = in.replaceAll("\\(HtmlSelect\\)","");
        in = in.replaceAll("HtmlSelect","WebElement");

        in = in.replaceAll("\\(HtmlAnchor\\)","");
        in = in.replaceAll("HtmlAnchor","WebElement");
        in = in.replaceAll("page\\.asXml\\(\\)","webDriver.getPageSource()");
        in = in.replaceAll("webDriver\\.getPageSource\\(\\)\\.contains\\(", "isInPage(");

        in = in.replaceAll("\\(HtmlTextInput\\)","");
        in = in.replaceAll("HtmlTextInput","WebElement");
        in = in.replaceAll("\\.isChecked\\(\\)",".isSelected()");

        in = in.replaceAll("webClient\\.waitForBackgroundJavaScript\\(([^\\)]+)\\)\\s*\\;", "Thread.sleep($1);");
        in = in.replaceAll("\\.type\\(([^\\)]+)\\)", ".sendKeys($1)");
        in = in.replaceAll("extends\\s+ITBase", "extends BaseITNG");
        in = in.replaceAll("import com\\.gargoylesoftware\\.htmlunit[^\\;]+\\;\\n+", "");
        in = in.replaceAll("import ee\\.jakarta\\.tck\\.faces\\.test\\.util\\.arquillian\\.ITBase\\;", "");

        in = in.replaceFirst("import\\s+", "import org.openqa.selenium.By;\n" +
                "import org.openqa.selenium.WebElement;\n" + "import ");

        return in;
    }
}
