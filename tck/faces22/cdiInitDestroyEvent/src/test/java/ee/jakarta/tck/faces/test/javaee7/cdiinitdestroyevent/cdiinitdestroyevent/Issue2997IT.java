/*
 * Copyright (c) 2021, 2024 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee7.cdiinitdestroyevent.cdiinitdestroyevent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.flow.FlowScoped;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

// https://github.com/eclipse-ee4j/mojarra/issues/3001
// https://github.com/jakartaee/faces/issues/1734
@Disabled("Depends on non-specified abandoned flow -- See https://github.com/jakartaee/faces/issues/1955")
public class Issue2997IT extends BaseITNG {

    /**
     * @see FlowScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3001
     */
    @Test
    void globalReturn() throws Exception {
        WebPage page = getPage("");

        WebElement button = page.findElement(By.id("flow-with-templates"));
        button.click();

        String pageText = page.getPageSource();
        assertTrue(pageText.contains("Bottom From Template"));
        assertTrue(pageText.contains("issue2997Bean"));

        button = page.findElement(By.id("issue2997Home"));
        button.click();

        pageText = page.getPageSource();
        assertTrue(pageText.contains("Issue2997Home"));
        assertTrue(pageText.contains("flow-with-templates"));
        assertTrue(pageText.contains("issue2997Bean"));

        page = getPage("");

        button = page.findElement(By.id("flow-with-templates"));
        button.click();

        pageText = page.getPageSource();
        assertTrue(pageText.contains("Bottom From Template"));

        button = page.findElement(By.id("issue2997UserList"));
        button.click();

        pageText = page.getPageSource();
        assertTrue(pageText.contains("Issue2997UserList"));
        assertTrue(pageText.contains("flow-with-templates"));
        assertTrue(pageText.contains("issue2997Bean"));

        page = getPage("");

        button = page.findElement(By.id("flow-with-templates"));
        button.click();

        pageText = page.getPageSource();
        assertTrue(pageText.contains("Bottom From Template"));

        button = page.findElement(By.id("issue2997PageInFacesConfig"));
        button.click();

        pageText = page.getPageSource();
        assertTrue(pageText.contains("Issue2997PageInFacesConfig"));
        assertTrue(pageText.contains("flow-with-templates"));
        assertTrue(pageText.contains("issue2997Bean"));
    }
}
