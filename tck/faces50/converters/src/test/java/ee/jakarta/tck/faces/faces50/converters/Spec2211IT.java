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
package ee.jakarta.tck.faces.faces50.converters;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec2211IT extends BaseITNG {

    /**
     * Test that the types instant, year, yearMonth and monthDay roundtrip through their ISO 8601 representation when no
     * pattern has been specified.
     *
     * @see https://github.com/jakartaee/faces/issues/2211
     */
    @Test
    void testDefaultFormat() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("instant", "2026-07-30T10:15:30Z");
        values.put("year", "2026");
        values.put("yearMonth", "2026-07");
        values.put("monthDay", "--07-30");

        assertRoundtrip("formWithDefaultFormat", values);
    }

    /**
     * Test that the types instant, year, yearMonth and monthDay honor an explicit pattern, whereby the instant resolves
     * its date and time fields against the specified time zone.
     *
     * @see https://github.com/jakartaee/faces/issues/2211
     */
    @Test
    void testPattern() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("instant", "2026-07-30 12:15:30");
        values.put("year", "FY2026");
        values.put("yearMonth", "07/2026");
        values.put("monthDay", "30-07");

        assertRoundtrip("formWithPattern", values);
    }

    /**
     * Test that unparseable input results in a conversion error message instead of in a silently converted null.
     *
     * @see https://github.com/jakartaee/faces/issues/2211
     */
    @Test
    void testUnparseableValue() {
        assertUnparseable("instant", "2026-07-30");
        assertUnparseable("year", "MMXXVI");
        assertUnparseable("yearMonth", "2026-13");
        assertUnparseable("monthDay", "--02-30");
    }

    private void assertRoundtrip(String formId, Map<String, String> values) {
        WebPage page = getPage("spec2211.xhtml");
        assertEquals("", page.findElement(By.id(formId + ":messages")).getText());

        values.forEach((id, value) -> page.findElement(By.id(formId + ":" + id)).sendKeys(value));
        page.guardHttp(page.findElement(By.id(formId + ":submit"))::click);

        assertEquals("", page.findElement(By.id(formId + ":messages")).getText());
        assertAll(values.entrySet().stream().map(entry ->
            () -> assertEquals(entry.getValue(), page.findElement(By.id(formId + ":" + entry.getKey())).getDomProperty("value"))));
    }

    private void assertUnparseable(String id, String value) {
        String formId = "formWithDefaultFormat";
        WebPage page = getPage("spec2211.xhtml");
        page.findElement(By.id(formId + ":" + id)).sendKeys(value);
        page.guardHttp(page.findElement(By.id(formId + ":submit"))::click);

        assertNotEquals("", page.findElement(By.id(formId + ":messages")).getText(),
            "Type " + id + " must reject '" + value + "'");
    }
}
