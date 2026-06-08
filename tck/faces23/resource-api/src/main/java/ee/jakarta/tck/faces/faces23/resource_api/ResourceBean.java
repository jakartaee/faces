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

package ee.jakarta.tck.faces.faces23.resource_api;

import java.io.IOException;
import java.io.InputStream;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.Resource;
import jakarta.faces.application.ResourceHandler;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ResourceBean {

    private static final String LIBRARY_NAME = "css";
    private static final String RESOURCE_NAME = "images/background.png";
    private static final String RESOURCE_TYPE = "images/png";
    private static final String COMBINED_NAME = LIBRARY_NAME + "/" + RESOURCE_NAME;
    private static final String STYLES_LIBRARY = "styles";

    public String getResourceWithLibrary() {
        ResourceHandler handler = FacesContext.getCurrentInstance().getApplication().getResourceHandler();
        Resource resource = handler.createResource(RESOURCE_NAME, LIBRARY_NAME, RESOURCE_TYPE);
        try {
            return resource.toString();
        } catch (Exception e) {
            return "** could not create resource " + RESOURCE_NAME + " in library " + LIBRARY_NAME + " **";
        }
    }

    public void setResourceWithLibrary(String resourceWithLibrary) {
        // noop
    }

    public String getResourceWithoutLibrary() {
        ResourceHandler handler = FacesContext.getCurrentInstance().getApplication().getResourceHandler();
        Resource resource = handler.createResource(COMBINED_NAME);
        try {
            return resource.toString();
        } catch (Exception e) {
            return "** could not create resource " + COMBINED_NAME + " **";
        }
    }

    public void setResourceWithoutLibrary(String resourceWithoutLibrary) {
        // noop
    }

    public String getResourceWithTrailingUnderscore() {
        return readResource("trailing.css");
    }

    public String getResourceWithLeadingUnderscore() {
        return readResource("leading.css");
    }

    public String getResourceWithInvalidVersion() {
        return readResource("noUnderscore.css");
    }

    public String getResourceWithValidVersion() {
        ResourceHandler handler = FacesContext.getCurrentInstance().getApplication().getResourceHandler();
        Resource resource = handler.createResource("foreground.css", STYLES_LIBRARY);

        if (resource != null) {
            try (InputStream is = resource.getInputStream()) {
                if (is.read() == -1) {
                    return "FAILURE";
                }
            } catch (IOException e) {
                return "FAILURE";
            }
        }

        return "SUCCESS";
    }

    private static String readResource(String resourceName) {
        ResourceHandler handler = FacesContext.getCurrentInstance().getApplication().getResourceHandler();
        Resource resource = handler.createResource(resourceName, STYLES_LIBRARY);

        if (resource != null) {
            try (InputStream is = resource.getInputStream()) {
                while (is.read() != -1) {
                    // Consume the resource to confirm it is readable.
                }
            } catch (IOException e) {
                return "FAILURE";
            }
        }

        return "SUCCESS";
    }
}
