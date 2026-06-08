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

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.PartialViewContext;
import jakarta.faces.context.PartialViewContextFactory;

public class FacesConfigPartialViewContextFactory extends PartialViewContextFactory implements InjectedArtifact {

    public FacesConfigPartialViewContextFactory(PartialViewContextFactory wrapped) {
        super(wrapped);
    }

    @Override
    public PartialViewContext getPartialViewContext(FacesContext context) {
        return getWrapped().getPartialViewContext(context);
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
