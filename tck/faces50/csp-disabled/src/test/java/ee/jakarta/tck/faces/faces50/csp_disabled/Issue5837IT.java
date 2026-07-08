/*
 * Copyright (c) Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GPL-2.0 with Classpath-exception-2.0 which
 * is available at https://openjdk.java.net/legal/gplv2+ce.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 or Apache-2.0
 */
package ee.jakarta.tck.faces.faces50.csp_disabled;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Issue5837IT extends BaseITNG {

    /**
     * A DOM event handler set via a (non-literal) expression must be rendered exactly once, even when the renderer
     * handles that event specially (chained submit/behavior script). Regression from #5723: the handler ended up in
     * getAttributesThatAreSet, so the optimized pass-thru emitted it raw in addition to the special rendering.
     * <p>
     * On Faces 5.0 with CSP disabled the special rendering is a {@code mojarra.ael(...)} script rather than an inline
     * {@code on*} attribute, so this asserts on the handler function call itself (rendering-model-agnostic): each must
     * occur exactly once in the response body. Without the fix the expression-set handlers occur twice.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5837
     */
    @Test
    void testEventHandlerRenderedOnce() {
        String body = getResponseBody("issue5837.xhtml");

        assertEquals(1, count(body, "commandLinkFn("), body);
        assertEquals(1, count(body, "commandButtonFn("), body);
        assertEquals(1, count(body, "checkboxFn("), body);
        assertEquals(1, count(body, "inputFn("), body);
        assertEquals(1, count(body, "commandLinkLiteralFn("), body);
    }

    /**
     * Counts non-overlapping occurrences of the given substring.
     */
    private static int count(String body, String substring) {
        int count = 0;
        for (int from = body.indexOf(substring); from != -1; from = body.indexOf(substring, from + substring.length())) {
            count++;
        }
        return count;
    }
}
