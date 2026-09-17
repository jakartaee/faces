/*
 * Copyright (c) Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces50.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Issue6010IT extends BaseITNG {

    @FindBy(id = "form:outer:inner:label")
    private WebElement label;

    @FindBy(id = "form:count")
    private WebElement count;

    /**
     * A composite component may aim a cc:clientBehavior at another composite component, which aims its own at a ClientBehaviorHolder of its implementation. The
     * behavior the page attaches to the outermost composite component must travel down that chain and end up on that holder, so that the view builds and
     * clicking the holder runs the behavior, on the first request as well as on the postbacks rebuilding the view.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6010
     */
    @Test
    void testNestedCompositeComponentClientBehavior() {
        var page = getPage("issue6010.xhtml");
        assertEquals("0", count.getText());

        page.guardAjax(label::click);
        assertEquals("1", count.getText());

        page.guardAjax(label::click);
        assertEquals("2", count.getText());
    }

}
