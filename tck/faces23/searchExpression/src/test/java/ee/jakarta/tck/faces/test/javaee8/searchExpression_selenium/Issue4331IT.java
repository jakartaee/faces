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

package ee.jakarta.tck.faces.test.javaee8.searchExpression_selenium;


import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.application.Application;
import jakarta.faces.component.search.SearchKeywordResolver;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class Issue4331IT extends BaseITNG {


    /**
     * @see Application#addSearchKeywordResolver(SearchKeywordResolver)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4331
     */
    @Test
    public void test() throws Exception {
        testCustomSearchKeywordResolverAddedViaFacesConfig();
    }

    public void testCustomSearchKeywordResolverAddedViaFacesConfig() throws Exception {
        WebPage page = getPage("issue4331.xhtml");

        WebElement input = getWebDriver().findElement(By.id("input"));
        Assert.assertFalse(input.getAttribute("onchange").contains("@custom"));
        Assert.assertTrue(input.getAttribute("onchange").contains("input"));
    }


}
