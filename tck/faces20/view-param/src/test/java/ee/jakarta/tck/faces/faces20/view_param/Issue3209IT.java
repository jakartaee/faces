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

package ee.jakarta.tck.faces.faces20.view_param;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.event.PreRenderViewEvent;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue3209IT extends BaseITNG {

    /**
     * PreRenderViewEvent is published for the view being rendered. Building an h:link with includeViewParams reads the target view's metadata, but that must
     * not run the target view's preRenderView listener: a postback on the source view leaves it uninvoked.
     *
     * @see PreRenderViewEvent
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3209
     */
    @Test
    void postbackDoesNotRunThePreRenderViewListenerOfTheLinkTarget() {
        WebPage page = getPage("issue3209-first.xhtml?id=11111");
        int before = count(page);

        page.guardHttp(page.findElement(By.id("postbackButton"))::click);

        assertEquals(
            before, count(page),
            "A postback on the source view must not run the target view's preRenderView listener."
        );

        page = getPage("issue3209-second.xhtml?id=22222");

        assertEquals(
            before + 1, count(page),
            "Rendering the target view itself must run its preRenderView listener exactly once."
        );
    }

    private static int count(WebPage page) {
        return Integer.parseInt(page.findElement(By.id("targetListenerCount")).getText());
    }

}
