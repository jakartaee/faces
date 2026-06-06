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

package ee.jakarta.tck.faces.faces23.namespacedparams;

import java.io.Serializable;

import jakarta.faces.component.NamingContainer;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;

/**
 * View root that is itself a {@link NamingContainer}, prefixing every contained
 * client id with {@value #PREFIX}. This reproduces the namespacing scenario 
 * where request parameters (including those produced by
 * {@code f:param}) must still be resolved after the prefix is applied.
 */
public class NamingContainerViewRoot extends UIViewRoot implements NamingContainer, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String PREFIX = "MyNamingContainer";

    @Override
    public String getContainerClientId(FacesContext context) {
        return PREFIX + super.getContainerClientId(context);
    }
}
