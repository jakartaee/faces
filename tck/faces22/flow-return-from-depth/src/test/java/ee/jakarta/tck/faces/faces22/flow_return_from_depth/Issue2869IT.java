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

package ee.jakarta.tck.faces.faces22.flow_return_from_depth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A flow-return whose outcome resolves to a flow-return in the calling flow must unwind the whole
 * flow stack, returning past several nested flows in a single navigation.
 */
class Issue2869IT extends BaseITNG {

    /**
     * Entering flow1, calling flow2 from it and then taking flow2's return returns all the way out
     * to the original page (past both flows).
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2869
     * @see jakarta.faces.flow.FlowHandler
     */
    @Test
    void returnFromDepth() {
        WebPage page = getPage("index.xhtml");
        assertEquals("Return From Depth Test", page.findElement(By.id("status")).getText(), "start page");

        page.guardHttp(page.findElement(By.id("form:flow1"))::click);
        page.guardHttp(page.findElement(By.id("form:flow2"))::click);
        page.guardHttp(page.findElement(By.id("form:exit2"))::click);

        assertEquals("Return From Depth Test", page.findElement(By.id("status")).getText(), "returned to start page");
    }
}
