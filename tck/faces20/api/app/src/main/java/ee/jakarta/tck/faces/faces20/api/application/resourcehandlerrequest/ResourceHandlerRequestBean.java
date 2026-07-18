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

package ee.jakarta.tck.faces.faces20.api.application.resourcehandlerrequest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.Resource;
import jakarta.faces.application.ResourceHandler;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Exercises the public {@link ResourceHandler} contract for creating resources and probing library
 * existence, rendering {@code true} only if every assertion held.
 */
@Named
@RequestScoped
public class ResourceHandlerRequestBean {

    public String getTestResult() {
        ResourceHandler handler = FacesContext.getCurrentInstance().getApplication().getResourceHandler();

        boolean result = handler != null;

        // A resource without a library.
        Resource resource = handler.createResource("duke-nv.gif");
        result &= resource != null
                && resource.getLibraryName() == null
                && "duke-nv.gif".equals(resource.getResourceName())
                && "image/gif".equals(resource.getContentType());

        // The same resource in a library.
        resource = handler.createResource("duke-nv.gif", "nvLibrary");
        result &= resource != null
                && "nvLibrary".equals(resource.getLibraryName())
                && "duke-nv.gif".equals(resource.getResourceName())
                && "image/gif".equals(resource.getContentType());

        // An explicit content type overrides the derived one.
        resource = handler.createResource("duke-nv.gif", "nvLibrary", "text/xml");
        result &= resource != null && "text/xml".equals(resource.getContentType());

        // A null content type falls back to the derived one.
        resource = handler.createResource("duke-nv.gif", "nvLibrary", null);
        result &= resource != null && "image/gif".equals(resource.getContentType());

        // A non-existent resource or library yields null.
        result &= handler.createResource("foo.jpg") == null;
        result &= handler.createResource("duke-nv.gif", "nonExistant") == null;
        result &= !handler.libraryExists("oeunhtnhtnhhnhh");

        // The built-in Faces script resource is always available.
        result &= handler.createResource(ResourceHandler.FACES_SCRIPT_RESOURCE_NAME,
                ResourceHandler.FACES_SCRIPT_LIBRARY_NAME) != null;

        return Boolean.toString(result);
    }
}
