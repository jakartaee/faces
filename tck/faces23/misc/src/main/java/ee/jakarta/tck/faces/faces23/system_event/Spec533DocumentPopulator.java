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

package ee.jakarta.tck.faces.faces23.system_event;

import jakarta.faces.application.ApplicationConfigurationPopulator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Spec533DocumentPopulator extends ApplicationConfigurationPopulator {

    private static final String EXPECTED_NAMESPACE_URI = "https://jakarta.ee/xml/ns/jakartaee";
    private static final String LIFECYCLE = "lifecycle";
    private static final String PHASE_LISTENER = "phase-listener";
    private static final String PHASE_LISTENER_CLASS = Spec533PhaseListener.class.getName();

    @Override
    public void populateApplicationConfiguration(Document document) {
        Element documentElement = document.getDocumentElement();
        String namespace = documentElement.getNamespaceURI();
        if (!EXPECTED_NAMESPACE_URI.equals(namespace)) {
            throw new IllegalStateException("Unexpected namespace");
        }
        Element lifecycleElement = document.createElementNS(namespace, LIFECYCLE);
        Element phaseListenerElement = document.createElementNS(namespace, PHASE_LISTENER);
        Node phaseListenerNode = document.createTextNode(PHASE_LISTENER_CLASS);
        phaseListenerElement.appendChild(phaseListenerNode);
        lifecycleElement.appendChild(phaseListenerElement);
        documentElement.appendChild(lifecycleElement);
    }
}
