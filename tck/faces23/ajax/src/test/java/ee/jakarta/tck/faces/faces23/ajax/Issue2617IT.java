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

package ee.jakarta.tck.faces.faces23.ajax;

import static java.util.stream.Collectors.toCollection;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2617IT extends BaseITNG {

    /**
     * faces.getViewState collects and encodes the form controls of the given form together with the view
     * state, and nothing else: markup which is not a form control contributes nothing, and an unchecked
     * checkbox is left out while a checked one and a select with a selected option are included.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2617
     */
    @Test
    void getViewStateCollectsExactlyTheFormControls() {
        WebPage page = getPage("issue2617.xhtml");

        String viewState = (String) page.executeScript(
                "return faces.getViewState(document.getElementById('form'));");

        assertEquals(Set.of("checked", "email", "form", "jakarta.faces.ViewState", "name", "select", "tel"),
                parameterNames(viewState),
                "faces.getViewState must collect exactly the form controls plus the view state.");
    }

    private static Set<String> parameterNames(String viewState) {
        return Arrays.stream(viewState.split("&"))
                .map(parameter -> parameter.split("=", 2)[0])
                .map(name -> URLDecoder.decode(name, StandardCharsets.UTF_8))
                .collect(toCollection(TreeSet::new));
    }
}
