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
package ee.jakarta.tck.faces.faces23.cdi_tccl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3345IT extends BaseITNG {

    /**
     * A servlet filter replaces the thread context class loader with a child class loader for the
     * duration of the request; CDI resolution of the {@code @Named} bean must still succeed, so the
     * page renders the bean's value rather than the filter's FAILURE fallback.
     *
     * @see jakarta.faces.context.FacesContext
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3345
     */
    @Test
    void testCDIWithTCCL() {
        WebPage page = getPage("index.xhtml");
        assertEquals("SUCCESS", page.findElement(By.id("result")).getText());
    }
}
