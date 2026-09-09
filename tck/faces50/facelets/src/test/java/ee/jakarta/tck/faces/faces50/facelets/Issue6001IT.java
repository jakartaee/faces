/*
 * Copyright (c) Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GPL-2.0 with Classpath-exception-2.0 which
 * is available at https://openjdk.java.net/legal/gplv2+ce.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 or Apache-2.0
 */
package ee.jakarta.tck.faces.faces50.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A tag generates its mark id from the number of times it has been applied so far in the current build, so a fragment applied more often than the build before
 * it applied it shifts the mark id of every tag it holds and none of them finds back the component it built. Where a {@code binding} then hands such a tag the
 * component of that earlier build, the component arrives with the children of that build attached and must have them cleaned up like a component found back
 * under its parent, or its tags build a second set beside the first.
 *
 * @see https://github.com/eclipse-ee4j/mojarra/issues/6001
 */
class Issue6001IT extends BaseITNG {

    @FindBy(id = "form:toggle")
    private WebElement toggle;

    /**
     * Toggling slot 0 from text to table applies the fragment twice where the build before it applied it once, so the tags of slot 1 all shift and the bound
     * component of slot 1 arrives through its binding. Each slot must hold exactly one child.
     */
    @Test
    void testFragmentAppliedMoreOftenThanBefore() {
        var page = getPage("issue6001.xhtml");
        assertEquals(0, getComponentCount(page, "form:child_slot0"));
        assertEquals(1, getComponentCount(page, "form:child_slot1"));

        page.guardHttp(toggle::click);
        assertEquals(1, getComponentCount(page, "form:child_slot0"), page.getSource());
        assertEquals(1, getComponentCount(page, "form:child_slot1"), page.getSource());
    }

    /**
     * Toggling back applies the fragment once where the build before it applied it twice, which every tag still matches, and toggling once more applies it
     * twice again with the binding of slot 1 filled by a build which itself reused it.
     */
    @Test
    void testFragmentAppliedRepeatedly() {
        var page = getPage("issue6001.xhtml");
        page.guardHttp(toggle::click);
        page.guardHttp(toggle::click);
        assertEquals(0, getComponentCount(page, "form:child_slot0"), page.getSource());
        assertEquals(1, getComponentCount(page, "form:child_slot1"), page.getSource());

        page.guardHttp(toggle::click);
        assertEquals(1, getComponentCount(page, "form:child_slot0"), page.getSource());
        assertEquals(1, getComponentCount(page, "form:child_slot1"), page.getSource());
    }

    /**
     * A composite component reached through a binding arrives holding the implementation its earlier build applied, which lives in a facet of its own, and must
     * be cleaned up as the children of any other component are.
     */
    @Test
    void testCompositeComponentFragmentAppliedMoreOftenThanBefore() {
        var page = getPage("issue6001-composite.xhtml");
        assertEquals(0, getComponentCount(page, "form:child_slot0:text"));
        assertEquals(1, getComponentCount(page, "form:child_slot1:text"));

        page.guardHttp(toggle::click);
        assertEquals(1, getComponentCount(page, "form:child_slot0:text"), page.getSource());
        assertEquals(1, getComponentCount(page, "form:child_slot1:text"), page.getSource());
    }

    private int getComponentCount(WebPage page, String clientId) {
        return page.findElements(By.id(clientId)).size();
    }

}
