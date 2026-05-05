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

package ee.jakarta.tck.faces.test.faces20.renderkit.grid;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.Application;
import jakarta.faces.component.html.HtmlColumn;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.component.html.HtmlPanelGrid;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("location")
@SessionScoped
public class GridUIBean implements Serializable {

    private static final long serialVersionUID = -2564031838083638087L;

    private HtmlPanelGrid gps;

    public HtmlPanelGrid getGps() {
        return gps;
    }

    public void setGps(HtmlPanelGrid panelGrid) {
        this.gps = panelGrid;
        gps.setId("coordinates");
        gps.setTitle("coordinates");
        gps.setColumns(2);
        gps.setColumnClasses("odd,even");
        gps.getChildren().add(buildColumn("North-Long", 3));
        gps.getChildren().add(buildColumn("East-Lat", 7));
    }

    private static HtmlColumn buildColumn(String id, int value) {
        Application application = FacesContext.getCurrentInstance().getApplication();
        HtmlColumn column = (HtmlColumn) application.createComponent(HtmlColumn.COMPONENT_TYPE);
        HtmlOutputText text = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        column.setId(id);
        text.setValue(value);
        column.getChildren().add(text);
        return column;
    }
}
