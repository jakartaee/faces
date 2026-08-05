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

package ee.jakarta.tck.faces.faces20.el;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.el.ValueExpression;

class Issue2835IT extends BaseITNG {

    /**
     * ValueExpression#setValue writes through to the target property, including writing null and including a
     * nested bean property, and raises PropertyNotWritableException for a read only property.
     *
     * @see ValueExpression#setValue(jakarta.el.ELContext, Object)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2835
     */
    @Test
    void setValueWritesThroughAndRejectsReadOnlyProperties() {
        WebPage page = getPage("issue2835.xhtml");

        assertEquals("SUCCESS", page.findElement(By.id("setNullOnProperty")).getText(),
                "Setting null must null a plain property.");
        assertEquals("SUCCESS", page.findElement(By.id("setValueOnNestedProperty")).getText(),
                "Setting a value must reach a nested bean property.");
        assertEquals("SUCCESS", page.findElement(By.id("setNullOnNestedProperty")).getText(),
                "Setting null must null a nested bean property.");
        assertEquals("SUCCESS", page.findElement(By.id("setValueOnReadOnlyProperty")).getText(),
                "Setting a read only property must raise PropertyNotWritableException.");
    }
}
