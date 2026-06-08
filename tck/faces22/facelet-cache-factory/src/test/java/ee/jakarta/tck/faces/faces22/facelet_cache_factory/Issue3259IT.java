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

package ee.jakarta.tck.faces.faces22.facelet_cache_factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.view.facelets.FaceletCacheFactory;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3259IT extends BaseITNG {

    /**
     * Verifies that a custom {@link FaceletCacheFactory} registered via {@code facelet-cache-factory} is invoked through
     * its decorating constructor.
     *
     * @see FaceletCacheFactory
     * @see jakarta.faces.view.facelets.FaceletCacheFactory#FaceletCacheFactory(jakarta.faces.view.facelets.FaceletCacheFactory)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3259
     */
    @Test
    void customFaceletCacheFactoryIsInvoked() throws Exception {
        WebPage page = getPage("issue3259.xhtml");
        assertEquals("SUCCESS", page.findElement(By.id("result")).getText());
    }
}
