/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee8.ajax_new;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.UIViewRoot;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class Spec1423IT extends BaseITNG {

    /**
     * @see UIViewRoot#addComponentResource(jakarta.faces.context.FacesContext, jakarta.faces.component.UIComponent, String)
     * @see https://github.com/jakartaee/faces/issues/1423
     */
    @Test
    public void testSpec1423Basic() throws Exception {
        WebPage page = getPage("spec1423.xhtml");
        WebElement button;

        assertTrue(page.findElement(By.id("scriptResult")).getText().isEmpty());
        assertTrue(page.findElement(By.id("stylesheetResult")).getText().isEmpty());

        button =  page.findElement(By.id("form1:addViaHead"));
        button.click();
        page.waitReqJs(Duration.ofMillis(60000));
        assertTrue(page.findElement(By.id("scriptResult")).getText().trim().equals("addedViaHead"));
        assertTrue(page.findElement(By.id("stylesheetResult")).getText().isEmpty());
    }

    @Test
    public void testSpec1423() throws Exception {
        WebPage page = getPage("spec1423.xhtml");
        WebElement button;

        assertTrue(page.findElement(By.id("scriptResult")).getText().isEmpty());
        assertTrue(page.findElement(By.id("stylesheetResult")).getText().isEmpty());

        button =  page.findElement(By.id("form1:addViaHead"));
        button.click();
        page.waitReqJs(Duration.ofMillis(6000));
        assertTrue(page.findElement(By.id("scriptResult")).getText().trim().equals("addedViaHead"));
        assertTrue(page.findElement(By.id("stylesheetResult")).getText().isEmpty());

        button =  page.findElement(By.id("form2:addViaInclude"));
        button.click();
        page.waitReqJs(Duration.ofMillis(6000));

        assertTrue(page.findElement(By.id("scriptResult")).getText().trim().equals("addedViaInclude"));
        assertTrue(page.findElement(By.id("stylesheetResult")).getText().trim().equals("rgb(255, 0, 0)"));

        button =  page.findElement(By.id("form1:addViaBody"));
        button.click();
        page.waitReqJs(Duration.ofMillis(6000));
        assertTrue(page.findElement(By.id("scriptResult")).getText().trim().equals("addedViaBody"));
        assertTrue(page.findElement(By.id("stylesheetResult")).getText().trim().equals("rgb(255, 0, 0)"));

        button =  page.findElement(By.id("form2:addViaInclude"));
        button.click();
        page.waitReqJs(Duration.ofMillis(6000));
        assertTrue(page.findElement(By.id("scriptResult")).getText().trim().equals("addedViaBody"));
        assertTrue(page.findElement(By.id("stylesheetResult")).getText().trim().equals("rgb(255, 0, 0)"));

        button =  page.findElement(By.id("form1:addProgrammatically"));
        button.click();
        page.waitReqJs(Duration.ofMillis(6000));
        assertTrue(page.findElement(By.id("scriptResult")).getText().trim().equals("addedProgrammatically"));
        assertTrue(page.findElement(By.id("stylesheetResult")).getText().trim().equals("rgb(0, 255, 0)"));

        button =  page.findElement(By.id("form1:addViaHead"));
        button.click();
        page.waitReqJs(Duration.ofMillis(6000));
        assertTrue(page.findElement(By.id("scriptResult")).getText().trim().equals("addedProgrammatically"));
        assertTrue(page.findElement(By.id("stylesheetResult")).getText().trim().equals("rgb(0, 255, 0)"));

        button =  page.findElement(By.id("form1:addViaBody"));
        button.click();
        page.waitReqJs(Duration.ofMillis(6000));
        assertTrue(page.findElement(By.id("scriptResult")).getText().trim().equals("addedProgrammatically"));
        assertTrue(page.findElement(By.id("stylesheetResult")).getText().trim().equals("rgb(0, 255, 0)"));

        button =  page.findElement(By.id("form2:addViaInclude"));
        button.click();
        page.waitReqJs(Duration.ofMillis(6000));
        assertTrue(page.findElement(By.id("scriptResult")).getText().trim().equals("addedProgrammatically"));
        assertTrue(page.findElement(By.id("stylesheetResult")).getText().trim().equals("rgb(0, 255, 0)"));
    }

}
