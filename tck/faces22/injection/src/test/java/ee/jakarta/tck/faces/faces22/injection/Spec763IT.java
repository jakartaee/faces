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

package ee.jakarta.tck.faces.faces22.injection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec763IT extends BaseITNG {

    private static final String INJECTED = "Injected from value specified in web.xml @PostConstruct called";

    private static final String[] ARTIFACTS = {
        "FacesConfigApplicationFactory",
        "FacesConfigActionListener",
        "FacesConfigNavigationHandler",
        "FacesConfigViewHandler",
        "FacesConfigStateManager",
        "FacesConfigELResolver",
        "FacesConfigResourceHandler",
        "FacesConfigSystemEventListener",
        "FacesConfigPhaseListener",
        "FacesConfigVisitContextFactory",
        "FacesConfigExceptionHandlerFactory",
        "FacesConfigExternalContextFactory",
        "FacesConfigFacesContextFactory",
        "FacesConfigPartialViewContextFactory",
        "FacesConfigClientWindowFactory",
        "FacesConfigLifecycleFactory",
        "FacesConfigRenderKitFactory",
        "FacesConfigViewDeclarationLanguageFactory",
        "FacesConfigFaceletCacheFactory",
        "FacesConfigTagHandlerDelegateFactory"
    };

    /**
     * Verifies that resource injection (@Resource) and @PostConstruct are honored on every
     * faces-config-declared artifact type eligible for injection per the spec: the application and
     * lifecycle artifacts (ApplicationFactory, ActionListener, NavigationHandler, StateManager,
     * ResourceHandler, SystemEventListener, PhaseListener) and the factories (FacesContextFactory,
     * ExternalContextFactory, PartialViewContextFactory, ExceptionHandlerFactory, VisitContextFactory,
     * ClientWindowFactory, LifecycleFactory, RenderKitFactory, ViewDeclarationLanguageFactory,
     * FaceletCacheFactory, TagHandlerDelegateFactory). ViewHandler and ELResolver are also covered
     * pending jakartaee/faces#2180 (they are injectable in the impl but missing from the spec table).
     *
     * @see jakarta.faces.application.ApplicationFactory
     * @see https://github.com/jakartaee/faces/issues/763
     */
    @Test
    void testInjectArtifacts() throws Exception {
        WebPage page = getPage("spec763.xhtml");

        assertAll(Arrays.stream(ARTIFACTS).map(artifact -> (Executable) () ->
                assertTrue(page.containsText(artifact + ": " + INJECTED),
                        "Expected injected message for " + artifact)));
    }
}
