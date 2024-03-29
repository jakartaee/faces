/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
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

 package com.sun.ts.tests.jsf.spec.render.commandlink;

 import java.util.HashMap;
 import java.util.Map;
 import java.io.Serializable;
 
 @jakarta.inject.Named("Attribute") @jakarta.enterprise.context.SessionScoped
 public class AttributeBean implements Serializable {
 
   private static final long serialVersionUID = -2564380871083456327L;
   
   private Map<String, Object> attMap = new HashMap<String, Object>();
 
   {
     attMap.put("manyattone", "manyOne");
     attMap.put("manyatttwo", "manyTwo");
     attMap.put("manyattthree", "manyThree");
   }
 
   public Map<String, Object> getAttMap() {
     return attMap;
   }
 
   public void setAttMap(Map<String, Object> attMap) {
     this.attMap = attMap;
   }
 
 }
 