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

package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3147IT extends BaseITNG {

    /**
     * An h:selectOneMenu must HTML-escape the labels of its select items so that markup supplied as a
     * label value cannot inject script into the rendered page. A label of "<script>alert('mytest');</script>"
     * must therefore appear escaped as "&lt;script&gt;alert('mytest');&lt;/script&gt;" in the response.
     *
     * @see jakarta.faces.component.html.HtmlSelectOneMenu
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3147
     */
    @Test
    void testSelectOneMenuXSS() {
        WebPage page = getPage("issue3147.xhtml");
        assertTrue(page.containsSource("&lt;script&gt;alert('mytest');&lt;/script&gt;"));
    }
}
