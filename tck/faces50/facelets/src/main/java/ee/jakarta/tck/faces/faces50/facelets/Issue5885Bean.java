/*
 * Copyright (c) Contributors to Eclipse Foundation.
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

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIOutput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue5885Bean {

    public static final String MODEL_VALUE = "MODEL VALUE";

    private String singleChildFacetValue = MODEL_VALUE;
    private String multiChildFacetValue = MODEL_VALUE;

    /**
     * Programmatically adds a component to the view, so that the view holds a dynamic action and the Facelets
     * refresh is therefore re-applied during Render Response.
     */
    public void addComponentResource(ComponentSystemEvent event) {
        FacesContext context = event.getFacesContext();
        UIOutput resource = new UIOutput();
        resource.setRendererType("jakarta.faces.resource.Stylesheet");
        resource.getAttributes().put("library", "issue5885");
        resource.getAttributes().put("name", "issue5885.css");
        context.getViewRoot().addComponentResource(context, resource, "head");
    }

    public String getSingleChildFacetValue() {
        return singleChildFacetValue;
    }

    public void setSingleChildFacetValue(String singleChildFacetValue) {
        this.singleChildFacetValue = singleChildFacetValue;
    }

    public String getMultiChildFacetValue() {
        return multiChildFacetValue;
    }

    public void setMultiChildFacetValue(String multiChildFacetValue) {
        this.multiChildFacetValue = multiChildFacetValue;
    }
}
