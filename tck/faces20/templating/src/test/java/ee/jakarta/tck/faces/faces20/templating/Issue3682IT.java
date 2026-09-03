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

package ee.jakarta.tck.faces.faces20.templating;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3682IT extends BaseITNG {

    /**
     * A ui:composition nested inside a plain body element still yields a well formed document: the template supplies the whole page, so the body opens after
     * the html element rather than the other way round.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3682
     */
    @Test
    void compositionInsideAPlainBodyYieldsWellFormedOutput() {
        WebPage page = getPage("template/mismatchedComposition.xhtml");
        String source = page.getSource();

        assertEquals(200, page.getResponseStatus(), "The view must render.");
        assertTrue(page.containsText("Hello from Facelets"), "The template must have supplied the content.");
        assertTrue(
            source.indexOf("<body") > source.indexOf("<html"),
            "The body element must open after the html element."
        );
    }

}
