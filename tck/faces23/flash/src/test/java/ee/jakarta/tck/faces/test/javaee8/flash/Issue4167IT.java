/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2023 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.javaee8.flash;

import static org.junit.Assert.assertTrue;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.context.Flash;

@RunWith(Arquillian.class)
public class Issue4167IT extends ITBase {

    /**
     * @see Flash#keep(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4171
     */
    @Test
    public void test() throws Exception {
        HtmlPage page = getPage("issue4167.xhtml");

        assertTrue(page.getHtmlElementById("result1").asNormalizedText().isEmpty());
        assertTrue(page.getHtmlElementById("result2").asNormalizedText().isEmpty());
        assertTrue(page.getHtmlElementById("result3").asNormalizedText().isEmpty());

        HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("form:button");
        page = button.click();

        assertTrue(page.getHtmlElementById("result1").asNormalizedText().equals("issue4167"));
        assertTrue(page.getHtmlElementById("result2").asNormalizedText().equals("issue4167"));
        assertTrue(page.getHtmlElementById("result3").asNormalizedText().equals("issue4167"));
    }

}
