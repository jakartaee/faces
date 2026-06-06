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

package ee.jakarta.tck.faces.faces23.namespacedparams;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.NamingContainer;
import jakarta.faces.context.ExternalContext;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Verifies that ajax and non-ajax submits (including {@code f:param}) resolve
 * request parameters correctly when the view root is a {@link NamingContainer}
 * that prefixes every client id, with {@code prependId=false} forms.
 *
 * @see ExternalContext#getRequestParameterMap()
 * @see NamingContainer
 * @see https://github.com/eclipse-ee4j/mojarra/issues/3035
 */
class Issue3035IT extends BaseITNG {

    /**
     * @see ExternalContext#getRequestParameterMap()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3035
     */
    @Test
    void testAjax() throws Exception {
        WebPage page = getPage("issue3035.xhtml");

        WebElement input = findBySuffix(page, "ajaxInput");
        // The input is pre-populated and bound to an f:ajax change listener that re-renders @form.
        // Replace its value within a single sendKeys so only one change (hence one ajax) fires, on TAB;
        // a separate clear() would fire its own change, re-render the form, and leave input stale.
        page.guardAjax(() -> input.sendKeys(Keys.chord(Keys.CONTROL, "a"), "MyText", Keys.TAB));

        assertTrue(findBySuffix(page, "ajaxOutput").getText().contains("MyText"));
    }

    /**
     * @see ExternalContext#getRequestParameterMap()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3035
     */
    @Test
    void testAjaxWithParams() throws Exception {
        WebPage page = getPage("issue3035.xhtml");

        WebElement input = findBySuffix(page, "ajaxInputParams");
        input.clear();
        input.sendKeys("MyText");

        WebElement button = findBySuffix(page, "ajaxSubmitParams");
        page.guardAjax(button::click);

        WebElement output = findBySuffix(page, "ajaxOutputParams");
        assertTrue(output.getText().contains("MyText value"));
    }

    /**
     * @see ExternalContext#getRequestParameterMap()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3035
     */
    @Test
    void testNonAjax() throws Exception {
        WebPage page = getPage("issue3035.xhtml");

        WebElement input = findBySuffix(page, "nonAjaxInput");
        input.clear();
        input.sendKeys("MyNonAjaxText");

        WebElement button = findBySuffix(page, "nonAjaxSubmit");
        page.guardHttp(button::click);

        WebElement output = findBySuffix(page, "nonAjaxOutput");
        assertTrue(output.getText().contains("MyNonAjaxText"));
    }

    /**
     * @see ExternalContext#getRequestParameterMap()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3035
     */
    @Test
    void testNonAjaxWithParams() throws Exception {
        WebPage page = getPage("issue3035.xhtml");

        WebElement input = findBySuffix(page, "nonAjaxInputParams");
        input.clear();
        input.sendKeys("MyNonAjaxText");

        WebElement button = findBySuffix(page, "nonAjaxSubmitParams");
        page.guardHttp(button::click);

        WebElement output = findBySuffix(page, "nonAjaxOutputParams");
        assertTrue(output.getText().contains("MyNonAjaxText value"));
    }

    /**
     * The view root prefixes every client id, so element ids are
     * {@code MyNamingContainer<viewId>:<fieldId>}. Match on the trailing
     * {@code :<fieldId>} segment rather than hard-coding the generated view id.
     */
    private static WebElement findBySuffix(WebPage page, String fieldId) {
        return page.findElement(By.xpath("//*[substring(@id, string-length(@id) - " + fieldId.length() + ") = ':" + fieldId + "']"));
    }
}
