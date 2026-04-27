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
package ee.jakarta.tck.faces.test.faces20.api.model.scalardatamodel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import ee.jakarta.tck.faces.test.faces20.api.common.beans.TestBean;
import ee.jakarta.tck.faces.test.faces20.api.model.common.BaseModelTestServlet;
import ee.jakarta.tck.faces.test.util.common.util.JSFTestUtil;

import jakarta.faces.model.DataModel;
import jakarta.faces.model.ScalarDataModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ScalarDataModelTestServlet")
public class ScalarDataModelTestServlet extends BaseModelTestServlet {

    @Override
    public DataModel createDataModel() {
        return new ScalarDataModel();
    }

    @Override
    public void initDataModel(DataModel model) {
        TestBean bean = new TestBean();
        List list = new ArrayList();
        list.add(bean);
        setBeansList(list);
        model.setWrappedData(bean);
    }

    public void scalarDataModelCtorTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        DataModel model = new ScalarDataModel(new TestBean());

        int curRow = model.getRowIndex();

        if (curRow != 0) {
            out.println(JSFTestUtil.FAIL + " Expected getRowIndex() to return 0"
                    + " when called against DataModel instance created by"
                    + " passing data to wrap to constructor.");
            out.println("Row index returned: " + curRow);
            return;
        }

        if (!model.isRowAvailable()) {
            out.println(JSFTestUtil.FAIL + " Expected isRowAvailable() to return"
                    + " true when called against DataModel instance created"
                    + " by passing data to wrap to constructor.");
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    public void dataModelGetSetWrappedDataTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        DataModel model = createDataModel();

        TestBean bean = new TestBean();

        model.setWrappedData(bean);

        Object ret = model.getWrappedData();

        if (!bean.equals(ret)) {
            out.println(JSFTestUtil.FAIL + " The value returned from getWrappedData()"
                    + " was not the same as what was set via setWrappedData().");
            out.println("Expected: " + bean);
            out.println("Received: " + ret);
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    @Override
    public void dataModelGetRowCountTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        DataModel data = createDataModel();

        int result = data.getRowCount();
        if (result != -1) {
            out.println(JSFTestUtil.FAIL + " Expected DataModel.getRowCount() to"
                    + " return -1 if no data was available on the Model " + "tier.");
            out.println("Row count received: " + result);
            return;
        }

        data = createDataModel();
        initDataModel(data);

        result = data.getRowCount();
        if (result != 1) {
            out.println(JSFTestUtil.FAIL + " Expected DataModel.getRowCount() to "
                    + "return 1.");
            out.println("Row count received: " + result);
            return;
        }

        out.println(JSFTestUtil.PASS);
    }

    @Override
    public void dataModelGetRowDataTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        DataModel data = createDataModel();

        data.setRowIndex(0);
        Object result = data.getRowData();
        if (result != null) {
            out.println("Test FAILED[1].  Expected DataModel.getRowData() to "
                    + "return a null result.");
            return;
        }

        initDataModel(data);
        result = data.getRowData();
        Object bean = result;
        if (!bean.equals(beans.get(0))) {
            out.println("Test FAILED[1].  The Object returned by UIData."
                    + "getRowData() at index 0 was not the expected Object.");
            out.println("Expected: " + beans.get(0));
            out.println("Recevied: " + bean);
            return;
        }

        out.println(JSFTestUtil.PASS);
    }
}
