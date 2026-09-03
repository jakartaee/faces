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

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Removes a Facelets created child from its parent and adds it straight back at the very index it came from, which must leave the view exactly as it was.
 */
@Named
@RequestScoped
public class Issue3484Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String TARGET = "form:outputText";

    public void removeAndReAdd() {
        UIComponent target = FacesContext.getCurrentInstance().getViewRoot().findComponent(TARGET);
        List<UIComponent> siblings = target.getParent().getChildren();
        int index = siblings.indexOf(target);

        siblings.remove(index);
        siblings.add(index, target);
    }

    public int getIndex() {
        UIComponent target = FacesContext.getCurrentInstance().getViewRoot().findComponent(TARGET);
        return target == null ? -1 : target.getParent().getChildren().indexOf(target);
    }

}
