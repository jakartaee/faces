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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A UIData component (here {@code h:dataTable}) whose value is an arbitrary {@link java.util.Collection}
 * that is not a {@link java.util.List} must iterate and render every element in encounter order.
 */
public class Spec479IT extends BaseITNG {

    /**
     * A dataTable backed by a plain Collection renders all rows in order.
     *
     * @see jakarta.faces.component.UIData#setValue(Object)
     * @see https://github.com/jakartaee/faces/issues/479
     */
    @Test
    void testDataTableCollection() {
        WebPage page = getPage("spec479.xhtml");
        String table = page.findElement(By.id("form:table")).getText();
        assertTrue(table.matches("(?s).*First0.*Last0.*First1.*Last1.*First2.*Last2.*"),
                "dataTable did not render all Collection rows in order, got: " + table);
    }
}
