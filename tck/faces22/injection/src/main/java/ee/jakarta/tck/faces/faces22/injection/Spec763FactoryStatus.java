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

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.FacesWrapper;
import jakarta.faces.FactoryFinder;
import jakarta.inject.Named;

/**
 * Exposes the injected message of every faces-config-declared factory wrapper to the view, keyed by simple class name
 * (e.g. {@code #{spec763Status.messages.FacesConfigFacesContextFactory}}). Factories are not reachable through a single
 * EL property like the application artifacts are, so they are resolved through {@link FactoryFinder} and the
 * {@link FacesWrapper} chain is walked until the {@link InjectedArtifact} wrapper is found.
 */
@Named("spec763Status")
@ApplicationScoped
public class Spec763FactoryStatus {

    private static final String[] FACTORY_TYPES = {
        FactoryFinder.VISIT_CONTEXT_FACTORY,
        FactoryFinder.EXCEPTION_HANDLER_FACTORY,
        FactoryFinder.EXTERNAL_CONTEXT_FACTORY,
        FactoryFinder.FACES_CONTEXT_FACTORY,
        FactoryFinder.PARTIAL_VIEW_CONTEXT_FACTORY,
        FactoryFinder.CLIENT_WINDOW_FACTORY,
        FactoryFinder.LIFECYCLE_FACTORY,
        FactoryFinder.RENDER_KIT_FACTORY,
        FactoryFinder.VIEW_DECLARATION_LANGUAGE_FACTORY,
        FactoryFinder.FACELET_CACHE_FACTORY,
        FactoryFinder.TAG_HANDLER_DELEGATE_FACTORY
    };

    public Map<String, String> getMessages() {
        Map<String, String> messages = new LinkedHashMap<>();

        for (String factoryType : FACTORY_TYPES) {
            for (Object factory = FactoryFinder.getFactory(factoryType); factory != null;
                    factory = factory instanceof FacesWrapper<?> wrapper ? wrapper.getWrapped() : null) {
                if (factory instanceof InjectedArtifact injectedArtifact) {
                    messages.put(factory.getClass().getSimpleName(), injectedArtifact.getInjectedMessage());
                    break;
                }
            }
        }

        return messages;
    }
}
