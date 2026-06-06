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

package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

public class Issue2457IT extends BaseITNG {

    /**
     * The DOCTYPE declared on a page that uses {@code ui:include} must be
     * preserved verbatim in the response, including its original casing.
     *
     * @see jakarta.faces.component.UIViewRoot#getDoctype()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2457
     */
    @Test
    void includeNotMissingDoctype() throws Exception {
        String response = getResponseBody("issue2457-doctype-include1.xhtml");
        assertTrue(response.contains("<!DOCTYPE HTML>"));
    }

    /**
     * The DOCTYPE declared on a template client that uses {@code ui:composition}
     * must be preserved verbatim in the response, including its original casing.
     *
     * @see jakarta.faces.component.UIViewRoot#getDoctype()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2457
     */
    @Test
    void compositionNotMissingDoctype() throws Exception {
        String response = getResponseBody("issue2457-doctype-composition1.xhtml");
        assertTrue(response.contains("<!DOCTYPE HTML>"));
    }

    /**
     * The lowercase HTML5 DOCTYPE declared on a template must be preserved
     * verbatim in the response, including its original casing.
     *
     * @see jakarta.faces.component.UIViewRoot#getDoctype()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2457
     */
    @Test
    void compositionNotMissingDoctypeLowercase() throws Exception {
        String response = getResponseBody("issue2457-doctype-composition1b.xhtml");
        assertTrue(response.contains("<!DOCTYPE html>"));
    }
}
