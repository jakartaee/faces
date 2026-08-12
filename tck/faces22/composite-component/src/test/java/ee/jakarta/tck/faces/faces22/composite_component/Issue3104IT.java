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

package ee.jakarta.tck.faces.faces22.composite_component;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3104IT extends BaseITNG {

    /**
     * A listener MethodExpression forwarded through two levels of composite component into f:ajax must not
     * fail silently: when the listener throws, the client-side error handler registered with
     * faces.ajax.addOnError is invoked and receives the error name.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3104
     */
    @Test
    void ajaxListenerFailureReachesTheErrorHandler() {
        WebPage page = getPage("issue3104.xhtml");

        page.findElement(By.id("form:nesting0:nesting1:inputText")).sendKeys("12345");
        page.executeScript("document.getElementById('form:nesting0:nesting1:inputText').blur();");
        page.waitForCondition(driver -> !driver.findElement(By.id("errorName")).getText().isEmpty());

        assertFalse(page.findElement(By.id("errorName")).getText().isEmpty(),
                "The failing ajax listener must surface an error to the registered onerror handler.");
    }
}
