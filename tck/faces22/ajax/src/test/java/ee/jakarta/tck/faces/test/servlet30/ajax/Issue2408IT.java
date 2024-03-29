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

package ee.jakarta.tck.faces.test.servlet30.ajax; 

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.html.HtmlSelectManyCheckbox;

public class Issue2408IT extends ITBase {

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
        HtmlPage page = getPage("selectManyCheckboxInComposite.xhtml");
        
        // This will ensure JavaScript finishes before evaluating the page.
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: Pending"));

        page = getCheckBoxes(page).get(0).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: mcheck-1"));

        page = getCheckBoxes(page).get(1).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: mcheck-1 mcheck-2"));

        page = getCheckBoxes(page).get(2).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: mcheck-1 mcheck-2 mcheck-3"));
    }

    /**
     * This test verifies correct function of SelectManyCheckbox in a Composite
     * Component over Ajax. The components in the page have ids.
     */
    @Test
    public void testSelectManyCheckboxIdsInComposite() throws Exception {
        HtmlPage page = getPage("selectManyCheckboxIdsInComposite.xhtml");

        // This will ensure JavaScript finishes before evaluating the page.
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: Pending"));

        HtmlCheckBoxInput cbox1 = (HtmlCheckBoxInput)page.getElementById("form:compId:cbox:0");
        page = cbox1.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: mcheck-1"));

        HtmlCheckBoxInput cbox2 = (HtmlCheckBoxInput)page.getElementById("form:compId:cbox:1");
        page = cbox2.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: mcheck-1 mcheck-2"));

        HtmlCheckBoxInput cbox3 = (HtmlCheckBoxInput)page.getElementById("form:compId:cbox:2");
        page = cbox3.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: mcheck-1 mcheck-2 mcheck-3"));
    }

    /**
     * This test verifies correct function of SelectManyCheckbox Component over Ajax.
     */
    @Test
    public void testSelectManyCheckboxNoComposite() throws Exception {
        HtmlPage page = getPage("selectManyCheckboxNoComposite.xhtml");
        
        // This will ensure JavaScript finishes before evaluating the page.
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: Pending"));

        page = getCheckBoxes(page).get(0).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: mcheck-1"));

        page = getCheckBoxes(page).get(1).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: mcheck-1 mcheck-2"));

        page = getCheckBoxes(page).get(2).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: mcheck-1 mcheck-2 mcheck-3"));
    }

    /**
     * This test verifies correct function of SelectOneRadio in a Composite
     * Component over Ajax.
     */
    @Test
    public void testSelectOneRadioInComposite() throws Exception {
        HtmlPage page = getPage("selectOneRadioInComposite.xhtml");

        // This will ensure JavaScript finishes before evaluating the page.
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: Pending"));

        page = getRadios(page).get(0).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: radio-1"));

        page = getRadios(page).get(1).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: radio-2"));

        page = getRadios(page).get(2).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: radio-3"));
    }

    /**
     * This test verifies correct function of SelectOneRadio in a Composite
     * Component over Ajax. The components in the page have ids.
     */
    @Test
    public void testSelectOneRadioIdsInComposite() throws Exception {
        HtmlPage page = getPage("selectOneRadioIdsInComposite.xhtml");

        // This will ensure JavaScript finishes before evaluating the page.
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: Pending"));

        HtmlRadioButtonInput radio1 = (HtmlRadioButtonInput)page.getElementById("form:compId:radio:0");
        page = radio1.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: radio-1"));

        HtmlRadioButtonInput radio2 = (HtmlRadioButtonInput)page.getElementById("form:compId:radio:1");
        page = radio2.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: radio-2"));

        HtmlRadioButtonInput radio3 = (HtmlRadioButtonInput)page.getElementById("form:compId:radio:2");
        page = radio3.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: radio-3"));
    }

    /**
     * This test verifies correct function of SelectOneRadio Component over Ajax.
     */
    @Test
    public void testSelectOneRadioNoComposite() throws Exception {
        HtmlPage page = getPage("selectOneRadioNoComposite.xhtml");

        // This will ensure JavaScript finishes before evaluating the page.
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: Pending"));

        page = getRadios(page).get(0).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: radio-1"));

        page = getRadios(page).get(1).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: radio-2"));

        page = getRadios(page).get(2).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("Status: radio-3"));
    }
    
    private List<HtmlCheckBoxInput> getCheckBoxes(final HtmlPage page) {
        final List<HtmlCheckBoxInput> checkBoxes = new ArrayList<>();
        final DomNodeList<DomElement> elements = page.getElementsByTagName("input");
        for (DomElement elem : elements) {
            if (elem instanceof HtmlCheckBoxInput) {
                checkBoxes.add((HtmlCheckBoxInput) elem);
            }
        }

        return checkBoxes;
    }
    
    private List<HtmlRadioButtonInput> getRadios(final HtmlPage page) {
        final List<HtmlRadioButtonInput> radios = new ArrayList<>();
        final DomNodeList<DomElement> elements = page.getElementsByTagName("input");
        for (DomElement elem : elements) {
            if (elem instanceof HtmlRadioButtonInput) {
                radios.add((HtmlRadioButtonInput)elem);
            }
        }
        
        return radios;
    }

}
