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

package ee.jakarta.tck.faces.faces22.resource_dependency;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A resource declared via {@link jakarta.faces.application.ResourceDependency} on a custom renderer remains rendered
 * after a postback when the hosting component is bound to a view-scoped bean.
 */
class Issue2744IT extends BaseITNG {

    /**
     * The @ResourceDependency JS declared on the custom renderer is present on initial render and still present after
     * a non-ajax commandButton postback.
     *
     * @see jakarta.faces.application.ResourceDependency
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2744
     */
    @Test
    void testResourceDependency() {
        WebPage page = getPage("issue2744.xhtml");
        assertTrue(page.getSource().contains("resourceDependency.js"), "Resource present on initial render");

        WebElement button = page.findElement(By.id("form:submit"));
        page.guardHttp(button::click);
        assertTrue(page.getSource().contains("resourceDependency.js"), "Resource still present after postback");
    }
}
