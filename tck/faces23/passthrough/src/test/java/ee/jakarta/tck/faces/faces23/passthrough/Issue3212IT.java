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

package ee.jakarta.tck.faces.faces23.passthrough;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.component.UIComponent;

class Issue3212IT extends BaseITNG {

    /**
     * A pass through attribute on an h:column whose value expression is row scoped is evaluated per row and
     * written onto that row's cell, also when the column declares facets.
     *
     * @see UIComponent#getPassThroughAttributes()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3212
     */
    @Test
    void rowScopedPassThroughAttributeIsEvaluatedPerRow() {
        WebPage page = getPage("issue3212.xhtml");

        assertEquals(200, page.getResponseStatus(), "The view must render.");

        List<WebElement> cells = page.findElements(By.cssSelector("#table tbody td"));
        List<Issue3212Bean.Entity> entities = new Issue3212Bean().getEntities();

        assertEquals(entities.size(), cells.size(), "One rendered cell per row.");

        for (int row = 0; row < entities.size(); row++) {
            assertEquals(entities.get(row).getModifiedOn().toString(), cells.get(row).getDomAttribute("data-order"),
                    "Row " + row + " must carry its own pass through attribute value.");
        }
    }
}
