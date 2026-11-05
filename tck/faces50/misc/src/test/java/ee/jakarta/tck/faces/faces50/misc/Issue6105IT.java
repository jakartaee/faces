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
package ee.jakarta.tck.faces.faces50.misc;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue6105IT extends BaseITNG {

    private static final List<String> ITEMS = List.of("one", "two", "three");

    @FindBy(id = "form:composite:menu")
    private WebElement menu;

    @FindBy(id = "form:ajaxSubmit")
    private WebElement ajaxSubmit;

    @FindBy(id = "form:nonAjaxSubmit")
    private WebElement nonAjaxSubmit;

    @FindBy(id = "form:selected")
    private WebElement selected;

    /**
     * When the items of a composite component which relocates its children via cc:insertChildren are created by a c:forEach, then an ajax postback must still
     * render all of them, and a value selected afterwards must still be submitted.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6105
     */
    @Test
    void testAjaxPostbackKeepsRelocatedForEachChildren() {
        WebPage page = getPage("issue6105.xhtml");
        assertEquals(ITEMS, getOptions());

        page.guardAjax(ajaxSubmit::click);
        assertEquals(ITEMS, getOptions());

        new Select(menu).selectByValue("two");
        page.guardAjax(ajaxSubmit::click);
        assertEquals(ITEMS, getOptions());
        assertEquals("two", selected.getText());
    }

    /**
     * When the items of a composite component which relocates its children via cc:insertChildren are created by a c:forEach, then a non-ajax postback must
     * still render all of them, and a value selected afterwards must still be submitted.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6105
     */
    @Test
    void testNonAjaxPostbackKeepsRelocatedForEachChildren() {
        WebPage page = getPage("issue6105.xhtml");
        assertEquals(ITEMS, getOptions());

        page.guardHttp(nonAjaxSubmit::click);
        assertEquals(ITEMS, getOptions());

        new Select(menu).selectByValue("three");
        page.guardHttp(nonAjaxSubmit::click);
        assertEquals(ITEMS, getOptions());
        assertEquals("three", selected.getText());
    }

    private List<String> getOptions() {
        return new Select(menu).getOptions().stream().map(option -> option.getAttribute("value")).collect(toList());
    }

}
