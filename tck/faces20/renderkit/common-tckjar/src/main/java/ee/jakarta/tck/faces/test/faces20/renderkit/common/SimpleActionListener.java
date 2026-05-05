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

package ee.jakarta.tck.faces.test.faces20.renderkit.common;

import java.io.Serializable;
import java.util.Map;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ActionListener;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;

@Named("ActionListener")
@SessionScoped
public class SimpleActionListener implements ActionListener, Serializable {

    private static final long serialVersionUID = -2123380871083456327L;

    // Selenium cannot inspect response headers, so mirror the PASSED/FAILED status onto the page.
    private String result = "";

    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {
        UIComponent component = event.getComponent();
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse) extContext.getResponse();
        Map<String, String> requestParamMap = extContext.getRequestParameterMap();
        String expectedId = requestParamMap.get("expectedId");

        if (expectedId == null) {
            response.addHeader("actionEvent", "Test error. Can't find expected component ID.");
            result = "FAILED. Can't find expected component ID";
        }
        else if (!expectedId.equals(component.getId())) {
            String message = "Expected component ID '" + expectedId + "', received: '" + component.getId() + '\'';
            response.addHeader("actionEvent", message);
            result = "FAILED. " + message;
        }
        else {
            response.addHeader("actionEventOK", "PASSED");
            result = "PASSED";
        }
    }

    public String getResult() {
        return result;
    }
}
