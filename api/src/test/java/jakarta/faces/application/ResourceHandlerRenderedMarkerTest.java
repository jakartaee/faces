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

package jakarta.faces.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import jakarta.faces.context.FacesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The rendered resource marker identifies the file, not the URL it is requested by, so that one file included twice
 * under two spellings of its name is still included once. A resource library shipping
 * <code>&#64;ResourceDependency(name = "jquery.js")</code> and an application writing
 * <code>name = "jquery.js?v=3"</code> name the same file, and a query string is what separates them.
 */
class ResourceHandlerRenderedMarkerTest {

    private final ResourceHandler resourceHandler = mock(ResourceHandler.class, CALLS_REAL_METHODS);
    private final FacesContext context = mock(FacesContext.class);

    @BeforeEach
    void giveTheContextALiveAttributesMap() {
        when(context.getAttributes()).thenReturn(new HashMap<>());
    }

    @Test
    void aNameCarryingAQueryStringMarksTheFileItNames() {
        resourceHandler.markResourceRendered(context, "jquery.js?v=3", "lib");

        assertTrue(resourceHandler.isResourceRendered(context, "jquery.js", "lib"));
        assertTrue(resourceHandler.isResourceRendered(context, "jquery.js?v=3", "lib"));
        assertTrue(resourceHandler.isResourceRendered(context, "jquery.js?v=4", "lib"));
    }

    @Test
    void aNameCarryingAQueryStringFindsTheFileMarkedWithout() {
        resourceHandler.markResourceRendered(context, "jquery.js", "lib");

        assertTrue(resourceHandler.isResourceRendered(context, "jquery.js?v=3", "lib"));
    }

    @Test
    void anotherFileOrLibraryStaysUnmarked() {
        resourceHandler.markResourceRendered(context, "jquery.js?v=3", "lib");

        assertFalse(resourceHandler.isResourceRendered(context, "jquery-ui.js?v=3", "lib"));
        assertFalse(resourceHandler.isResourceRendered(context, "jquery.js?v=3", "otherlib"));
    }

    @Test
    void aNullNameIsMarkedAndFoundWithoutThrowing() {
        resourceHandler.markResourceRendered(context, null, null);

        assertTrue(resourceHandler.isResourceRendered(context, null, null));
    }

}
