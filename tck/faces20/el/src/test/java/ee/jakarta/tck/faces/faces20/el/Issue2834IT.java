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

import jakarta.el.ValueExpression;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2834IT extends BaseITNG {

    /**
     * @see ValueExpression#setValue
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2834
     */
    @Test
    void setNull5() {
        WebPage page = getPage("issue2834-5.xhtml");
        assertEquals("SUCCESS", page.findElement(By.id("result")).getText());
    }

    /**
     * @see ValueExpression#setValue
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2834
     */
    @Test
    void setNull6() {
        WebPage page = getPage("issue2834-6.xhtml");
        assertEquals("SUCCESS", page.findElement(By.id("result")).getText());
    }

    /**
     * @see ValueExpression#setValue
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2834
     */
    @Test
    void setNull7() {
        WebPage page = getPage("issue2834-7.xhtml");
        assertEquals("SUCCESS", page.findElement(By.id("result")).getText());
    }

    /**
     * @see ValueExpression#setValue
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2834
     */
    @Test
    void setNull8() {
        WebPage page = getPage("issue2834-8.xhtml");
        assertEquals("SUCCESS", page.findElement(By.id("result")).getText());
    }
}
