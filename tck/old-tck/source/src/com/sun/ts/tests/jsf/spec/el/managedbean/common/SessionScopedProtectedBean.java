/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jsf.spec.el.managedbean.common;

import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@jakarta.inject.Named("sessionScopedProtected") @jakarta.enterprise.context.SessionScoped
public class SessionScopedProtectedBean extends ScopedBean
    implements Serializable {

  protected @PostConstruct void onPostConstruct() {
    setPostConstructProperty("session protected PostConstruct method invoked");
  }

  protected @PreDestroy void onPreDestroy() {
    PreDestroyProp
        .setPreDestroyProperty("session protected PreDestroy method invoked");
  }

  public SessionScopedProtectedBean() {
    System.out.println("SessionScopedProtectedBean created.");
  }
}
