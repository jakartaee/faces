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
import jakarta.faces.view.facelets.BehaviorHandler;
import jakarta.faces.view.facelets.ComponentHandler;
import jakarta.faces.view.facelets.ConverterHandler;
import jakarta.faces.view.facelets.TagHandlerDelegate;
import jakarta.faces.view.facelets.TagHandlerDelegateFactory;
import jakarta.faces.view.facelets.ValidatorHandler;

public class FacesConfigTagHandlerDelegateFactory extends TagHandlerDelegateFactory implements InjectedArtifact {

    public FacesConfigTagHandlerDelegateFactory(TagHandlerDelegateFactory wrapped) {
        super(wrapped);
    }

    @Override
    public TagHandlerDelegate createComponentHandlerDelegate(ComponentHandler owner) {
        return getWrapped().createComponentHandlerDelegate(owner);
    }

    @Override
    public TagHandlerDelegate createValidatorHandlerDelegate(ValidatorHandler owner) {
        return getWrapped().createValidatorHandlerDelegate(owner);
    }

    @Override
    public TagHandlerDelegate createConverterHandlerDelegate(ConverterHandler owner) {
        return getWrapped().createConverterHandlerDelegate(owner);
    }

    @Override
    public TagHandlerDelegate createBehaviorHandlerDelegate(BehaviorHandler owner) {
        return getWrapped().createBehaviorHandlerDelegate(owner);
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
