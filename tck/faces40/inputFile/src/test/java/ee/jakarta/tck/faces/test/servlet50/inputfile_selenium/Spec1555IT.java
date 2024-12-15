/*
 * Copyright (c) 2021, 2023 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.inputfile_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.ExtendedWebDriver;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.html.HtmlInputFile;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static org.junit.Assert.assertEquals;

public class Spec1555IT extends BaseITNG {

    /**
     * @see HtmlInputFile#isMultiple()
     * @see https://github.com/jakartaee/faces/issues/1555
     */
    @Test
    public void testSingleSelectionNonAjax() throws Exception {
        testSingleSelection("singleSelectionFormNonAjax");
    }

    /**
     * @see HtmlInputFile#isMultiple()
     * @see https://github.com/jakartaee/faces/issues/1555
     */
    @Test
    public void testSingleSelectionAjax() throws Exception {
        testSingleSelection("singleSelectionFormAjax");
    }

    private void testSingleSelection(String form) throws Exception {
        WebPage page = getPage("spec1555IT.xhtml");
        ExtendedWebDriver webDriver = getWebDriver();
        WebElement input = webDriver.findElement(By.id(form + ":input"));

        assertEquals("Multiple attribute is NOT set", null, input.getAttribute("multiple"));

        File file = generateTempFile("file", "bin", 123);
        // Selenium allows to send the file name as key input
        input.sendKeys(file.getAbsolutePath());

        page.guardAjax(webDriver.findElement(By.id(form + ":submit"))::click);
        assertEquals("Value attribute is NOT set", "", webDriver.findElement(By.id(form + ":input")).getAttribute("value"));

        WebElement messages = webDriver.findElement(By.id("messages"));

        List<WebElement> messagesElements = messages.findElements(By.cssSelector("*"));
        assertEquals("There is 1 message", 1, messagesElements.size());

        WebElement message = messagesElements.get(0);

        assertEquals("Uploaded file has been received", "field: singleSelection, name: " + file.getName() + ", size: " + file.length(), message.getText());
    }

    /**
     * @see HtmlInputFile#isMultiple()
     * @see https://github.com/jakartaee/faces/issues/1555
     */
    @Test
    public void testMultipleSelectionNonAjax() throws Exception {
        testMultipleSelection("multipleSelectionFormNonAjax");
    }

    /**
     * @see HtmlInputFile#isMultiple()
     * @see https://github.com/jakartaee/faces/issues/1555
     */
    @Test
    public void testMultipleSelectionAjax() throws Exception {
        testMultipleSelection("multipleSelectionFormAjax");
    }

    private void testMultipleSelection(String form) throws Exception {
        WebPage page = getPage("spec1555IT.xhtml");
        ExtendedWebDriver webDriver = getWebDriver();
        WebElement input = webDriver.findElement(By.id(form + ":input"));

        assertEquals("Multiple attribute is set", "true", input.getAttribute("multiple"));

        File file1 = generateTempFile("file1", "bin", 123);
        File file2 = generateTempFile("file2", "bin", 234);
        File file3 = generateTempFile("file3", "bin", 345);

        String[] fileNames = {
                file1.getAbsolutePath(),
                file2.getAbsolutePath(),
                file3.getAbsolutePath()
        };


        String files = String.join("\n", fileNames);
        input.sendKeys(files);
        page.guardAjax(webDriver.findElement(By.id(form + ":submit"))::click);

        assertEquals("Value attribute is NOT set", "", webDriver.findElement(By.id(form + ":input")).getAttribute("value"));

        WebElement messages = webDriver.findElement(By.id("messages"));
        List childElements = messages.findElements(By.cssSelector("*"));

        assertEquals("There are 3 messages", 3, childElements.size());

        Iterator<WebElement> iterator = childElements.iterator();
        WebElement message1 = iterator.next();
        WebElement message2 = iterator.next();
        WebElement message3 = iterator.next();

        assertEquals("First uploaded file has been received", "field: multipleSelection, name: " + file1.getName() + ", size: " + file1.length(), message1.getText());
        assertEquals("Second uploaded file has been received", "field: multipleSelection, name: " + file2.getName() + ", size: " + file2.length(), message2.getText());
        assertEquals("Third uploaded file has been received", "field: multipleSelection, name: " + file3.getName() + ", size: " + file3.length(), message3.getText());
    }

    private static File generateTempFile(String name, String ext, int size) throws IOException {
        Path path = Files.createTempFile(name, "." + ext);
        path.toFile().deleteOnExit();
        byte[] content = new byte[size];
        Files.write(path, content, APPEND);
        return path.toFile();
    }
}
