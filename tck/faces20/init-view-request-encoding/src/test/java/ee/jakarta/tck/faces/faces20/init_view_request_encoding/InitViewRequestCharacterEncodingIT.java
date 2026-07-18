/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces20.init_view_request_encoding;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class InitViewRequestCharacterEncodingIT extends BaseITNG {

    /**
     * A request character encoding set before the view is initialized (here, via a before-phase
     * listener) must be reported back by ExternalContext.getRequestCharacterEncoding at render time.
     *
     * @see jakarta.faces.context.ExternalContext#setRequestCharacterEncoding(String)
     * @see jakarta.faces.context.ExternalContext#getRequestCharacterEncoding()
     */
    @Test
    void requestEncodingSetBeforeInitViewIsHonored() {
        WebPage page = getPage("initViewRequestCharacterEncoding.xhtml");
        assertEquals("ISO-8859-1", page.findElement(By.id("form:encoding")).getText(), "request character encoding");
    }
}
