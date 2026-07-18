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
package ee.jakarta.tck.faces.faces20.renderkit.outputformat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.html.HtmlOutputFormat;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2355IT extends BaseITNG {

    /**
     * Verifies that an {@code h:outputFormat} whose value is absent, a null literal, or an
     * expression evaluating to null renders as empty markup instead of throwing.
     *
     * @see HtmlOutputFormat
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2355
     */
    @Test
    void testNullValueForOutputFormat() {
        WebPage page = getPage("outputformat/issue2355.xhtml");

        assertEquals(200, page.getResponseStatus(), "Page with null-valued h:outputFormat must render");
        assertEquals("", page.findElement(By.id("form:noArgs")).getText(), "outputFormat with no arguments");
        assertEquals("", page.findElement(By.id("form:nullLiteral")).getText(), "outputFormat with null literal value");
        assertEquals("", page.findElement(By.id("form:nullBean")).getText(), "outputFormat with expression evaluating to null");
    }
}
