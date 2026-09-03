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

package ee.jakarta.tck.faces.faces22.composite_component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue1810IT extends BaseITNG {

    /**
     * A RuntimeException thrown by the action method bound to a composite component's declared action attribute must reach the ExceptionHandler rather than
     * being swallowed by the retargeting, so the postback yields a 500.
     *
     * @see jakarta.faces.view.facelets.FaceletContext
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1810
     */
    @Test
    void exceptionFromRetargetedActionPropagates() {
        WebPage page = getPage("issue1810.xhtml");

        page.findElement(By.id("action:form1:submit")).click();

        assertEquals(
            500, page.getResponseStatus(),
            "An exception from the retargeted action method must not be swallowed."
        );
    }

}
