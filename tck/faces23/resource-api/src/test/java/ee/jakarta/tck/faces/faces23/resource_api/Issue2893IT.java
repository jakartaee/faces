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

package ee.jakarta.tck.faces.faces23.resource_api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue2893IT extends BaseITNG {

    /**
     * A resource loaded via a component's {@link jakarta.faces.application.ResourceDependency}
     * annotation must still be present in the rendered head after a postback re-render.
     *
     * @see jakarta.faces.application.ResourceDependency
     * @see https://github.com/javaserverfaces/mojarra/issues/2893
     */
    @Test
    void testComponentResourceDependency() throws Exception {
        WebPage page = getPage("faces/issue2893.xhtml");
        assertTrue(page.containsSource("foo.js"), "@ResourceDependency resource is present on initial render");

        page.guardHttp(() -> page.findElement(By.id("form:link")).click());
        assertTrue(page.containsSource("foo.js"), "@ResourceDependency resource is still present after postback");
    }
}
