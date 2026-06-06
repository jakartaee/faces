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
import jakarta.faces.application.Application;
import jakarta.faces.application.ApplicationFactory;

public class FacesConfigApplicationFactory extends ApplicationFactory {

    private ApplicationFactory oldFactory = null;

    private NewApplication newApp = null;

    public FacesConfigApplicationFactory() {
    }

    public FacesConfigApplicationFactory(ApplicationFactory yourOldFactory) {
        oldFactory = yourOldFactory;
    }

    @Override
    public Application getApplication() {
        if (null == newApp) {
            newApp = new NewApplication(oldFactory.getApplication());
            newApp.setInjectedMessage(getInjectedMessage());
        }
        return newApp;
    }

    @Override
    public void setApplication(Application application) {
        newApp = (NewApplication) application;
    }

    @Override
    public String toString() {
        return "FacesConfigApplicationFactory";
    }

    @Resource(name = "injectedMessage")
    private String injectedMessage;

    public String getInjectedMessage() {
        return injectedMessage + " " + postConstructCalled;
    }

    private String postConstructCalled;

    @PostConstruct
    private void doPostConstruct() {
        postConstructCalled = "@PostConstruct called";
    }
}
