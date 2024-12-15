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

package ee.jakarta.tck.faces.test.servlet30.ajax_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.html.HtmlSelectManyCheckbox;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class Issue2408IT extends BaseITNG {

    /**
     * This test verifies correct function of SelectManyCheckbox in a Composite
     * Component over Ajax.
     *
     * @see AjaxBehavior
     * @see HtmlSelectManyCheckbox
     * @see UIComponent#getCurrentCompositeComponent(jakarta.faces.context.FacesContext)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2412
     */
    @Test
    public void testSelectManyCheckboxInComposite() throws Exception {
        WebPage page = getPage("selectManyCheckboxInComposite.xhtml");

        // This will ensure JavaScript finishes before evaluating the page.
        assertTrue(page.getPageSource().contains("Status: Pending"));

        page.guardAjax(getCheckBoxes(page).get(0)::click);
        assertTrue(page.isInPage("Status: mcheck-1"));

        page.guardAjax(getCheckBoxes(page).get(1)::click);
        assertTrue(page.isInPage("Status: mcheck-1 mcheck-2"));

        page.guardAjax(getCheckBoxes(page).get(2)::click);
        assertTrue(page.isInPage("Status: mcheck-1 mcheck-2 mcheck-3"));
    }

    /**
     * This test verifies correct function of SelectManyCheckbox in a Composite
     * Component over Ajax. The components in the page have ids.
     */
    @Test
    public void testSelectManyCheckboxIdsInComposite() throws Exception {
        WebPage page = getPage("selectManyCheckboxIdsInComposite.xhtml");

        // This will ensure JavaScript finishes before evaluating the page.
        assertTrue(page.getPageSource().contains("Status: Pending"));

        WebElement cbox1 = page.findElement(By.id("form:compId:cbox:0"));
        page.guardAjax(cbox1::click);
        assertTrue(page.isInPage("Status: mcheck-1"));

        WebElement cbox2 = page.findElement(By.id("form:compId:cbox:1"));
        page.guardAjax(cbox2::click);
        assertTrue(page.isInPage("Status: mcheck-1 mcheck-2"));

        WebElement cbox3 = page.findElement(By.id("form:compId:cbox:2"));
        page.guardAjax(cbox3::click);
        assertTrue(page.isInPage("Status: mcheck-1 mcheck-2 mcheck-3"));
    }

    /**
     * This test verifies correct function of SelectManyCheckbox Component over Ajax.
     */
    @Test
    public void testSelectManyCheckboxNoComposite() throws Exception {
        WebPage page = getPage("selectManyCheckboxNoComposite.xhtml");

        // This will ensure JavaScript finishes before evaluating the page.
        assertTrue(page.getPageSource().contains("Status: Pending"));

        page.guardAjax(getCheckBoxes(page).get(0)::click);
        assertTrue(page.isInPage("Status: mcheck-1"));

        page.guardAjax(getCheckBoxes(page).get(1)::click);
        assertTrue(page.isInPage("Status: mcheck-1 mcheck-2"));

        page.guardAjax(getCheckBoxes(page).get(2)::click);
        assertTrue(page.isInPage("Status: mcheck-1 mcheck-2 mcheck-3"));
    }

    /**
     * This test verifies correct function of SelectOneRadio in a Composite
     * Component over Ajax.
     */
    @Test
    public void testSelectOneRadioInComposite() throws Exception {
        WebPage page = getPage("selectOneRadioInComposite.xhtml");

        // This will ensure JavaScript finishes before evaluating the page.
        assertTrue(page.isInPage("Status: Pending"));

        page.guardAjax(getRadios(page).get(0)::click);
        assertTrue(page.isInPage("Status: radio-1"));

        page.guardAjax(getRadios(page).get(1)::click);
        assertTrue(page.isInPage("Status: radio-2"));

        page.guardAjax(getRadios(page).get(2)::click);
        assertTrue(page.isInPage("Status: radio-3"));
    }

    /**
     * This test verifies correct function of SelectOneRadio in a Composite
     * Component over Ajax. The components in the page have ids.
     */
    @Test
    public void testSelectOneRadioIdsInComposite() throws Exception {
        WebPage page = getPage("selectOneRadioIdsInComposite.xhtml");

        // This will ensure JavaScript finishes before evaluating the page.
        assertTrue(page.isInPage("Status: Pending"));

        WebElement radio1 = page.findElement(By.id("form:compId:radio:0"));
        page.guardAjax(radio1::click);
        assertTrue(page.isInPage("Status: radio-1"));

        WebElement radio2 = page.findElement(By.id("form:compId:radio:1"));
        page.guardAjax(radio2::click);
        assertTrue(page.isInPage("Status: radio-2"));

        WebElement radio3 = page.findElement(By.id("form:compId:radio:2"));
        page.guardAjax(radio3::click);
        assertTrue(page.isInPage("Status: radio-3"));
    }

    /**
     * This test verifies correct function of SelectOneRadio Component over Ajax.
     */
    @Test
    public void testSelectOneRadioNoComposite() throws Exception {
        WebPage page = getPage("selectOneRadioNoComposite.xhtml");

        // This will ensure JavaScript finishes before evaluating the page.
        assertTrue(page.isInPage("Status: Pending"));

        page.guardAjax(getRadios(page).get(0)::click);
        assertTrue(page.isInPage("Status: radio-1"));

        page.guardAjax(getRadios(page).get(1)::click);
        assertTrue(page.isInPage("Status: radio-2"));

        page.guardAjax(getRadios(page).get(2)::click);
        assertTrue(page.isInPage("Status: radio-3"));
    }

    private List<WebElement> getCheckBoxes(WebPage page) {
        return page.findElements(By.cssSelector("input[type='checkbox']"));
    }

    private List<WebElement> getRadios(WebPage page) {
        return page.findElements(By.cssSelector("input[type='radio']"));
    }

}
