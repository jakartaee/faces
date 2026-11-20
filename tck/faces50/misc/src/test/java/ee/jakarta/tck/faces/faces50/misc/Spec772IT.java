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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * For a Map value of f:selectItems, the value of each item is the value of the entry, and its label is the key of the entry converted to a String, or an empty
 * string when the key is <code>null</code>. With <code>var</code>, the Map.Entry of the current item is exposed while the item attributes and pass-through
 * attributes are evaluated. This also applies to an f:selectItems nested in f:selectItemGroups, as the javadoc of UISelectItems specifies. For a Map value of
 * f:selectItemGroups, the label of each group is the key of its entry, and <code>var</code> exposes the Map.Entry to the nested f:selectItems. A
 * <code>null</code> value of an entry stays <code>null</code>.
 *
 * @see <a href="https://github.com/jakartaee/faces/issues/772">issue 772</a>
 */
class Spec772IT extends BaseITNG {

    private static final List<String> LABELS = List.of("Apple", "", "Banana");

    private WebPage page;

    /**
     * A Map value of an f:selectItems nested in f:selectItemGroups yields an item per entry, with <code>var</code> exposing the Map.Entry while
     * <code>itemValue</code> and <code>itemLabel</code> are evaluated. A postback of the item of a <code>null</code> value passes validation.
     */
    @Test
    void testMapNestedInSelectItemGroups() {
        page = getPage("spec772.xhtml");

        assertEquals(List.of("a", "n", ""), valuesOf("nested"));
        assertEquals(LABELS, labelsOf("nested"));
        assertEquals(List.of("va", "vn", "v"), valuesOf("nestedVar"));
        assertEquals(List.of("Apple:a", ":n", "Banana:"), labelsOf("nestedVar"));

        select("nested", "");
        select("nestedVar", "vn");
        submit();

        assertEquals("", page.findElement(By.id("form:messages")).getText());
        assertEquals("[]", resultOf("nested"));
        assertEquals("[vn]", resultOf("nestedVar"));
        assertEquals("", selectedValueOf("nested"));
    }

    /**
     * A Map value of an f:selectItems directly in the select component exposes the Map.Entry under <code>var</code> while <code>itemValue</code>,
     * <code>itemLabel</code> and the pass-through attributes are evaluated.
     */
    @Test
    void testMapWithVar() {
        page = getPage("spec772.xhtml");

        assertEquals(List.of("va", "vn", "v"), valuesOf("directVar"));
        assertEquals(List.of("Apple:a", ":n", "Banana:"), labelsOf("directVar"));
        assertEquals(List.of("kApple", "k", "kBanana"), attributeOf("directVar", "data-key"));

        select("directVar", "v");
        submit();

        assertEquals("", page.findElement(By.id("form:messages")).getText());
        assertEquals("[v]", resultOf("directVar"));
    }

    /**
     * A Map value of f:selectItemGroups yields a group per entry, labeled with the key of the entry, or with an empty string when the key is <code>null</code>.
     * Its <code>var</code> exposes the Map.Entry to the nested f:selectItems.
     */
    @Test
    void testMapValueOfSelectItemGroups() {
        page = getPage("spec772.xhtml");

        List<String> groupLabels = page.findElements(By.cssSelector("#form\\:groups optgroup")).stream().map(group -> group.getAttribute("label"))
            .collect(toList());
        assertEquals(List.of("fruit", ""), groupLabels);
        assertEquals(List.of("a", "n", "", "c"), valuesOf("groups"));
        assertEquals(List.of("Apple", "", "Banana", "Cherry"), labelsOf("groups"));

        select("groups", "c");
        submit();

        assertEquals("", page.findElement(By.id("form:messages")).getText());
        assertEquals("[c]", resultOf("group"));
    }

    /**
     * A Map value of an f:selectItems directly in the select component labels the item of a <code>null</code> key with an empty string.
     */
    @Test
    void testNullKeyOfMapValueIsEmptyLabel() {
        page = getPage("spec772.xhtml");

        assertEquals(List.of("a", "n", ""), valuesOf("direct"));
        assertEquals(LABELS, labelsOf("direct"));
    }

    /**
     * A Map value of an f:selectItems directly in the select component keeps a <code>null</code> value of an entry as the value of its item. While the value of
     * the select component is <code>null</code>, that item is therefore rendered selected, as "Rendering the option elements" of the Listbox renderer specifies
     * for a <code>null</code> item value.
     */
    @Test
    void testNullValueOfMapValueStaysNull() {
        page = getPage("spec772-nullValue.xhtml");

        assertEquals(List.of("a", ""), valuesOf("direct"));
        assertEquals("", selectedValueOf("direct"));
    }

    private void select(String id, String value) {
        new Select(page.findElement(By.id("form:" + id))).selectByValue(value);
    }

    private void submit() {
        page.guardHttp(page.findElement(By.id("form:submit"))::click);
    }

    private String resultOf(String name) {
        return page.findElement(By.id("form:" + name + "Result")).getText();
    }

    private String selectedValueOf(String id) {
        return new Select(page.findElement(By.id("form:" + id))).getFirstSelectedOption().getAttribute("value");
    }

    private List<String> valuesOf(String id) {
        return attributeOf(id, "value");
    }

    private List<String> attributeOf(String id, String name) {
        return options(id).stream().map(option -> option.getAttribute(name)).collect(toList());
    }

    private List<String> labelsOf(String id) {
        return options(id).stream().map(WebElement::getText).collect(toList());
    }

    private List<WebElement> options(String id) {
        return page.findElements(By.cssSelector("#form\\:" + id + " option"));
    }

}
