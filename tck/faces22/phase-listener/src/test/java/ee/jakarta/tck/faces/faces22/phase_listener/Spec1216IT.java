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

package ee.jakarta.tck.faces.faces22.phase_listener;

import static java.net.URI.create;
import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.http.HttpResponse;
import java.util.List;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.event.PhaseListener;

class Spec1216IT extends BaseITNG {

    /**
     * Exceptions thrown by a faces-config.xml lifecycle PhaseListener and by an f:phaseListener registered on the
     * UIViewRoot must both reach the custom ExceptionHandler (which records each message in a custom response header).
     *
     * @see ExceptionHandler
     * @see PhaseListener
     * @see https://github.com/jakartaee/faces/issues/1216
     */
    @Test
    void testViewRootPhaseListener() throws Exception {
        HttpResponse<String> response = newHttpClient().send(
                newBuilder(create(webUrl + "spec1216ViewPhaseListener.xhtml")).build(), ofString());

        List<String> headers = response.headers().allValues(Spec1216ExceptionHandler.HEADER);
        assertEquals(
                List.of("Thrown from faces-config.xml PhaseListener",
                        "Thrown from faces-config.xml PhaseListener",
                        "Thrown from UIViewRoot PhaseListener"),
                headers);
    }
}
