/*
 * Copyright (c) Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces50.facelets;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UINamingContainer;

@FacesComponent("com.example.Issue5584Composite")
public class Issue5584Composite extends UINamingContainer {

    private UIComponent nested;
    public String result;

    public void action() {
        result = "success";
    }

    public UIComponent getNested() {
        return nested;
    }

    public void setNested(UIComponent nested) {
        this.nested = nested;
    }

    public String getResult() {
        return result;
    }

}
