/*
 * Copyright (c) Contributors to Eclipse Foundation.
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
package org.eclipse.ee4j.tck.faces.faces50.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import jakarta.faces.application.Application;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Spec1501IT extends BaseITNG {

    /**
     * @see Application#publishEvent(jakarta.faces.context.FacesContext, Class, Object)
     * @see https://github.com/jakartaee/faces/issues/1501
     */
    @Test
    void observedPhases() {
        WebPage page = getPage("spec1501.xhtml");
        WebElement observedEventsOnInitialRequest = page.findElement(By.id("observedEvents"));
        assertEquals(List.of(
                "PostConstructApplicationEvent: true",
                "PostConstructViewMapEvent: true",
                "MyPostConstructViewMapEvent: true",
                "PreRenderViewEvent: true",
                "MyPreRenderViewEvent: true").toString(), observedEventsOnInitialRequest.getText());
    }
}
