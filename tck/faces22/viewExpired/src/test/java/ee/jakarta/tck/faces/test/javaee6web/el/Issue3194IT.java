/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee6web.el;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.ViewExpiredException;
import jakarta.faces.view.ViewScoped;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

@Disabled("Sloppy test using Mojarra specific assumptions. See https://github.com/jakartaee/faces/issues/1773")
public class Issue3194IT extends BaseITNG {

  /**
   * @see ViewScoped
     * @see ViewExpiredException
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3198
   */
  @Test
  void viewExpired() throws Exception {
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        getPage("faces/viewExpired.xhtml");
        WebPage page = getPage("faces/viewExpired.xhtml");
        assertTrue(page.getPageSource().contains("1"));
        page = getPage("faces/viewExpired.xhtml");
        assertTrue(page.getPageSource().contains("2"));
        page = getPage("faces/viewExpired.xhtml");
        assertTrue(page.getPageSource().contains("3"));
    }
}
