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

package ee.jakarta.tck.faces.faces22.composite_component;

import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewDeclarationLanguage;
import jakarta.inject.Named;

/**
 * Creates a composite component programmatically through the VDL, addressing it by its composite
 * taglib URI and passing a populated attributes map.
 */
@Named
@RequestScoped
public class Issue2566Bean {

    private static final String TAGLIB_URI = "jakarta.faces.composite/issue2566";
    private static final String TAG_NAME = "issue2566";

    public String getCreateCompositeComponent() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("pi", 3.14f);
        attributes.put("pagecontent", "issue2566");

        FacesContext context = FacesContext.getCurrentInstance();
        ViewDeclarationLanguage vdl = context.getApplication().getViewHandler()
                .getViewDeclarationLanguage(context, context.getViewRoot().getViewId());

        UIComponent component = vdl.createComponent(context, TAGLIB_URI, TAG_NAME, attributes);

        return component != null ? "SUCCESS" : "FAILED";
    }
}
