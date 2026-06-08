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

package ee.jakarta.tck.faces.faces23.ui_input;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec599IT extends BaseITNG {

    /**
     * ViewDeclarationLanguage#createComponent(context, taglibURI, tagName, rendererType) must return a
     * component with the renderer type associated with the given tag. Creating the "inputText" tag from
     * the jakarta.faces.html taglib must yield a component whose renderer type is "jakarta.faces.Text".
     *
     * @see jakarta.faces.view.ViewDeclarationLanguage#createComponent(jakarta.faces.context.FacesContext, String, String, java.util.Map)
     * @see https://github.com/jakartaee/faces/issues/599
     */
    @Test
    void testCreateComponent() {
        WebPage page = getPage("spec599.xhtml");
        assertTrue(page.containsText("SUCCESS"));
    }
}
