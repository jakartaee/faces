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
package ee.jakarta.tck.faces.faces20.api.context.facescontext;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.RenderKitFactory;
import jakarta.inject.Named;

@Named
@RequestScoped
public class FacesContextGetRenderKitBean {

    public String getResult() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        String original = root.getRenderKitId();
        try {
            root.setRenderKitId(null);
            if (context.getRenderKit() != null) {
                return "FAIL: a null renderKitId must yield a null RenderKit";
            }

            root.setRenderKitId("nosuchkit");
            if (context.getRenderKit() != null) {
                return "FAIL: an unknown renderKitId must yield a null RenderKit";
            }

            root.setRenderKitId(RenderKitFactory.HTML_BASIC_RENDER_KIT);
            if (context.getRenderKit() == null) {
                return "FAIL: the HTML_BASIC renderKitId must yield a RenderKit";
            }

            return "SUCCESS";
        } finally {
            root.setRenderKitId(original);
        }
    }
}
