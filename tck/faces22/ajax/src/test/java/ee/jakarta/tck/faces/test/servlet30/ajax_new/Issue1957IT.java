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
import jakarta.faces.component.behavior.ClientBehaviorHolder;
import jakarta.faces.component.html.HtmlPanelGroup;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class Issue1957IT extends BaseITNG {

    /**
     * @see ClientBehaviorHolder
     * @see HtmlPanelGroup
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1961
     */
    @Test
    public void testPanelGroupAjaxBehavior() throws Exception {
        WebPage page = getPage("issue1957.xhtml");
        WebElement span = page.findElement(By.id("form:group"));
        page.waitForCondition(ExpectedConditions.elementToBeClickable(span), Duration.ofMillis(3000));
        span.click();
        page.waitForCurrentRequestEnd();
        assertTrue(page.getPageSource().contains("form:group Event: begin"));
        assertTrue(page.getPageSource().contains("form:group Event: complete"));
        assertTrue(page.getPageSource().contains("form:group Event: success"));
    }
}
