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

package ee.jakarta.tck.faces.faces23.faces_data_model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.faces.component.UIData;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue1834IT extends BaseITNG {

    /**
     * @see UIData
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1834
     */
    @Test
    void testNestedRepeatDataTable() throws Exception {
        WebPage page = getPage("issue1834.xhtml");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                WebElement element = page.findElement(By.id("form:repeat:" + i + ":table:" + j + ":row"));
                assertNotNull(element);
            }
        }
    }
}
