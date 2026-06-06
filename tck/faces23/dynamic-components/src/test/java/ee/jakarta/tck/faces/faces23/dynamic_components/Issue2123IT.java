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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A custom component that adds a dynamic panel grid child pre-render and a nested output text
 * grandchild on postback must render the grandchild inside the parent across repeated postbacks.
 */
public class Issue2123IT extends BaseITNG {

    private static final String NEW_OUTPUT = "NEW-OUTPUT";

    /**
     * Initially only the dynamic panel grid is present (no grandchild yet); each postback keeps the
     * added output text component nested between the parent's encodeBegin and encodeEnd markers.
     *
     * @see jakarta.faces.event.PostRestoreStateEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2123
     */
    @Test
    void testAddComponent() throws Exception {
        WebPage page = getPage("issue2123.xhtml");
        assertTrue(!page.getSource().contains(NEW_OUTPUT), "grandchild not yet added before postback");

        WebElement button = page.findElement(By.id("button"));
        page.guardHttp(button::click);
        assertOutputInPlace(page);

        button = page.findElement(By.id("button"));
        page.guardHttp(button::click);
        assertOutputInPlace(page);
    }

    private void assertOutputInPlace(WebPage page) {
        String source = page.getSource();
        assertTrue(source.indexOf("Component::encodeBegin") < source.indexOf(NEW_OUTPUT));
        assertTrue(source.indexOf(NEW_OUTPUT) < source.indexOf("Component::encodeEnd"));
    }
}
