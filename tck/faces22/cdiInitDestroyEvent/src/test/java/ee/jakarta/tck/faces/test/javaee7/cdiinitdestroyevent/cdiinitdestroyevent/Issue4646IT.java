/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
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

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.view.ViewScoped;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Issue4646IT extends BaseITNG {

  /**
   * @see ViewScoped
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4646
   */
  @Test
  void preDestroyEventIssue4646() throws Exception {
        WebPage page = getPage("faces/issue4646.xhtml");
        WebElement counterElement = page.findElement(By.id("counterMessage"));
        int currentCount = parseInt(counterElement.getText());
        
        // +1
        page = getPage("faces/issue4646.xhtml");
        counterElement = page.findElement(By.id("counterMessage"));
        assertEquals(currentCount + 1, parseInt(counterElement.getText()), "+1 should be the objects created");
        
        // +2
        page = getPage("faces/issue4646.xhtml");
        counterElement = page.findElement(By.id("counterMessage"));
        assertEquals(currentCount + 2, parseInt(counterElement.getText()), "+2 should be the objects created");
        
        // invalidate
        WebElement invalidateButton = page.findElement(By.id("invalidateSession"));
        invalidateButton.click();
        
        // should be the initial count
        page = getPage("faces/issue4646.xhtml");
        counterElement = page.findElement(By.id("counterMessage"));
        assertEquals(currentCount, parseInt(counterElement.getText()), "The initial count should be again");
        
        // invalidate again
        invalidateButton = page.findElement(By.id("invalidateSession"));
        invalidateButton.click();
    }
}
