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

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The <code>var</code> of f:selectItems and of f:selectItemGroups exposes the current member of the collection referenced by <code>value</code>, as the VDL
 * documentation of their <code>var</code> attributes requires. This holds too for their pass-through attributes. Those of f:selectItems go on every optgroup
 * and option it produces, and those of f:selectItemGroups on its optgroups only, as their outer-most elements.
 *
 * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6176">issue 6176</a>
 */
class Issue6176IT extends BaseITNG {

    private static final List<String> FLAGS = List.of("on", "off");

    private WebPage page;

    /**
     * When <code>itemValue</code> points to a property of the member, <code>var</code> is still the member, not the item value.
     */
    @Test
    void testVarIsMemberWhenItemValueIsPropertyOfMember() {
        page = getPage("issue6176-itemValue.xhtml");
        assertEquals(FLAGS, flagsOf("menuItemValue"));
        assertEquals(FLAGS, flagsOf("listboxItemValue"));
    }

    /**
     * Without <code>itemValue</code>, <code>var</code> is the member, which is also the item value.
     */
    @Test
    void testVarIsMemberWithoutItemValue() {
        page = getPage("issue6176-element.xhtml");
        assertEquals(List.of("a", "b"), flagsOf("menuElement"));
    }

    /**
     * When the member is a <code>SelectItem</code>, <code>var</code> is that <code>SelectItem</code>. When the member is a <code>SelectItemGroup</code>,
     * <code>var</code> is that group for its optgroup and for each option of the group.
     */
    @Test
    void testVarIsSelectItemMember() {
        page = getPage("issue6176-selectItems.xhtml");
        assertEquals(List.of("one", "group", "group", "group"), flagsOf("menuSelectItems"));
    }

    /**
     * The <code>var</code> of f:selectItemGroups is the member which produced the group, also when it has an <code>itemValue</code>, which f:selectItemGroups
     * does not define. The pass-through attributes of f:selectItemGroups go on the optgroup of each group only, not on its options.
     */
    @Test
    void testSelectItemGroupsVarIsMemberOnOptgroupOnly() {
        page = getPage("issue6176-groups.xhtml");
        assertEquals(Arrays.asList("x", null, null, "y", null), flagsOf("menuGroups"));
    }

    /**
     * Returns the data-flag of each optgroup and option of the given select, in document order.
     */
    private List<String> flagsOf(String id) {
        String select = "#form\\:" + id;
        return page.findElements(By.cssSelector(select + " optgroup, " + select + " option")).stream().map(element -> element.getAttribute("data-flag"))
            .collect(toList());
    }

}
