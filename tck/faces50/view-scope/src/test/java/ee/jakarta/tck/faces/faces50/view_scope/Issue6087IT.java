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
package ee.jakarta.tck.faces.faces50.view_scope;

import static java.net.URI.create;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.CookieManager;
import java.net.http.HttpClient;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

class Issue6087IT extends BaseITNG {

    /** Must exceed org.glassfish.mojarra.numberOfActiveViewMaps in web.xml, so that view maps get evicted. */
    private static final int VIEWS = 5;

    private static final String VIEW = "issue6087.xhtml";
    private static final String SESSION = "issue6087-session";

    /**
     * When more views are opened within the same session than the maximum number of active view maps, then the view scoped beans of every evicted view map must
     * be destroyed, and the remaining ones must be destroyed when the session is destroyed.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6087
     */
    @Test
    void evictedViewMapsMustHaveTheirViewScopedBeansDestroyed() throws Exception {
        HttpClient client = HttpClient.newBuilder().cookieHandler(new CookieManager()).build(); // Shares the JSESSIONID.
        int[] before = getCounts(client);

        for (int i = 0; i < VIEWS; i++) {
            getResponseBody(client, VIEW);
        }

        int[] after = getCounts(client);
        assertEquals(VIEWS, after[0] - before[0], "created");
        assertEquals(VIEWS, after[1] - before[1], "destroyed");
    }

    private int[] getCounts(HttpClient client) throws Exception {
        String[] counts = getResponseBody(client, SESSION).split(",");
        return new int[] { Integer.parseInt(counts[0]), Integer.parseInt(counts[1]) };
    }

    private String getResponseBody(HttpClient client, String resource) throws Exception {
        return client.send(newBuilder(create(webUrl + resource)).build(), ofString()).body();
    }

}
