/*
 * Copyright (c) Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GPL-2.0 with Classpath-exception-2.0 which
 * is available at https://openjdk.java.net/legal/gplv2+ce.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 or Apache-2.0
 */
package ee.jakarta.tck.faces.faces50.misc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * A {@code h:panelGroup} renders a wrapper element when it carries a renderable id, {@code style}, {@code styleClass},
 * any pass-through attribute, or an attached client behavior; {@code layout="block"} makes that wrapper a {@code div}
 * rather than a {@code span}, but does not by itself force a wrapper.
 *
 * @see https://github.com/jakartaee/faces/issues/2182
 */
class Spec2182IT extends BaseITNG {

    @Test
    void testWrapperGate() {
        String body = getResponseBody("spec2182.xhtml");
        assertAll(
            () -> assertTrue(body.contains("<div id=\"c1\"><i id=\"bare\">"), () -> "a bare panelGroup emits no wrapper, but got:\n" + body),
            () -> assertTrue(body.contains("<span data-test=\"pta\"><i id=\"pta\">"), () -> "a pass-through attribute forces a span wrapper carrying that attribute, but got:\n" + body),
            () -> assertTrue(body.contains("<span class=\"scmark\"><i id=\"sc\">"), () -> "styleClass forces a span wrapper, but got:\n" + body),
            () -> assertTrue(body.contains("<div class=\"dbmark\"><i id=\"db\">"), () -> "layout=block makes the wrapper a div, but got:\n" + body),
            () -> assertTrue(body.contains("<div id=\"c5\"><span "), () -> "an attached client behavior forces a span wrapper, but got:\n" + body),
            () -> assertTrue(body.contains("<div id=\"c6\"><i id=\"lb\">"), () -> "layout=block alone does not force a wrapper, but got:\n" + body)
        );
    }
}
