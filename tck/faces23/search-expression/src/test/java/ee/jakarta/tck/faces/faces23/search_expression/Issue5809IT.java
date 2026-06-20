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

package ee.jakarta.tck.faces.faces23.search_expression;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue5809IT extends BaseITNG {

    /**
     * Resolving an f:ajax render expression such as {@code @root:@id(...)} must not
     * traverse unrendered components. An unrendered subtree has no client-side
     * markup and can never be a useful ajax target, so the resolution visit must
     * skip it - mirroring the partial render visit, which already does so.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5809
     */
    @Test
    void test() throws Exception {
        WebPage page = getPage("issue5809.xhtml");

        // The rendered target must still resolve to its client id.
        WebElement button = page.findElement(By.id("form:button"));
        assertTrue(page.getBehaviorScript(button).contains("form:target"));

        // The unrendered h:dataTable must not have been traversed, so its model
        // getter must not have been invoked during the render expression resolution.
        WebElement listTouched = page.findElement(By.id("form:listTouched"));
        assertEquals("false", listTouched.getText());
    }
}
