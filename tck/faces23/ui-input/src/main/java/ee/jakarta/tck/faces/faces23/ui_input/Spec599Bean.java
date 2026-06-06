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

package ee.jakarta.tck.faces.faces23.ui_input;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.Application;
import jakarta.faces.application.ViewHandler;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewDeclarationLanguage;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec599Bean {

    public String getDoCreateComponent() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();

        ViewHandler viewHandler = application.getViewHandler();
        ViewDeclarationLanguage vdl = viewHandler.getViewDeclarationLanguage(context, context.getViewRoot().getViewId());

        HtmlInputText inputText = (HtmlInputText) vdl.createComponent(context, "jakarta.faces.html", "inputText", null);

        return "jakarta.faces.Text".equals(inputText.getRendererType()) ? "SUCCESS" : "FAILED";
    }
}
