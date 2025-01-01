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

package ee.jakarta.tck.faces.test.servlet50.doctype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.UIViewRoot;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1568IT extends BaseITNG {

    /**
     * @see UIViewRoot#getDoctype()
     * @see https://github.com/jakartaee/faces/issues/1568
     */
    @Test
    void html5() throws Exception {
        WebPage page = getPage("spec1568IT-HTML5.xhtml");

        assertEquals("<!DOCTYPE html>", getDoctype(page), "Page is using HTML5 doctype");
    }

    /**
     * @see UIViewRoot#getDoctype()
     * @see https://github.com/jakartaee/faces/issues/1568
     */
    @Test
    void html4Public() throws Exception {
        WebPage page = getPage("spec1568IT-HTML4-public.xhtml");

        assertEquals("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">",
                getDoctype(page), "Page is using XHTML4 transitional public doctype");
    }

    /**
     * @see UIViewRoot#getDoctype()
     * @see https://github.com/jakartaee/faces/issues/1568
     */
    @Test
    void html4System() throws Exception {
        WebPage page = getPage("spec1568IT-HTML4-system.xhtml");

        assertEquals("<!DOCTYPE html SYSTEM \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">", getDoctype(page),
                "Page is using XHTML4 strict system doctype");
    }

    private static String getDoctype(WebPage page) {
        return (String) page.getWebDriver().getJSExecutor().executeScript("return new XMLSerializer().serializeToString(document.doctype);");
    }

}
