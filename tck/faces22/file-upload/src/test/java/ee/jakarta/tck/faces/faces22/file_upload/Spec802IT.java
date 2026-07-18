/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces22.file_upload;

import static ee.jakarta.tck.faces.faces22.file_upload.Spec802Validator.MARKER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.component.html.HtmlInputFile;

/**
 * The bytes of a file uploaded through {@code h:inputFile} must reach the model as a {@code Part} and survive a
 * validator rejecting the file.
 */
class Spec802IT extends BaseITNG {

    private static final String VIEW = "spec802.xhtml";

    /**
     * The bytes of the uploaded file reach the bean and can be rendered back, and a sibling input of the same
     * multipart form is submitted along with it.
     *
     * @see HtmlInputFile
     * @see https://github.com/jakartaee/faces/issues/802
     */
    @Test
    void uploadedBytesReachBeanAndRenderBack() throws IOException {
        WebPage page = getPage(VIEW);
        String content = MARKER + " reaches the model";
        String text = String.valueOf(System.nanoTime());

        page.findElement(By.id("form:text")).sendKeys(text);
        submit(page, content);

        assertEquals(content, page.findElement(By.id("form:fileText")).getText(),
                "The bytes of the uploaded file must reach the bean and be rendered back.");
        assertEquals(text, page.findElement(By.id("form:textOutput")).getText(),
                "The sibling input of the multipart form must have been submitted along with the file.");
    }

    /**
     * A validator rejecting the uploaded file shows its validation message and suppresses the model update of both the
     * file itself and the sibling inputs of the same form.
     *
     * @see HtmlInputFile
     * @see jakarta.faces.validator.Validator
     * @see https://github.com/jakartaee/faces/issues/802
     */
    @Test
    void validatorRejectionSuppressesSiblingModelUpdate() throws IOException {
        WebPage page = getPage(VIEW);
        String text = String.valueOf(System.nanoTime());

        page.findElement(By.id("form:text")).sendKeys(text);
        submit(page, "This file lacks the marker");

        assertTrue(page.containsText(Spec802Validator.INVALID_FILE_MESSAGE),
                "The validation message of the rejected file must be shown.");
        assertEquals("", page.findElement(By.id("form:fileText")).getText(),
                "A rejected file must not reach the model.");
        assertEquals("", page.findElement(By.id("form:textOutput")).getText(),
                "A failed validation must suppress the model update of the sibling inputs.");
    }

    /**
     * Uploading another file on the same view yields that file's bytes, not the bytes of the previous upload.
     *
     * @see HtmlInputFile
     * @see https://github.com/jakartaee/faces/issues/802
     */
    @Test
    void repeatedUploadsInSameViewYieldLatestBytes() throws IOException {
        WebPage page = getPage(VIEW);

        for (int i = 1; i <= 3; i++) {
            String content = MARKER + " upload " + i;
            submit(page, content);
            assertEquals(content, page.findElement(By.id("form:fileText")).getText(),
                    "Upload " + i + " on the same view must yield its own bytes.");
        }
    }

    /**
     * Selects a temporary file with the given content in the file input and submits the form.
     */
    private void submit(WebPage page, String content) throws IOException {
        Path file = Files.createTempFile("spec802", ".txt");
        Files.writeString(file, content);
        page.findElement(By.id("form:file")).sendKeys(file.toAbsolutePath().toString());
        page.guardHttp(page.findElement(By.id("form:button"))::click);
    }
}
