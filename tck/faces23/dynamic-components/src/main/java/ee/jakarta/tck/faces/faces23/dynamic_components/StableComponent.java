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

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.SystemEvent;

@FacesComponent(value = "ee.jakarta.tck.faces.faces23.dynamic_components.StableComponent")
public class StableComponent extends DynamicComponentBase {

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (FacesContext.getCurrentInstance().getMaximumSeverity() != null) {
            return;
        }

        HtmlInputText inputText1 = new HtmlInputText();
        inputText1.setValue("1");
        getChildren().add(inputText1);

        HtmlInputText inputText2 = new HtmlInputText();
        inputText2.setValue("2");
        getChildren().add(inputText2);

        HtmlInputText inputText3 = new HtmlInputText();
        inputText3.setId("text3");
        inputText3.setRequired(true);
        getChildren().add(inputText3);
    }
}
