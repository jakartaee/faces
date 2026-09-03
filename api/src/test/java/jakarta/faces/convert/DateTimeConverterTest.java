/*
 * Copyright (c) Oracle and/or its affiliates. All rights reserved.
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

package jakarta.faces.convert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import jakarta.faces.application.Application;
import jakarta.faces.component.UIPanel;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.opentest4j.TestAbortedException;

/**
 * The JUnit tests for the DateTimeConverter class.
 *
 * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/5399">GitHub issue #5399</a>
 */
public class DateTimeConverterTest {

    private FacesContext facesContext;
    private MockedStatic<FacesContext> facesContextStatic;
    private UIPanel component;

    @BeforeEach
    public void setUp() {
        facesContext = Mockito.mock(FacesContext.class);
        UIViewRoot viewRoot = Mockito.mock(UIViewRoot.class);
        Application application = Mockito.mock(Application.class);
        ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        Map<String, Object> applicationMap = new HashMap<>();

        Mockito.when(facesContext.getViewRoot()).thenReturn(viewRoot);
        Mockito.when(facesContext.getApplication()).thenReturn(application);
        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getApplicationMap()).thenReturn(applicationMap);
        Mockito.when(viewRoot.createUniqueId()).thenReturn("test");
        Mockito.when(viewRoot.getLocale()).thenReturn(Locale.US);

        facesContextStatic = Mockito.mockStatic(FacesContext.class);
        facesContextStatic.when(FacesContext::getCurrentInstance).thenReturn(facesContext);

        component = new UIPanel();
    }

    @AfterEach
    public void tearDown() {
        facesContextStatic.close();
    }

    /**
     * Test that localTime parsing accepts user input with regular space before AM/PM.
     *
     * On JDK 21+, DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM) with Locale.US produces a pattern that uses NNBSP (U+202F) between time and AM/PM
     * marker. Users naturally type regular spaces (U+0020), causing a parse failure.
     *
     * @see <a href="https://bugs.openjdk.org/browse/JDK-8324308">JDK-8324308</a>
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/5399">GitHub issue #5399</a>
     */
    @Test
    public void testLocalTimeParsingWithRegularSpaceBeforeAmPm() {
        requireNnbspInPattern(null, FormatStyle.MEDIUM);

        DateTimeConverter converter = new DateTimeConverter();
        converter.setType("localTime");
        converter.setLocale(Locale.US);

        // This is what a user would type: regular space (U+0020) before AM.
        String userInput = "10:30:00 AM";

        // This should succeed but currently throws ConverterException on JDK 21+
        // because the formatter expects NNBSP (U+202F) instead of regular space.
        Object result = converter.getAsObject(facesContext, component, userInput);

        assertNotNull(result, "Parsing '10:30:00 AM' with regular space should succeed");
        assertInstanceOf(LocalTime.class, result);
        assertEquals(LocalTime.of(10, 30, 0), result);
    }

    /**
     * Test that localTime parsing works with NNBSP (the JDK 21+ character). This verifies the formatter itself works — the input just uses the "right"
     * character.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/5399">GitHub issue #5399</a>
     */
    @Test
    public void testLocalTimeParsingWithNnbsp() {
        requireNnbspInPattern(null, FormatStyle.MEDIUM);

        DateTimeConverter converter = new DateTimeConverter();
        converter.setType("localTime");
        converter.setLocale(Locale.US);

        // Input with NNBSP — this should always work on JDK 21+.
        String inputWithNnbsp = "10:30:00\u202fAM";

        Object result = converter.getAsObject(facesContext, component, inputWithNnbsp);

        assertNotNull(result);
        assertInstanceOf(LocalTime.class, result);
        assertEquals(LocalTime.of(10, 30, 0), result);
    }

    /**
     * Test that localDateTime parsing accepts user input with regular space before AM/PM.
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/5399">GitHub issue #5399</a>
     */
    @Test
    public void testLocalDateTimeParsingWithRegularSpaceBeforeAmPm() {
        requireNnbspInPattern(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

        DateTimeConverter converter = new DateTimeConverter();
        converter.setType("localDateTime");
        converter.setLocale(Locale.US);

        // Format a known value to get the expected formatted string, then replace NNBSP with regular space
        // to simulate user input.
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withLocale(Locale.US);
        LocalDateTime testDateTime = LocalDateTime.of(2024, 3, 15, 10, 30, 0);
        String formatted = formatter.format(testDateTime);
        String userInput = formatted.replace('\u202f', ' ');

        Object result = converter.getAsObject(facesContext, component, userInput);

        assertNotNull(result, "Parsing localDateTime with regular space should succeed");
        assertInstanceOf(LocalDateTime.class, result);
        assertEquals(testDateTime, result);
    }

    /**
     * Test that getAsString and then getAsObject roundtrip works when the formatted output is manually typed by a user (i.e., NNBSP replaced with regular
     * space).
     *
     * @see <a href="https://github.com/eclipse-ee4j/mojarra/issues/5399">GitHub issue #5399</a>
     */
    @Test
    public void testLocalTimeRoundtripWithRegularSpace() {
        requireNnbspInPattern(null, FormatStyle.MEDIUM);

        DateTimeConverter converter = new DateTimeConverter();
        converter.setType("localTime");
        converter.setLocale(Locale.US);

        LocalTime originalTime = LocalTime.of(14, 45, 30);

        // getAsString produces formatted output (may contain NNBSP on JDK 21+)
        String formatted = converter.getAsString(facesContext, component, originalTime);
        assertNotNull(formatted);

        // Simulate a user copying the displayed value but the browser/OS normalizing NNBSP to regular space
        String userInput = formatted.replace('\u202f', ' ');

        // This roundtrip should work
        Object parsed = converter.getAsObject(facesContext, component, userInput);

        assertNotNull(parsed, "Roundtrip with regular space should succeed");
        assertEquals(originalTime, parsed);
    }

    /**
     * Test that type "instant" formats and parses using the ISO 8601 instant representation, which is what {@link Instant#toString()} and
     * {@link Instant#parse(CharSequence)} use.
     *
     * @see <a href="https://github.com/jakartaee/faces/issues/2211">GitHub issue #2211</a>
     */
    @Test
    public void testInstant() {
        DateTimeConverter converter = createConverter("instant");
        Instant instant = Instant.parse("2026-07-30T10:15:30Z");

        assertEquals("2026-07-30T10:15:30Z", converter.getAsString(facesContext, component, instant));
        assertEquals(instant, converter.getAsObject(facesContext, component, "2026-07-30T10:15:30Z"));
    }

    /**
     * Test that type "instant" resolves the date and time fields of an explicit pattern against the configured time zone, as an {@link Instant} does not carry
     * one by itself.
     *
     * @see <a href="https://github.com/jakartaee/faces/issues/2211">GitHub issue #2211</a>
     */
    @Test
    public void testInstantWithPatternAndTimeZone() {
        DateTimeConverter converter = createConverter("instant");
        converter.setPattern("uuuu-MM-dd HH:mm:ss");
        converter.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
        Instant instant = Instant.parse("2026-07-30T10:15:30Z");

        assertEquals("2026-07-30 12:15:30", converter.getAsString(facesContext, component, instant));
        assertEquals(instant, converter.getAsObject(facesContext, component, "2026-07-30 12:15:30"));
    }

    /**
     * Test that type "year" formats and parses using the ISO 8601 year representation, which is what {@link Year#toString()} and
     * {@link Year#parse(CharSequence)} use.
     *
     * @see <a href="https://github.com/jakartaee/faces/issues/2211">GitHub issue #2211</a>
     */
    @Test
    public void testYear() {
        DateTimeConverter converter = createConverter("year");

        assertEquals("2026", converter.getAsString(facesContext, component, Year.of(2026)));
        assertEquals(Year.of(2026), converter.getAsObject(facesContext, component, "2026"));
    }

    /**
     * Test that type "yearMonth" formats and parses using the ISO 8601 year-month representation, which is what {@link YearMonth#toString()} and
     * {@link YearMonth#parse(CharSequence)} use.
     *
     * @see <a href="https://github.com/jakartaee/faces/issues/2211">GitHub issue #2211</a>
     */
    @Test
    public void testYearMonth() {
        DateTimeConverter converter = createConverter("yearMonth");

        assertEquals("2026-07", converter.getAsString(facesContext, component, YearMonth.of(2026, 7)));
        assertEquals(YearMonth.of(2026, 7), converter.getAsObject(facesContext, component, "2026-07"));
    }

    /**
     * Test that type "monthDay" formats and parses using the ISO 8601 month-day representation, which is what {@link MonthDay#toString()} and
     * {@link MonthDay#parse(CharSequence)} use.
     *
     * @see <a href="https://github.com/jakartaee/faces/issues/2211">GitHub issue #2211</a>
     */
    @Test
    public void testMonthDay() {
        DateTimeConverter converter = createConverter("monthDay");

        assertEquals("--07-30", converter.getAsString(facesContext, component, MonthDay.of(7, 30)));
        assertEquals(MonthDay.of(7, 30), converter.getAsObject(facesContext, component, "--07-30"));
    }

    /**
     * Test that type "monthDay" accepts February 29, which is valid without a year.
     *
     * @see <a href="https://github.com/jakartaee/faces/issues/2211">GitHub issue #2211</a>
     */
    @Test
    public void testMonthDayAcceptsLeapDay() {
        DateTimeConverter converter = createConverter("monthDay");

        assertEquals(MonthDay.of(2, 29), converter.getAsObject(facesContext, component, "--02-29"));
    }

    /**
     * Test that the types "year", "yearMonth" and "monthDay" honor an explicit pattern.
     *
     * @see <a href="https://github.com/jakartaee/faces/issues/2211">GitHub issue #2211</a>
     */
    @Test
    public void testPattern() {
        assertPattern("year", "'FY'uuuu", Year.of(2026), "FY2026");
        assertPattern("yearMonth", "MM/uuuu", YearMonth.of(2026, 7), "07/2026");
        assertPattern("monthDay", "dd-MM", MonthDay.of(7, 30), "30-07");
    }

    private void assertPattern(String type, String pattern, Object value, String formatted) {
        DateTimeConverter converter = createConverter(type);
        converter.setPattern(pattern);

        assertEquals(formatted, converter.getAsString(facesContext, component, value));
        assertEquals(value, converter.getAsObject(facesContext, component, formatted));
    }

    /**
     * Test that unparseable input for the new types results in a ConverterException rather than in a silent null, which is what happens when a type is not
     * covered by the message selection in {@link DateTimeConverter#getAsObject(FacesContext, jakarta.faces.component.UIComponent, String)}.
     *
     * @see <a href="https://github.com/jakartaee/faces/issues/2211">GitHub issue #2211</a>
     */
    @Test
    public void testUnparseableValueThrowsConverterException() {
        assertUnparseable("instant", "2026-07-30");
        assertUnparseable("year", "MMXXVI");
        assertUnparseable("yearMonth", "2026-13");
        assertUnparseable("monthDay", "--02-30");
    }

    private void assertUnparseable(String type, String value) {
        DateTimeConverter converter = createConverter(type);
        assertThrows(
            ConverterException.class, () -> converter.getAsObject(facesContext, component, value),
            () -> "Type " + type + " must reject '" + value + '\''
        );
    }

    private static DateTimeConverter createConverter(String type) {
        DateTimeConverter converter = new DateTimeConverter();
        converter.setType(type);
        converter.setLocale(Locale.US);
        return converter;
    }

    private static void requireNnbspInPattern(FormatStyle dateStyle, FormatStyle timeStyle) {
        String localizedPattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(
            dateStyle, timeStyle, IsoChronology.INSTANCE, Locale.US
        );
        if (!localizedPattern.contains("\u202f")) {
            throw new TestAbortedException("JDK 21+ required: localized pattern does not contain NNBSP");
        }
    }

}
