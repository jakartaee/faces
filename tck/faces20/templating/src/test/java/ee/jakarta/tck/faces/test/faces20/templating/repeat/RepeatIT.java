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
package ee.jakarta.tck.faces.test.faces20.templating.repeat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class RepeatIT extends BaseITNG {

    @Test
    void templateUIRepeatVarTest() {
        WebPage page = getPage("faces/repeat/repeatVar.xhtml");

        List<String> expected = List.of(
            "Color: Red",
            "Color: Green",
            "Color: Blue",
            "Color: Violet",
            "Color: Pink");

        assertLabelsStartingWith(page, "Color:", expected);
    }

    @Test
    void templateUIRepeatOffsetTest() {
        WebPage page = getPage("faces/repeat/repeatOffset.xhtml");

        List<String> expected = List.of(
            "Color: Violet",
            "Color: Pink");

        assertLabelsStartingWith(page, "Color:", expected);
    }

    @Test
    void templateUIRepeatVarStatusTest() {
        WebPage page = getPage("faces/repeat/repeatVarStat.xhtml");

        assertLabelsStartingWith(page, "VSIndex",
            List.of("VSIndex: 0", "VSIndex: 1", "VSIndex: 2", "VSIndex: 3", "VSIndex: 4"));

        assertLabelsStartingWith(page, "VSFirst",
            List.of("VSFirst: true", "VSFirst: false", "VSFirst: false", "VSFirst: false", "VSFirst: false"));

        assertLabelsStartingWith(page, "VSLast",
            List.of("VSLast: false", "VSLast: false", "VSLast: false", "VSLast: false", "VSLast: true"));

        assertLabelsStartingWith(page, "VSStep",
            List.of("VSStep: 2", "VSStep: 2", "VSStep: 2"));

        assertLabelsStartingWith(page, "VSOdd",
            List.of("VSOdd: false", "VSOdd: true", "VSOdd: false", "VSOdd: true", "VSOdd: false"));

        assertLabelsStartingWith(page, "VSEven",
            List.of("VSEven: true", "VSEven: false", "VSEven: true", "VSEven: false", "VSEven: true"));
    }

    private void assertLabelsStartingWith(WebPage page, String prefix, List<String> expected) {
        List<String> actual = page.findElements(By.tagName("label")).stream()
            .map(WebElement::getText)
            .map(String::trim)
            .filter(text -> text.startsWith(prefix))
            .collect(Collectors.toCollection(ArrayList::new));

        assertEquals(expected.size(), actual.size(),
            "Unexpected number of <label> elements starting with '" + prefix + "'");
        for (int i = 0; i < expected.size(); i++) {
            assertTrue(actual.get(i).replaceAll("\\s+", " ").equals(expected.get(i)),
                "Expected '" + expected.get(i) + "' at index " + i + " but found '" + actual.get(i) + "'");
        }
    }
}
