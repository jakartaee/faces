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
package ee.jakarta.tck.faces.faces23.facelets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * A Facelets tag that references a non-existent template or include must fail the request rather
 * than silently rendering nothing; the runtime surfaces the missing resource as an HTTP 500.
 */
class FaceletNotFoundIT extends BaseITNG {

    /**
     * A {@code <ui:composition template>} pointing at a missing template yields HTTP 500.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/955
     * @see jakarta.faces.view.facelets.FaceletException
     */
    @Test
    void testCompositionNotFound() {
        assertEquals(500, getStatusCode("faceletNotFoundComposition.xhtml"));
    }

    /**
     * A {@code <ui:decorate template>} pointing at a missing template yields HTTP 500.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/955
     * @see jakarta.faces.view.facelets.FaceletException
     */
    @Test
    void testDecorateNotFound() {
        assertEquals(500, getStatusCode("faceletNotFoundDecorate.xhtml"));
    }

    /**
     * A {@code <ui:include src>} pointing at a missing file yields HTTP 500.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/955
     * @see jakarta.faces.view.facelets.FaceletException
     */
    @Test
    void testIncludeNotFound() {
        assertEquals(500, getStatusCode("faceletNotFoundInclude.xhtml"));
    }
}
