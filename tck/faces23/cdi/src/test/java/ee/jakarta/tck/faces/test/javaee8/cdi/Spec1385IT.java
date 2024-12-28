/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee8.cdi;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.context.Flash;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

/**
 * Tests the availability of The Flash via injection of a {@link Flash}
 * instance.
 *
 */
public class Spec1385IT extends ITBase {

  /**
   * @see Inject
     * @see Flash
     * @see https://github.com/jakartaee/faces/issues/1385
   */
  @Test
  void injectFlash() throws Exception {

        // Renders nothing of interest, should cause cookie to be set
        webClient.getPage(webUrl + "faces/injectFlash.xhtml?setFlash=true");

        // Next request processes cookie, should render value put in Flash and set "deletion" cookie
        HtmlPage page = webClient.getPage(webUrl + "faces/injectFlash.xhtml?getFlash=true");

        assertTrue(page.asXml().contains("foo:bar"));

        // No cookie anymore and the value put in Flash previously should not be rendered anymore
        page = webClient.getPage(webUrl + "faces/injectFlash.xhtml?getFlash=true");

        assertFalse(page.asXml().contains("foo:bar"));
    }

}
