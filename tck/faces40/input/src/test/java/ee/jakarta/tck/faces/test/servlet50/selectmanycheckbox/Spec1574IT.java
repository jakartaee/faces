/*
 * Copyright (c) 2021 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.selectmanycheckbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.html.HtmlSelectManyCheckbox;
import jakarta.faces.component.html.HtmlSelectOneRadio;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1574IT extends BaseITNG {

    /**
     * @see HtmlSelectManyCheckbox
     * @see https://github.com/jakartaee/faces/issues/1574
     */
    @Test
    void selectManyCheckboxDefaultMarkup() throws Exception {
        WebPage page = getPage("spec1574IT.xhtml");
        WebElement selectManyCheckbox = page.findElement(By.id("form:input"));
        assertValidMarkup(selectManyCheckbox, true, false);
    }

    /**
     * @see HtmlSelectManyCheckbox
     * @see https://github.com/jakartaee/faces/issues/1574
     */
    @Test
    void selectManyCheckboxLineDirectionMarkup() throws Exception {
        WebPage page = getPage("spec1574IT.xhtml?layout=lineDirection");
        WebElement selectManyCheckbox = page.findElement(By.id("form:input"));
        assertValidMarkup(selectManyCheckbox, true, false);
    }

    /**
     * @see HtmlSelectManyCheckbox
     * @see https://github.com/jakartaee/faces/issues/1574
     */
    @Test
    void selectManyCheckboxPageDirectionMarkup() throws Exception {
        WebPage page = getPage("spec1574IT.xhtml?layout=pageDirection");
        WebElement selectManyCheckbox = page.findElement(By.id("form:input"));
        assertValidMarkup(selectManyCheckbox, true, true);
    }

    /**
     * @see HtmlSelectManyCheckbox
     * @see https://github.com/jakartaee/faces/issues/1574
     */
    @Test
    void selectManyCheckboxListMarkup() throws Exception {
        WebPage page = getPage("spec1574IT.xhtml?layout=list");
        WebElement selectManyCheckbox = page.findElement(By.id("form:input"));
        assertValidMarkup(selectManyCheckbox, false, true);
    }

    /**
     * @see HtmlSelectOneRadio
     * @see https://github.com/jakartaee/faces/issues/1574
     */
    @Test
    void selectOneRadioDefaultMarkup() throws Exception {
        WebPage page = getPage("spec1574IT.xhtml?radio=true");
        WebElement selectOneRadio = page.findElement(By.id("form:input"));
        assertValidMarkup(selectOneRadio, true, false);
    }

    /**
     * @see HtmlSelectOneRadio
     * @see https://github.com/jakartaee/faces/issues/1574
     */
    @Test
    void selectOneRadioLineDirectionMarkup() throws Exception {
        WebPage page = getPage("spec1574IT.xhtml?radio=true&layout=lineDirection");
        WebElement selectOneRadio = page.findElement(By.id("form:input"));
        assertValidMarkup(selectOneRadio, true, false);
    }

    /**
     * @see HtmlSelectOneRadio
     * @see https://github.com/jakartaee/faces/issues/1574
     */
    @Test
    void selectOneRadioPageDirectionMarkup() throws Exception {
        WebPage page = getPage("spec1574IT.xhtml?radio=true&layout=pageDirection");
        WebElement selectOneRadio = page.findElement(By.id("form:input"));
        assertValidMarkup(selectOneRadio, true, true);
    }

    /**
     * @see HtmlSelectOneRadio
     * @see https://github.com/jakartaee/faces/issues/1574
     */
    @Test
    void selectOneRadioListMarkup() throws Exception {
        WebPage page = getPage("spec1574IT.xhtml?radio=true&layout=list");
        WebElement selectOneRadio = page.findElement(By.id("form:input"));
        assertValidMarkup(selectOneRadio, false, true);
    }

    private static void assertValidMarkup(WebElement element, boolean table, boolean vertical) {
        int inputFields = 0;

        if (table) {
            assertEquals("table", element.getTagName(), "element is table");
            assertEquals(1, element.findElements(By.xpath("./*")).size(), "table has 1 child");
            assertEquals("tbody", element.findElements(By.xpath("./*")).get(0).getTagName(), "table child is tbody");
            int tbodyChildCount = vertical ? 4 : 1;
            assertEquals(tbodyChildCount, element.findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).size(),
                    "tbody has " + tbodyChildCount + " rows");
            int i = 1;

            for (WebElement row : element.findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*"))) {
                assertEquals("tr", row.getTagName(), "row is tr");
                int trChildCount = vertical ? 1 : 4;
                assertEquals(trChildCount, row.findElements(By.xpath("./*")).size(), "tr has " + trChildCount + " cells");

                for (WebElement cell : row.findElements(By.xpath("./*"))) {
                    assertEquals("td", cell.getTagName(), "cell is td");

                    if (i % 2 == 0) {
                        assertEquals(1, cell.findElements(By.xpath("./*")).size(), "cell has 1 child");
                        assertEquals("table", cell.findElements(By.xpath("./*")).get(0).getTagName(), "cell child is table");
                        assertEquals(1, cell.findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).size(), "child table has 1 child");
                        assertEquals("tbody", cell.findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).get(0).getTagName(), "group is tbody");
                        int groupChildCount = vertical ? 3 : 1;
                        assertEquals(groupChildCount,
                                cell.findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).size(),
                                "group has " + groupChildCount + " rows");

                        for (WebElement group : cell.findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*"))) {
                            assertEquals("tr", group.getTagName(), "group is tr");

                            for (WebElement item : group.findElements(By.xpath("./*"))) {
                                assertEquals("td", item.getTagName(), "item is td");
                                assertEquals(2, item.findElements(By.xpath("./*")).size(), "td has 2 children");
                                assertEquals("input", item.findElements(By.xpath("./*")).get(0).getTagName(), "first child is input");
                                assertEquals("label", item.findElements(By.xpath("./*")).get(1).getTagName(), "second child is label");
                                inputFields++;
                            }
                        }
                    } else {
                        assertEquals(0, cell.findElements(By.xpath("./*")).size(), "cell has no children");
                    }

                    i++;
                }
            }
        } else {
            assertEquals("ul", element.getTagName(), "element is ul");
            assertEquals(2, element.findElements(By.xpath("./*")).size(), "ul has 2 children");

            for (WebElement group : element.findElements(By.xpath("./*"))) {
                assertEquals("li", group.getTagName(), "group is li");
                assertEquals(1, group.findElements(By.xpath("./*")).size(), "li has 1 child");
                assertEquals("ul", group.findElements(By.xpath("./*")).get(0).getTagName(), "child is ul");

                for (WebElement item : group.findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*"))) {
                    assertEquals("li", item.getTagName(), "item is li");
                    assertEquals(2, item.findElements(By.xpath("./*")).size(), "li has 2 children");
                    assertEquals("input", item.findElements(By.xpath("./*")).get(0).getTagName(), "first child is input");
                    assertEquals("label", item.findElements(By.xpath("./*")).get(1).getTagName(), "second child is label");
                    inputFields++;
                }
            }
        }

        assertEquals(6, inputFields, "there were 6 input fields");
    }
}
