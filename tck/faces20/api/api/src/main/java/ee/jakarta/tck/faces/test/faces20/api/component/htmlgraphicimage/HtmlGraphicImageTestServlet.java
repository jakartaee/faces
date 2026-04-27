/*
 * Copyright (c) 2009, 2026 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.faces20.api.component.htmlgraphicimage;

import ee.jakarta.tck.faces.test.faces20.api.component.uigraphic.UIGraphicTestServlet;

import jakarta.faces.component.UIComponentBase;
import jakarta.faces.component.html.HtmlGraphicImage;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/HtmlGraphicImageTestServlet")
public final class HtmlGraphicImageTestServlet extends UIGraphicTestServlet {

  private static final String[] attrNames = { "alt", "dir", "height", "lang", "longdesc", "onclick", "ondblclick", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "style", "styleClass", "title", "usemap", "width", "ismap" };

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    setRendererType("jakarta.faces.Image");
    setAttributeNames(attrNames);
  }

  @Override
  protected UIComponentBase createComponent() {
    return new HtmlGraphicImage();
  }
}
