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
package ee.jakarta.tck.faces.faces22.flows;

import java.io.Serializable;

import jakarta.faces.application.Application;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.FacesContext;
import jakarta.faces.flow.FlowScoped;
import jakarta.inject.Named;

@Named("flow2Bean")
@FlowScoped(Issue4279Flow2Bean.TITLE)
public class Issue4279Flow2Bean implements Serializable {

    static final String TITLE = "flow2";
    private static final long serialVersionUID = 1L;

    public String getTitle() {
        return TITLE;
    }

    public String returnFromFlow2() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        NavigationHandler navigationHandler = application.getNavigationHandler();
        navigationHandler.getNavigationCase(facesContext, "returnFromFlow2", "returnFromFlow2");

        return "returnFromFlow2";
    }
}
