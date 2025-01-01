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

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.annotation.RequestMap;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

/**
 * Tests the availability of the request map via CDI
 *
 */
public class Spec1393IT extends BaseITNG {

  /**
   * @see Inject
     * @see RequestMap
     * @see https://github.com/jakartaee/faces/issues/1393
   */
  @Test
  void requestMap() throws Exception {

        WebPage page = getPage("injectRequestMap.xhtml");

        // Request attribute is set in AttributeFilter
        assertTrue(page.getPageSource().contains("fooAttribute:bar"));
    }

}
