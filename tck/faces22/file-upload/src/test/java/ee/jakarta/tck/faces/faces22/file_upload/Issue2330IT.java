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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import jakarta.faces.component.html.HtmlInputFile;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2330IT extends BaseITNG {

    /**
     * When the container fails to read the multipart parts during decoding of an {@code h:inputFile}
     * (here a servlet {@code Filter} wraps the request so {@code HttpServletRequest.getParts()} throws an
     * {@code IOException}), the file renderer must surface the failure as a faces message rather than
     * propagating the exception, so it is rendered via {@code h:messages}.
     *
     * @see HtmlInputFile
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2330
     */
    @Test
    void testFileException() throws Exception {
        WebPage page = getPage("issue2330.xhtml");

        Path file = Files.createTempFile("issue2330", ".txt");
        Files.writeString(file, "JSR-344");

        page.findElement(By.id("file")).sendKeys(file.toAbsolutePath().toString());

        page.guardHttp(page.findElement(By.id("button"))::click);

        assertTrue(page.containsText("Negative test, intentional failure"),
            "File renderer must surface the multipart read failure as a faces message");
    }
}
