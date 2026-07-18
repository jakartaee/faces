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
package ee.jakarta.tck.faces.faces20.composite.editablevalueholder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class EditablevalueholderIT extends BaseITNG {

    @Test
    void compositeEditableValueHolderTest() {
        // case 1: only 'name' attribute specified
        WebPage pageOne = getPage("editablevalueholder/caseOne.xhtml");
        testValueListener(pageOne, "firstname", "caseOne", "testingOne");

        // case 2: both 'target' and 'name' attributes specified
        WebPage pageTwo = getPage("editablevalueholder/caseTwo.xhtml");
        testValueListener(pageTwo, "lastname", "caseTwo", "testingTwo");

        // case 3: the exposed value holder is a nested composite reusing the same id
        WebPage pageThree = getPage("editablevalueholder/caseThree.xhtml");
        testValueListener(pageThree, "firstname", "caseThree", "testingThree");

        // case 4: 'targets' points across a NamingContainer nested in the composite
        WebPage pageFour = getPage("editablevalueholder/caseFour.xhtml");
        testValueListener(pageFour, "lastname", "caseFour", "testingFour");
    }

    /**
     * The backing beans are session scoped, so each case must submit a value distinct from the one
     * the earlier case using the same bean left behind — otherwise no value change fires at all.
     */
    private void testValueListener(WebPage page, String textId, String spanId, String value) {
        WebElement input = findByIdSuffix(page, textId);
        input.clear();
        input.sendKeys(value);

        WebElement button = findByIdSuffix(page, "button");
        page.guardHttp(button::click);

        WebElement span = findByIdSuffix(page, spanId);
        assertEquals("TRUE", span.getText(), "Span after value change");

        // Test without changing the input value.
        WebElement button2 = findByIdSuffix(page, "button");
        page.guardHttp(button2::click);

        WebElement span2 = findByIdSuffix(page, spanId);
        assertEquals("", span2.getText(), "Span without value change");
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
