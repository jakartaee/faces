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

package ee.jakarta.tck.faces.faces22.phase_listener_default;

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

/**
 * This module declares no jakarta.faces.VIEWROOT_PHASE_LISTENER_QUEUES_EXCEPTIONS context-param, so it exercises the
 * spec default. The sibling faces22/phase-listener module pins the param to true and asserts the opposite outcome.
 */
class Spec1216IT extends BaseITNG {

    /**
     * By default an exception thrown by an f:phaseListener registered on the UIViewRoot must not be queued to the
     * ExceptionHandler, whereas an exception thrown by a faces-config.xml lifecycle PhaseListener still must be. The
     * custom ExceptionHandler records each exception it receives in a custom response header.
     *
     * @see ExceptionHandler
     * @see PhaseListener
     * @see https://github.com/jakartaee/faces/issues/1216
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4058
     */
    @Test
    void testViewRootPhaseListenerExceptionsNotQueuedByDefault() throws Exception {
        HttpResponse<String> response = newHttpClient().send(
                newBuilder(create(webUrl + "spec1216ViewPhaseListener.xhtml")).build(), ofString());

        List<String> headers = response.headers().allValues(Spec1216ExceptionHandler.HEADER);
        assertEquals(
                List.of("Thrown from faces-config.xml PhaseListener",
                        "Thrown from faces-config.xml PhaseListener"),
                headers);
    }
}
