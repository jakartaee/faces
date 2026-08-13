/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Add and remove permutations applied to a bound panel group within a single action listener must
 * render the net effect of the permutation, not any intermediate state.
 */
public class Issue2399IT extends BaseITNG {

    private static final String ADDED_VALUE = "I was dynamically added";
    private static final String REMOVE_ME_VALUE = "Remove Me";

    /**
     * Adding a child yields that child.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2399
     */
    @Test
    void testAdd() {
        assertPermutation("add", true, true);
    }

    /**
     * Adding then removing the same child within one listener yields neither an added child nor a
     * disturbed static child.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2399
     */
    @Test
    void testAddRemove() {
        assertPermutation("addRemove", false, true);
    }

    /**
     * Adding, removing and adding again within one listener yields exactly one added child.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2399
     */
    @Test
    void testAddRemoveAdd() {
        assertPermutation("addRemoveAdd", true, true);
    }

    /**
     * Removing the static child yields a panel group without it.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2399
     */
    @Test
    void testRemove() {
        assertPermutation("remove", false, false);
    }

    /**
     * Removing the static child and adding it back within one listener restores it.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2399
     */
    @Test
    void testRemoveAdd() {
        assertPermutation("removeAdd", false, true);
    }

    /**
     * Removing, re-adding and removing the static child again within one listener leaves it removed.
     *
     * @see jakarta.faces.component.UIComponent#getChildren()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2399
     */
    @Test
    void testRemoveAddRemove() {
        assertPermutation("removeAddRemove", false, false);
    }

    /**
     * Clicks the button of the given permutation and asserts the resulting children of the bound
     * panel group. The static input's value only survives as a rendered attribute, so the assertion
     * reads the panel group's markup rather than its text.
     */
    private void assertPermutation(String permutation, boolean expectAdded, boolean expectRemoveMe) {
        WebPage page = getPage("issue2399.xhtml");
        page.guardHttp(page.findElement(By.id("form:" + permutation))::click);

        String group = page.findElement(By.id("form:group")).getAttribute("outerHTML");
        assertEquals(expectAdded, group.contains(ADDED_VALUE),
                permutation + " added child expected=" + expectAdded);
        assertEquals(expectRemoveMe, group.contains(REMOVE_ME_VALUE),
                permutation + " static child expected=" + expectRemoveMe);
    }
}
