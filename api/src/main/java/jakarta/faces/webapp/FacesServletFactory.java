/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2021 Contributors to Eclipse Foundation.
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
package jakarta.faces.webapp;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;

/**
 * The Faces Servlet factory.
 * 
 * @author Manfred Riem
 */
public abstract class FacesServletFactory {
    
    /**
     * Get the Faces Servlet.
     * 
     * @return the Faces Servlet.
     */
    public abstract Servlet getFacesServlet(ServletConfig config);
}
