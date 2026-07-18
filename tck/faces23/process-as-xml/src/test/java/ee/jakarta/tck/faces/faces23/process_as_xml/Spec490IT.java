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
package ee.jakarta.tck.faces.faces23.process_as_xml;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Spec490IT extends BaseITNG {

    /**
     * A view processed as {@code xhtml} passes non-component markup — XML declaration, comments and
     * CDATA sections — straight through to the response.
     *
     * @see jakarta.faces.view.ViewDeclarationLanguage
     * @see https://github.com/jakartaee/faces/issues/490
     */
    @Test
    void testXhtmlPassesThroughMarkup() {
        String body = getResponseBody("xhtmlview.xhtml");
        assertTrue(body.contains("hello-xhtml"), "component content rendered");
        assertTrue(body.contains("<!-- passthrough comment -->"), "comment passed through");
        assertTrue(body.contains("<![CDATA["), "CDATA delimiters passed through");
    }

    /**
     * A view processed as raw {@code xml} consumes non-component markup: the XML declaration and
     * comments do not appear in the response, and CDATA sections lose their delimiters, their
     * content being emitted as ordinary text.
     *
     * @see jakarta.faces.view.ViewDeclarationLanguage
     * @see https://github.com/jakartaee/faces/issues/490
     */
    @Test
    void testRawXmlConsumesMarkup() {
        String body = getResponseBody("faces/xmlview.view.xml");
        assertTrue(body.contains("hello-xml"), "component content rendered");
        assertFalse(body.contains("<!-- consumed comment -->"), "comment consumed");
        assertFalse(body.contains("<![CDATA["), "CDATA delimiters consumed");
        assertFalse(body.contains("<?xml"), "XML declaration consumed");
    }
}
