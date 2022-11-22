/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet30.ajax_new;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.context.PartialResponseWriter;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Spec1296IT extends BaseITNG {

    /**
     * @see PartialResponseWriter
     * @see https://github.com/jakartaee/faces/issues/1296
     */
    @Test
    public void testPartialResponseWriterOutsideFacesServlet() throws Exception {
        WebPage page = getPage("BeforeFilter");
        String pageXml = page.getResponseBody();
        assertTrue(pageXml.matches("(?s).*<\\?xml\\s+version=\\'1\\.0\\'\\s+encoding=\\'UTF-8\\'\\?>\\s*<partial-response>\\s*<changes>\\s*<update\\s+id=\\\"foo\\\">\\s*<\\!\\[CDATA\\[\\s*\\]]>\\s*</update>\\s*</changes>\\s*</partial-response>.*"));
    }
}
