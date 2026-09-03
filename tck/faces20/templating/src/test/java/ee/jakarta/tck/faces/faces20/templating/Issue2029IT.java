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

import jakarta.faces.view.facelets.TagAttributeException;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * The VDL documentation of the {@code template} attribute of {@code ui:composition} and {@code ui:decorate} requires a {@link TagAttributeException} when the
 * URI cannot be resolved. The request must therefore fail rather than silently render an empty page. The exception message is not asserted: these run in the
 * Production project stage, which does not surface it.
 */
class Issue2029IT extends BaseITNG {

    /**
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2029
     */
    @Test
    void compositionWithUnresolvableTemplateFails() {
        assertEquals(500, getPage("template/compositionBadPath.xhtml").getResponseStatus());
    }

    /**
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2029
     */
    @Test
    void compositionWithBlankTemplateFails() {
        assertEquals(500, getPage("template/compositionEmptyPath.xhtml").getResponseStatus());
    }

    /**
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2029
     */
    @Test
    void decorateWithUnresolvableTemplateFails() {
        assertEquals(500, getPage("template/decorateBadPath.xhtml").getResponseStatus());
    }

    /**
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2029
     */
    @Test
    void decorateWithBlankTemplateFails() {
        assertEquals(500, getPage("template/decorateEmptyPath.xhtml").getResponseStatus());
    }

}
