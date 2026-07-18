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

package ee.jakarta.tck.faces.faces22.composite_component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2566IT extends BaseITNG {

    /**
     * VDL#createComponent must also resolve a composite taglib URI, and must accept a populated
     * attributes map, returning the composite component instance.
     *
     * @see jakarta.faces.view.ViewDeclarationLanguage#createComponent(jakarta.faces.context.FacesContext, String, String, java.util.Map)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2566
     */
    @Test
    void createComponentResolvesCompositeTaglibUri() {
        WebPage page = getPage("issue2566.xhtml");
        assertEquals("SUCCESS", page.findElement(By.id("result")).getText());
    }
}
