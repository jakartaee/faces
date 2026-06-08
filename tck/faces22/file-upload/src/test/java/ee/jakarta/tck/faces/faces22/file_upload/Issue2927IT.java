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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A required h:inputFile that is submitted with no file chosen fails validation with the standard
 * "Value is required." message.
 */
class Issue2927IT extends BaseITNG {

    /**
     * Submitting the form without choosing a file for the required h:inputFile produces the standard
     * required-validation message for that component.
     *
     * @see jakarta.faces.component.html.HtmlInputFile
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2927
     */
    @Test
    void testInputFileRequired() {
        WebPage page = getPage("issue2927.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form:button")).click());
        assertTrue(page.containsText("form:file: Validation Error: Value is required."),
                "Required h:inputFile must report 'Value is required.' when no file is chosen");
    }
}
