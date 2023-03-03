/*
 * Copyright (c) 2021 Contributors to Eclipse Foundation.
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
// Original code stemming 100% from me, hence relicense from EPL
package ee.jakarta.tck.faces.test.util.selenium;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * Standard Arquilian runner, which triggers the
 * default Arquilian/HTMLUnit based test System if
 * test.selenium is not set as system property
 */
public class BaseArquilianRunner extends Arquillian {
    public BaseArquilianRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected boolean isIgnored(FrameworkMethod child) {
        if("true".equals(System.getProperty("test.selenium"))) {
            return true;
        }
        return super.isIgnored(child);
    }

}
