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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.view.facelets.TagDecorator;

class Issue3727IT extends BaseITNG {

    /**
     * A plain select/option block in a view that also decorates elements with jsf:id is emitted with its
     * tags correctly nested: the closing select tag follows the last closing option tag.
     *
     * @see TagDecorator
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3727
     */
    @Test
    void plainSelectKeepsItsOptionsNested() {
        WebPage page = getPage("issue3727.xhtml");
        String source = page.getSource();

        assertEquals(200, page.getResponseStatus(), "The view must render.");
        assertTrue(source.indexOf("</select>") > source.lastIndexOf("</option>"),
                "The closing select tag must follow the last closing option tag.");
        assertEquals(3, page.findElements(By.cssSelector("#plain option")).size(),
                "All options must remain children of the select.");
    }
}
