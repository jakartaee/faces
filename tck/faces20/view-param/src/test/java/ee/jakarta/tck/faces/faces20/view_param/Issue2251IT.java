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
package ee.jakarta.tck.faces.faces20.view_param;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2251IT extends BaseITNG {

    /**
     * An EL expression typed into an f:viewParam-bound input must be treated as a literal
     * string and never re-evaluated as EL, both when the input is round-tripped through an
     * implicit-navigation redirect.
     *
     * @see jakarta.faces.component.UIViewParameter
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2251
     */
    @Test
    void noELEvaluationOnImplicitNavigation() {
        WebPage page = getPage("issue2251.xhtml");
        page.findElement(By.id("form:input")).sendKeys("#{4+5}");
        page.guardHttp(page.findElement(By.id("form:implicitNavigationButton"))::click);
        assertEquals("#{4+5}", page.findElement(By.id("form:value")).getText(), "input value must not be evaluated as EL");
    }

    /**
     * An EL expression typed into an f:viewParam-bound input must not be re-evaluated, while
     * the server-configured EL view-param in the navigation rule is evaluated normally.
     *
     * @see jakarta.faces.component.UIViewParameter
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2251
     */
    @Test
    void noELEvaluationOnExplicitNavigation() {
        WebPage page = getPage("issue2251.xhtml");
        page.findElement(By.id("form:input")).sendKeys("#{12+1}");
        page.guardHttp(page.findElement(By.id("form:explicitNavigationButton"))::click);
        assertEquals("#{12+1}", page.findElement(By.id("form:value")).getText(), "input value must not be evaluated as EL");
        assertEquals("6", page.findElement(By.id("form:elViewParam")).getText(), "faces-config view-param EL must be evaluated");
    }
}
