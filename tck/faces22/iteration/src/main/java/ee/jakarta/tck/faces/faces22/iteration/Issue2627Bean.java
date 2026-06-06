/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces22.iteration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import jakarta.el.ValueExpression;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UINamingContainer;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.component.html.HtmlColumn;
import jakarta.faces.component.html.HtmlDataTable;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.component.html.HtmlPanelGroup;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class Issue2627Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    public String getTitle() {
        return "Can not add table dynamically";
    }

    public String getAddValue() {
        return "Add Datatable";
    }

    public void addTable() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIViewRoot root = fc.getViewRoot();
        UIComponent container = root.findComponent("form:dtcontainer");
        container.getChildren().add(creatTable(fc));
    }

    public HtmlDataTable creatTable(FacesContext fc) {
        HtmlDataTable table = new HtmlDataTable();
        ValueExpression ve = fc.getApplication().getExpressionFactory()
                .createValueExpression(fc.getELContext(), "#{issue2627Bean.testStrings}", Object.class);
        table.setId("table");
        table.setValueExpression("value", ve);
        table.setVar("str");

        UINamingContainer nc = new UINamingContainer();
        nc.setId("nc");

        HtmlPanelGroup ncPanel = new HtmlPanelGroup();
        ncPanel.setId("ncpanel");

        HtmlOutputText text = new HtmlOutputText();
        text.setId("strv");
        ValueExpression textve = fc.getApplication().getExpressionFactory()
                .createValueExpression(fc.getELContext(), "#{str}", Object.class);
        text.setValueExpression("value", textve);
        ncPanel.getChildren().add(text);

        nc.getChildren().add(ncPanel);

        HtmlPanelGroup panel = new HtmlPanelGroup();
        panel.getChildren().add(nc);

        HtmlColumn column = new HtmlColumn();
        column.getChildren().add(panel);
        table.getChildren().add(column);
        return table;
    }

    public List<String> getTestStrings() {
        String vs[] = {"one", "two", "three", "four"};
        return Arrays.asList(vs);
    }
}
