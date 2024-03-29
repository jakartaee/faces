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

package com.sun.ts.tests.jsf.spec.render.datatable;

import java.util.ArrayList;
import java.util.List;

@jakarta.inject.Named("dList") @jakarta.enterprise.context.RequestScoped
public class DataListBean {

  private List<DataBean> dataList = new ArrayList<DataBean>();

  public DataListBean() {
    // default values
    dataList.add(new DataBean("Anna", 'f', (short) 28));
    dataList.add(new DataBean("Cort", 'm', (short) 7));
    dataList.add(new DataBean("Cade", 'm', (short) 4));
  }

  /**
   * @return the dataList
   */
  public List<DataBean> getDataList() {
    return dataList;
  }

  /**
   * @param dataList
   *          the dataList to set
   */
  public void setDataList(List<DataBean> dataList) {
    this.dataList = dataList;
  }
}
