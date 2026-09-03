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

package ee.jakarta.tck.faces.faces20.jstl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2528IT extends BaseITNG {

    /**
     * A c:set whose value is the empty string is a valid assignment, so the view compiles and the variable resolves to the empty string rather than the tag
     * failing at build time.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2528
     */
    @Test
    void setWithAnEmptyValueCompilesAndResolves() {
        WebPage page = getPage("issue2528.xhtml");

        assertEquals(200, page.getResponseStatus(), "The view must render.");
        assertEquals(
            "[]", page.findElement(By.id("rendered")).getText(),
            "The variable set to an empty value must resolve to the empty string."
        );
    }

}
