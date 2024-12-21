/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee8.cdi_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.ExtendedWebDriver;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.annotation.FlowMap;
import jakarta.inject.Inject;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Tests the availability of the flow map via injection
 *
 */

public class Spec1386IT extends BaseITNG {

    /**
     * @see Inject
     * @see FlowMap
     * @see https://github.com/jakartaee/faces/issues/1386
     */
    @Test
    public void testInjectFlowMap() throws Exception {
        // Start on initial (non-flow) view
        WebPage page = getPage("injectFlowMap.xhtml");

        // Enter main flow
        ExtendedWebDriver webDriver = getWebDriver();
        page.guardAjax(webDriver.findElement(By.id("form:enter"))::click);

        // Put value in flow scope map
        page.guardAjax(webDriver.findElement(By.id("form:init"))::click);

        // Navigate to next page in flow
        page.guardAjax(webDriver.findElement(By.id("form:next"))::click);


        // Value should be available from flow map now
        page.waitForCondition(webDriver1 -> page.isInPage("foo:bar"));


        // Enter nested flow
        page.guardAjax(webDriver.findElement(By.id("form:nested"))::click);

        // Put (different) value in flow map using same key
        page.guardAjax(webDriver.findElement(By.id("form:init"))::click);

        // Navigate to next page in nested flow
        page.guardAjax(webDriver.findElement(By.id("form:next"))::click);
        // Different value should be available from flow map now
        page.waitForCondition(webDriver1 -> page.isInPage("foo:barx"));


        // Exit nested flow
        page.guardAjax(webDriver.findElement(By.id("form:exit"))::click);

        // Original value should be available from flow map again
        page.waitForCondition(webDriver1 -> page.isInPage("foo:bar"));

    }

}
