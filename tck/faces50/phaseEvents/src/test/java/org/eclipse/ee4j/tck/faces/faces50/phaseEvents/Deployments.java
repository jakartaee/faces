/*
 * Copyright (c) 2024 Contributors to Eclipse Foundation.
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
package org.eclipse.ee4j.tck.faces.faces50.phaseEvents;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

/**
 * Given Arquilian has no single deployment testsuite
 * mechanism we have to rely on a third party library.
 * This improves the performance in a major area, namely
 * we are only deploying once and then run all tests
 * on the same deployment (cuts down by 95% the test time)
 */
@ArquillianSuiteDeployment
public class Deployments {
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return BaseITNG.createDeployment();
    }
}

