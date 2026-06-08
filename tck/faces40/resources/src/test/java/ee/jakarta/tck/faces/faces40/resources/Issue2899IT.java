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

package ee.jakarta.tck.faces.faces40.resources;

import static java.net.URI.create;
import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.http.HttpResponse;

import jakarta.faces.application.ResourceHandler;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue2899IT extends BaseITNG {

    /**
     * The {@link ResourceHandler} must honor a conditional request: a resource
     * request carrying an {@code If-Modified-Since} matching the resource's
     * {@code Last-Modified} must yield a 304 Not Modified.
     *
     * @see ResourceHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2899
     */
    @Test
    void testResourceCaching() throws Exception {
        WebPage page = getPage("issue2899.xhtml");
        WebElement link = page.findElement(By.cssSelector("link[rel='stylesheet']"));
        String cssUrl = link.getAttribute("href");

        HttpResponse<String> initial = newHttpClient().send(newBuilder(create(cssUrl)).build(), ofString());
        assertEquals(200, initial.statusCode());

        String lastModified = initial.headers().firstValue("Last-Modified")
                .orElseGet(() -> initial.headers().firstValue("Date").orElse(null));
        assertNotNull(lastModified, "Resource response must carry a Last-Modified (or Date) header");

        HttpResponse<String> conditional = newHttpClient().send(
                newBuilder(create(cssUrl))
                        .header("If-Modified-Since", lastModified)
                        .header("Cache-Control", "max-age=0")
                        .build(),
                ofString());
        assertEquals(304, conditional.statusCode());
    }
}
