/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces50.ajax;

import java.io.Serializable;
import java.util.Map;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class Spec2243Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext context;

    private boolean shown;

    private String param;

    public void show() {
        shown = true;
    }

    /**
     * Shows, and obtains a bookmarkable URL with the view parameters of another view, which makes the implementation inspect the metadata of that view.
     */
    public void showWithBookmarkableURL() {
        shown = true;
        context.getApplication().getViewHandler().getBookmarkableURL(context, "/spec2243-metadata.xhtml", Map.of(), true);
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public boolean isShown() {
        return shown;
    }

}
