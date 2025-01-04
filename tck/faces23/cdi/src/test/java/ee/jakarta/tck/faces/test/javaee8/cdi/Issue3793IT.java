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

import jakarta.faces.annotation.FacesConfig;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Issue3793IT extends BaseITNG {

  /**
   * @see FacesConfig#version()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3797
   */
  @Test
  void facesConfig23() throws Exception {
        WebPage page = getPage("faces/mojarraFacesConfigVersion.xhtml");

      assertFalse(page.getPageSource().contains("2.3"));
    }
}
