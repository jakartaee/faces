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

import jakarta.faces.event.ActionListener;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Spec901IT extends BaseITNG {

    /**
     * @see ActionListener
     * @see https://github.com/jakartaee/faces/issues/901
     */
    @Test
    void testActionListener1() throws Exception {
        WebPage page = getPage("spec901.xhtml");

        WebElement button1 = page.findElement(By.id("form:loginPanel:loginEvent"));
        page.guardHttp(button1::click);
        assertTrue(page.containsSource("a called b called"));

        WebElement button2 = page.findElement(By.id("form:loginPanel:cancelEvent:someOtherEvent"));
        page.guardHttp(button2::click);
        assertTrue(page.containsSource("c called"));
    }
}
