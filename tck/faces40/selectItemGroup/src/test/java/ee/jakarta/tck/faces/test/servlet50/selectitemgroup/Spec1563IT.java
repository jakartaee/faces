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

package ee.jakarta.tck.faces.test.servlet50.selectitemgroup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import jakarta.faces.component.UISelectItemGroup;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1563IT extends BaseITNG {

    /**
     * @see UISelectItemGroup
     * @see https://github.com/jakartaee/faces/issues/1563
     */
    @Test
    void test() throws Exception {
        WebPage page = getPage("spec1563IT.xhtml");
        Select select = new Select(page.findElement(By.id("form:input")));

        assertValidMarkup(select);

        select.selectByValue("5");

        assertEquals("", page.findElement(By.id("form:messages")).getText(), "messages is empty before submit");
        assertEquals("", page.findElement(By.id("form:output")).getText(), "output is empty before submit");

        page.findElement(By.id("form:submit")).click();

        assertValidMarkup(select);
        assertEquals("", page.findElement(By.id("form:messages")).getText(), "messages is still empty after submit");
        assertEquals("5", page.findElement(By.id("form:output")).getText(), "output is '5' after submit");

        select = new Select(page.findElement(By.id("form:input")));
        select.selectByValue("2");
        page.findElement(By.id("form:submit")).click();

        assertValidMarkup(select);
        assertEquals("", page.findElement(By.id("form:messages")).getText(), "messages is still empty after submit");
        assertEquals("2", page.findElement(By.id("form:output")).getText(), "output is '2' after submit");
    }

    private static void assertValidMarkup(Select select) {
        List<WebElement> optgroups = select.getWrappedElement().findElements(By.tagName("optgroup"));
        assertEquals(2, optgroups.size(), "select has 2 optgroup children");

        for (WebElement optgroup : optgroups) {
            List<WebElement> options = optgroup.findElements(By.tagName("option"));
            assertEquals(3, options.size(), "optgroup has in turn 3 options");
        }

        assertEquals(6, select.getOptions().size(), "select element has 6 options");

        WebElement option2 = select.getOptions().get(1);
        assertEquals("Cat", option2.getText(), "2nd option is 'Cat'");

        WebElement option5 = select.getOptions().get(4);
        assertEquals("Audi", option5.getText(), "5th option is 'Audi'");
    }
}
