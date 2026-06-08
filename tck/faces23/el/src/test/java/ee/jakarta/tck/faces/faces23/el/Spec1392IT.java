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

package ee.jakarta.tck.faces.faces23.el;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.Application;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec1392IT extends BaseITNG {

    /**
     * The {@code #{request}} implicit object must resolve to the current {@code HttpServletRequest},
     * so {@code request.getParameter('foo')} returns the value of the {@code foo} request parameter.
     *
     * @see Application#getELResolver()
     * @see https://github.com/jakartaee/faces/issues/1392
     */
    @Test
    void requestParameter() {
        WebPage page = getPage("spec1392.xhtml?foo=bar");
        assertTrue(page.containsSource("foo:bar"));
    }
}
