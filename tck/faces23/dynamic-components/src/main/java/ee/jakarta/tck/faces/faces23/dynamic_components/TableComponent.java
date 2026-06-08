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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import jakarta.faces.application.Application;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIColumn;
import jakarta.faces.component.html.HtmlDataTable;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.SystemEvent;

@FacesComponent(value = "ee.jakarta.tck.faces.faces23.dynamic_components.TableComponent")
public class TableComponent extends DynamicComponentBase {

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!context.isPostback()) {
            Application application = context.getApplication();

            HtmlDataTable dataTable = new HtmlDataTable();
            dataTable.setVar("_internal");
            dataTable.setValueExpression("value",
                application.getExpressionFactory().createValueExpression(
                    context.getELContext(), "#{issue1830Bean.list}", Object.class));
            getChildren().add(dataTable);

            UIColumn column = new UIColumn();
            column.setId(context.getViewRoot().createUniqueId());
            dataTable.getChildren().add(column);

            HtmlOutputText outputText = new HtmlOutputText();
            outputText.setId(context.getViewRoot().createUniqueId());
            outputText.setValueExpression("value",
                application.getExpressionFactory().createValueExpression(
                    context.getELContext(), "#{_internal}", Object.class));
            column.getChildren().add(outputText);
        }
    }
}
