/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet30.ajax_new;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.behavior.AjaxBehavior;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class Issue1817IT extends BaseITNG {

    /**
     * @see AjaxBehavior
     * @see com.sun.faces.facelets.component.UIRepeat
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1821
     */
    @Test
    public void testAjaxUIRepeat() throws Exception {
        WebPage page = getPage("issue1817.xhtml");
        final List<WebElement> elements = page.findElements(By.tagName("a"));
        List<String[]> ids = elements.stream().map(element -> new String[]{element.getAttribute("id"), element.getText()})
                .collect(Collectors.toList());
        for (String[] id : ids) {
            WebElement anchor = page.findElement(By.id(id[0]));
            anchor.click();
            String expectedText = "Triggered item: " + id[1];
            WebElement body = page.findElement(By.tagName("body"));
            page.waitForCondition(ExpectedConditions.textToBePresentInElement(body, expectedText), Duration.ofMillis(3000));
            assertTrue(page.getPageSource().contains(expectedText));
        }
    }
}
