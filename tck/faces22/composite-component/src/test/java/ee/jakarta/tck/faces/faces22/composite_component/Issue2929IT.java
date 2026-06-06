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

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.UIComponent;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue2929IT extends BaseITNG {

    /**
     * @see UIComponent#getClientId()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2929
     */
    @Test
    void testCCWithBinding() throws Exception {
        WebPage page = getPage("issue2929.xhtml");

        WebElement anchor = page.getAnchors().get(0);
        page.guardAjax(anchor::click);

        assertEquals("commentBoxForm:bindID", page.findElement(By.id("compositeId")).getText());
        assertEquals("commentBoxForm:nonBindId", page.findElement(By.id("nonCompositeId")).getText());
    }
}
