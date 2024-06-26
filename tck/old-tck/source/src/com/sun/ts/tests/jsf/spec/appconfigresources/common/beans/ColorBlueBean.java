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
package com.sun.ts.tests.jsf.spec.appconfigresources.common.beans;

import java.io.Serializable;

@jakarta.inject.Named("Ball") @jakarta.enterprise.context.SessionScoped
public class ColorBlueBean implements Serializable {

  private static final long serialVersionUID = -2564071088038087108L; 

  private String color;

  public ColorBlueBean() {
    this.initialSetup();
  }

  /**
   * @return the color
   */
  public String getColor() {
    return color;
  }

  /**
   * @param color
   *          the color to set
   */
  public void setColor(String color) {
    this.color = color;
  }

  private void initialSetup() {
    color = "blue";
  }
}
