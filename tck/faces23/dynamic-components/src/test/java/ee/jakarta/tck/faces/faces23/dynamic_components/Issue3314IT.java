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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A component dynamically added by an ajax action to a bound panel group whose first child is a
 * transient plain-HTML node must keep its position after a subsequent full postback reload: the
 * transient first child still renders before the dynamically added component.
 */
public class Issue3314IT extends BaseITNG {

    private static final String TRANSIENT_CHILD = "transient first child";
    private static final String ADDED_CHILD = "dynamically added; now perform a reload";

    /**
     * After ajax add and a full reload, the transient first child precedes the added component.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3314
     */
    @Test
    void testMoveComponent() {
        WebPage page = getPage("issue3314.xhtml");
        page.guardAjax(page.findElement(By.id("form:add"))::click);
        page.guardHttp(page.findElement(By.id("form:reload"))::click);

        String source = page.getSource();
        int transientChild = source.indexOf(TRANSIENT_CHILD);
        int addedChild = source.indexOf(ADDED_CHILD);
        assertTrue(transientChild >= 0 && addedChild >= 0 && transientChild < addedChild,
                "Transient first child must render before the dynamically added component, but got transient="
                        + transientChild + " added=" + addedChild);
    }
}
