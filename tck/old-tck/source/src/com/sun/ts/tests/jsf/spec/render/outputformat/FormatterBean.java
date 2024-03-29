/*
 * Copyright (c) 2009, 2018 Oracle and/or its affiliates. All rights reserved.
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

/*
 * $Id$
 */
package com.sun.ts.tests.jsf.spec.render.outputformat;

import java.io.Serializable;

@jakarta.inject.Named("info") @jakarta.enterprise.context.SessionScoped
public class FormatterBean implements Serializable {

  private String technology = "JSF";

  private String component = "f:param";

  /** Creates a new instance of MessageBean */
  public FormatterBean() {
  }

  public String getTechnology() {
    return technology;
  }

  public void setTechnology(String technology) {
    this.technology = technology;
  }

  public String getComponent() {
    return component;
  }

  public void setComponent(String component) {
    this.component = component;
  }
}
