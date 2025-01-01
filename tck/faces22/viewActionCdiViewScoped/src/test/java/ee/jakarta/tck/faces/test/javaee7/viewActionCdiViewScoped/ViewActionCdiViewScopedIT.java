/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.javaee7.viewActionCdiViewScoped;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIViewAction;
import jakarta.faces.view.ViewScoped;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class ViewActionCdiViewScopedIT extends BaseITNG {

  /**
   * @see UIViewAction
     * @see ViewScoped
     * @see https://github.com/javaee/mojarra/commit/44cf9e4603ed3f948286dec35c4c1fe93e3abb83
   */
  @Test
  void noQueryParam() throws Exception {
        WebPage page = getPage("");

        assertTrue(page.getPageSource().indexOf("First Page") != -1);
    }

  /**
   * @see UIViewAction
     * @see ViewScoped
     * @see https://github.com/javaee/mojarra/commit/44cf9e4603ed3f948286dec35c4c1fe93e3abb83
   */
  @Test
  void withQueryParam() throws Exception {
        WebPage page = getPage("/?page=2");

        assertTrue(page.getPageSource().indexOf("Second Page") != -1);
    }
}
