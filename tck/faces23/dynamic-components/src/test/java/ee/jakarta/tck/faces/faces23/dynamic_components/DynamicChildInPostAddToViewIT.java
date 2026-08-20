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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A child added from a <code>postAddToView</code> listener is reproduced by the view build of every request, so it must
 * appear exactly once per request and take part in the lifecycle like a child declared in the view. This holds whether
 * the listener is attached to the view root or to the container it adds to.
 */
public class DynamicChildInPostAddToViewIT extends BaseITNG {

    private static final By CHILD = By.className("dynamic-child");
    private static final String SUBMITTED_VALUE = "submitted into a dynamically added input";

    /**
     * A listener on the view root adds one child per request, and the child decodes its submitted value.
     *
     * @see jakarta.faces.event.PostAddToViewEvent
     */
    @Test
    void listenerOnViewRoot() {
        assertSingleChildDecodesItsValue("dynamicChildInViewRoot.xhtml");
    }

    /**
     * A listener on the container adds one child per request, and the child decodes its submitted value.
     *
     * @see jakarta.faces.event.PostAddToViewEvent
     */
    @Test
    void listenerOnContainer() {
        assertSingleChildDecodesItsValue("dynamicChildInContainer.xhtml");
    }

    private void assertSingleChildDecodesItsValue(String viewId) {
        WebPage page = getPage(viewId);
        assertEquals(1, page.findElements(CHILD).size(), "one added child on initial render");

        page.findElement(CHILD).sendKeys(SUBMITTED_VALUE);
        page.guardHttp(page.findElement(By.id("form:submit"))::click);

        assertEquals(1, page.findElements(CHILD).size(), "one added child after postback");
        assertEquals(SUBMITTED_VALUE, page.findElement(By.id("echo")).getText(), "value decoded from the added child");
    }
}
