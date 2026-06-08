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

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.ApplicationConfigurationPopulator;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Spec533IT extends BaseITNG {

    /**
     * A {@link PhaseListener} programmatically registered into the application
     * configuration by an {@link ApplicationConfigurationPopulator} must fire
     * during the lifecycle.
     *
     * @see ApplicationConfigurationPopulator
     * @see https://github.com/jakartaee/faces/issues/533
     */
    @Test
    void testConfigurationEffective() throws Exception {
        WebPage page = getPage("spec533.xhtml");
        assertTrue(page.containsText("MyPhaseListener called"));
    }
}
