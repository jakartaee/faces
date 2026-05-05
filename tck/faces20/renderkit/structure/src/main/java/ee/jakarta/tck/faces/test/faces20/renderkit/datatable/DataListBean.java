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
package ee.jakarta.tck.faces.test.faces20.renderkit.datatable;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("dList")
@RequestScoped
public class DataListBean {

    private List<DataBean> dataList = new ArrayList<>();

    public DataListBean() {
        dataList.add(new DataBean("Anna", 'f', (short) 28));
        dataList.add(new DataBean("Cort", 'm', (short) 7));
        dataList.add(new DataBean("Cade", 'm', (short) 4));
    }

    public List<DataBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataBean> dataList) {
        this.dataList = dataList;
    }
}
