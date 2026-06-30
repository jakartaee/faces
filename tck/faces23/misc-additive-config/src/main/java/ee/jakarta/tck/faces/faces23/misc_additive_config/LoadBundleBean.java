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

package ee.jakarta.tck.faces.faces23.misc_additive_config;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class LoadBundleBean {

    private static final String BUNDLE_PREFIX = "ee.jakarta.tck.faces.faces23.misc_additive_config.";

    private String mylocale = "en";

    private String basename = BUNDLE_PREFIX + "GreetingA";

    public String getMylocale() {
        return mylocale;
    }

    public void changeMyLocale(String mylocale) {
        this.mylocale = mylocale;
    }

    public String getBasename() {
        return basename;
    }

    public void useA() {
        basename = BUNDLE_PREFIX + "GreetingA";
    }

    public void useB() {
        basename = BUNDLE_PREFIX + "GreetingB";
    }
}
