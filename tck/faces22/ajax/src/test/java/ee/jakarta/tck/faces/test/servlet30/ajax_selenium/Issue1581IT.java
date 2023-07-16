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

package ee.jakarta.tck.faces.test.servlet30.ajax_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class Issue1581IT extends BaseITNG {

    /**
     * This test verifies correct function of SelectManyCheckbox in a Composite
     * Component over Ajax. 
     */
    @Test
    public void testSelectManyCheckboxInComposite() throws Exception {
        WebPage page = getPage("issue1581.xhtml");

        // This will ensure JavaScript finishes before evaluating the page.
        page.waitReqJs();
        assertCheckBoxes(getCheckboxes(page), false, false, false, false);

        getCheckboxes(page).get(0).click();
        page.waitReqJs();
        assertCheckBoxes(getCheckboxes(page), true, false, false, false);

        getCheckboxes(page).get(1).click();
        page.waitReqJs();
        assertCheckBoxes(getCheckboxes(page), true, true, false, false);

        getCheckboxes(page).get(2).click();
        page.waitReqJs();
        assertCheckBoxes(getCheckboxes(page), true, true, true, false);

        getCheckboxes(page).get(3).click();
        page.waitReqJs();
        assertCheckBoxes(getCheckboxes(page), true, true, true, true);
    }


    private void assertCheckBoxes(List<WebElement> checkboxes, boolean... expectedValues) {
        for (int cnt = 0; cnt < checkboxes.size(); cnt++) {
            WebElement checkbox = checkboxes.get(cnt);
            //label assertion, the gettext of the parent returns the label only
            assert (checkbox.findElement(By.xpath("..")).getText()).equals("JAVASERVERFACES-" + (cnt + 1));
            boolean assertionValue = expectedValues[cnt];
            assertEquals(checkbox.isSelected(), assertionValue);
        }
    }

    private List<WebElement> getCheckboxes(WebPage page) {


        List<WebElement> checkBoxes = new ArrayList<>();

        List<WebElement> elements = page.findElements(By.tagName("input"));
        for (Iterator<WebElement> it = elements.iterator(); it.hasNext(); ) {
            WebElement elem = it.next();
            if (elem.getAttribute("type").equals("checkbox")) {
                checkBoxes.add(elem);
            }
        }

        return checkBoxes;
    }

}
