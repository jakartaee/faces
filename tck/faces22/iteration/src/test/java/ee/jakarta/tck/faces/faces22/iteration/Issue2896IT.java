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

package ee.jakarta.tck.faces.faces22.iteration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Nested c:forEach generates child components with correctly-IDed clientIds derived from the surrounding loop
 * index expressions.
 */
class Issue2896IT extends BaseITNG {

    /**
     * After adding a new nested item, the freshly appended row is rendered with the expected generated component
     * id (form0:item0_3) and value (item[new3]).
     *
     * @see jakarta.faces.component.UIComponent#getClientId()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2896
     */
    @Test
    void testNestedForEach() {
        WebPage page = getPage("issue2896.xhtml");
        page.guardHttp(() -> page.findElement(By.id("form0:addButton")).click());
        String item = page.findElement(By.id("form0:item0_3")).getText();
        assertTrue(item.contains("item[new3]"), "Newly added nested item is rendered with the expected value");
    }
}
