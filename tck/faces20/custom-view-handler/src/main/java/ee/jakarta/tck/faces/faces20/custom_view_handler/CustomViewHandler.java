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

package ee.jakarta.tck.faces.faces20.custom_view_handler;

import jakarta.faces.application.ViewHandler;
import jakarta.faces.application.ViewHandlerWrapper;
import jakarta.faces.context.FacesContext;

/**
 * A custom {@link ViewHandler} whose {@code deriveViewId} appends the {@code .xhtml} suffix, so an
 * extensionless request such as {@code /faces/issue3488} resolves to {@code /issue3488.xhtml}.
 */
public class CustomViewHandler extends ViewHandlerWrapper {

    public CustomViewHandler(ViewHandler wrapped) {
        super(wrapped);
    }

    @Override
    public String deriveViewId(FacesContext context, String requestViewId) {
        return super.deriveViewId(context, requestViewId + ".xhtml");
    }

    @Override
    public String deriveLogicalViewId(FacesContext context, String requestViewId) {
        return deriveViewId(context, requestViewId);
    }
}
