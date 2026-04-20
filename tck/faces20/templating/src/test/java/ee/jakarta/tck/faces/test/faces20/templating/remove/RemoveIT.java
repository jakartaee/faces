/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.faces20.templating.remove;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class RemoveIT extends BaseITNG {

    @Test
    void templateUIRemoveTagTest() {
        WebPage page = getPage("faces/remove/removeOne.xhtml");

        assertEquals(1, page.findElements(By.id("Rendered")).size(),
            "Expected <label id='Rendered'> to be rendered");
        assertEquals(0, page.findElements(By.id("Not-Rendered")).size(),
            "Expected <label id='Not-Rendered'> to be removed");
    }
}
