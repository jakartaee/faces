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
package ee.jakarta.tck.faces.faces20.appconfigresources.startupbehavior;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class StartupBehaviorIT extends BaseITNG {

    @Test
    void applicationConfigurationfilesTest1() {
        WebPage page = getPage("appconfig_01.xhtml");
        assertEquals("YES", findByIdSuffix(page, "answer").getText());
        assertEquals("Ready!", findByIdSuffix(page, "status").getText());
        assertEquals("red", findByIdSuffix(page, "color").getText());
    }

    @Test
    void applicationConfigurationfilesTest2() {
        WebPage page = getPage("appconfig_02.xhtml");
        assertEquals("NO", findByIdSuffix(page, "question").getText());
        assertEquals("blue", findByIdSuffix(page, "ball").getText());
    }

    /**
     * A component, converter, validator and renderer declared in an application configuration
     * resource which is only reachable because it is listed in {@code jakarta.faces.CONFIG_FILES}
     * are registered in the application and in the render kit, whereas the same kind of declaration
     * in a resource which is not listed is ignored.
     *
     * @see jakarta.faces.webapp.FacesServlet#CONFIG_FILES_ATTR
     * @see https://jakarta.ee/specifications/faces/5.0/apidocs/jakarta.faces/jakarta/faces/webapp/facesservlet#CONFIG_FILES_ATTR
     */
    @Test
    void applicationConfigurationfilesTest3() {
        WebPage page = getPage("appconfig_03.xhtml");
        assertEquals("StartupBehaviorConfigComponent", findByIdSuffix(page, "component").getText());
        assertEquals("StartupBehaviorConfigConverter", findByIdSuffix(page, "converter").getText());
        assertEquals("StartupBehaviorConfigValidator", findByIdSuffix(page, "validator").getText());
        assertEquals("StartupBehaviorConfigRenderer", findByIdSuffix(page, "renderer").getText());
        assertEquals("NOT-REGISTERED", findByIdSuffix(page, "unlisted").getText());
    }

    private static org.openqa.selenium.WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
