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

import java.util.Collection;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.faces.component.visit.VisitContext;
import jakarta.faces.component.visit.VisitContextFactory;
import jakarta.faces.component.visit.VisitHint;
import jakarta.faces.context.FacesContext;

public class FacesConfigVisitContextFactory extends VisitContextFactory implements InjectedArtifact {

    public FacesConfigVisitContextFactory(VisitContextFactory wrapped) {
        super(wrapped);
    }

    @Override
    public VisitContext getVisitContext(FacesContext context, Collection<String> ids, Set<VisitHint> hints) {
        return getWrapped().getVisitContext(context, ids, hints);
    }

    @Resource(name = "injectedMessage")
    private String injectedMessage;

    private String postConstructCalled;

    @PostConstruct
    private void doPostConstruct() {
        postConstructCalled = "@PostConstruct called";
    }

    @Override
    public String getInjectedMessage() {
        return injectedMessage + " " + postConstructCalled;
    }
}
