/*
 * Copyright (c) 2011, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jsf.spec.templating.common;

import java.io.Serializable;

import jakarta.faces.component.UIOutput;

@jakarta.inject.Named("Character")
@jakarta.enterprise.context.RequestScoped
public class NameBean implements Serializable {

  private UIOutput name;

  public void setName(UIOutput n) {
    this.name = n;
  }

  public UIOutput getName() {
    return name;
  }

  public NameBean() {
    this.initialSetup();
  }

  // ----------------------------------------------- private methods

  private final void initialSetup() {
    UIOutput myComp = new UIOutput();
    myComp.setId("name");
    myComp.setValue("Vidtily Chernobyl");

    this.setName(myComp);
  }
}
