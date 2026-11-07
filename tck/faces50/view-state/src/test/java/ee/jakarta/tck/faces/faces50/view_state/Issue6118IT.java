/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces50.view_state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue6118IT extends BaseITNG {

    @FindBy(id = "form:text")
    private WebElement text;

    @FindBy(id = "form:remove")
    private WebElement remove;

    @FindBy(id = "form:submit")
    private WebElement submit;

    /**
     * A component whose initial state was cleared is saved with its full state, and the <code>StateHolder</code> contract requires that
     * <code>restoreState()</code> restores the state as it was saved. An attribute which was removed before the save therefore stays removed, also when the
     * restoring build sets it again.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/6118">issue 6118</a>
     */
    @Test
    void testAttributeRemovedAfterClearInitialStateStaysRemoved() {
        WebPage page = getPage("issue6118.xhtml");
        assertEquals("[y]", text.getText(), "before remove");

        page.guardHttp(remove::click);
        assertEquals("[]", text.getText(), "after remove");

        page.guardHttp(submit::click);
        assertEquals("[]", text.getText(), "after the next postback");
    }

}
