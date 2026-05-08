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

package ee.jakarta.tck.faces.faces22.bean_lifecycle_events;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.flow.FlowScoped;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

// https://github.com/eclipse-ee4j/mojarra/issues/3001
// https://github.com/jakartaee/faces/issues/1734
@Disabled("Depends on non-specified abandoned flow -- See https://github.com/jakartaee/faces/issues/1955")
public class Issue3001IT extends BaseITNG {

    /**
     * @see FlowScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3001
     */
    @Test
    void globalReturn() throws Exception {
        WebPage page = getPage("cdiInitDestroyEvent.xhtml");

        WebElement button = page.findElement(By.id("flow-with-templates"));
        button.click();

        String pageText = page.getSource();
        assertTrue(pageText.contains("Bottom From Template"));
        assertTrue(pageText.contains("Issue3001Bean"));

        button = page.findElement(By.id("issue3001Home"));
        button.click();

        pageText = page.getSource();
        assertTrue(pageText.contains("issue3001Home"));
        assertTrue(pageText.contains("flow-with-templates"));
        assertTrue(pageText.contains("Issue3001Bean"));

        page = getPage("cdiInitDestroyEvent.xhtml");

        button = page.findElement(By.id("flow-with-templates"));
        button.click();

        pageText = page.getSource();
        assertTrue(pageText.contains("Bottom From Template"));

        button = page.findElement(By.id("issue3001UserList"));
        button.click();

        pageText = page.getSource();
        assertTrue(pageText.contains("issue3001UserList"));
        assertTrue(pageText.contains("flow-with-templates"));
        assertTrue(pageText.contains("Issue3001Bean"));

        page = getPage("cdiInitDestroyEvent.xhtml");

        button = page.findElement(By.id("flow-with-templates"));
        button.click();

        pageText = page.getSource();
        assertTrue(pageText.contains("Bottom From Template"));

        button = page.findElement(By.id("issue3001PageInFacesConfig"));
        button.click();

        pageText = page.getSource();
        assertTrue(pageText.contains("issue3001PageInFacesConfig"));
        assertTrue(pageText.contains("flow-with-templates"));
        assertTrue(pageText.contains("Issue3001Bean"));
    }
}
