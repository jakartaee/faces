/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.faces22.composite_component;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIComponent;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue1641IT extends BaseITNG {

    /**
     * Verifies that a composite component and its retargeted action source
     * survive a postback under partial state saving: the inner command button
     * keeps its value and its action listener fires, incrementing the counter.
     *
     * @see UIComponent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1641
     */
    @Test
    void testCompositeStateSurvivesPostback() throws Exception {
        WebPage page = getPage("issue1641.xhtml");

        assertTrue(page.containsText("count=0"));
        assertTrue(page.containsSource("Click Me"));

        WebElement button = page.findElement(By.id("button:abutton"));
        page.guardHttp(button::click);

        assertTrue(page.containsText("count=1"));
        assertTrue(page.containsSource("Click Me"));
    }
}
