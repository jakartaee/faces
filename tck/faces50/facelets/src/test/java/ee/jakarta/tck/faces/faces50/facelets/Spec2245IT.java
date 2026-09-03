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
package ee.jakarta.tck.faces.faces50.facelets;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec2245IT extends BaseITNG {

    private static final String TEXT_AROUND_CHILD = "before component after";

    /**
     * Whitespace between template text and a child which follows it is content and must reach the response. The amount is not pinned, only its presence, so an
     * implementation which compresses runs of whitespace stays conformant. This holds no matter whether the text is literal or carries an expression, whether
     * the child is a component or a tag handler, and which whitespace character separates them.
     *
     * @see jakarta.faces.view.facelets.TextHandler
     * @see https://github.com/jakartaee/faces/issues/2245
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5989
     */
    @Test
    void testWhitespaceBeforeChildIsPreserved() {
        WebPage page = getPage("spec2245.xhtml");

        assertAll(
            () -> assertEquals(
                TEXT_AROUND_CHILD, page.findElement(By.id("literalTextBeforeComponent")).getText(),
                "literal text before a component"
            ),
            () -> assertEquals(
                TEXT_AROUND_CHILD, page.findElement(By.id("expressionTextBeforeComponent")).getText(),
                "text carrying an expression before a component"
            ),
            () -> assertEquals(
                TEXT_AROUND_CHILD, page.findElement(By.id("literalTextBeforeTagHandler")).getText(),
                "literal text before a tag handler"
            ),
            () -> assertEquals(
                TEXT_AROUND_CHILD, page.findElement(By.id("tabBeforeComponent")).getText(),
                "tab before a component"
            ),
            () -> assertEquals(
                TEXT_AROUND_CHILD, page.findElement(By.id("newlineBeforeComponent")).getText(),
                "newline and indentation before a component"
            )
        );
    }

    /**
     * Text consisting solely of whitespace is not content and must be dropped in front of a child, because a text node becomes a child of its own and layout
     * renderers count children positionally. A panel grid of two columns holding two children, indented over separate lines, therefore renders exactly one row
     * of two cells.
     *
     * @see jakarta.faces.view.facelets.TextHandler
     * @see https://github.com/jakartaee/faces/issues/2245
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5989
     */
    @Test
    void testWhitespaceOnlyTextBeforeChildIsDropped() {
        WebPage page = getPage("spec2245.xhtml");

        List<WebElement> rows = page.findElements(By.cssSelector("#whitespaceOnlyBetweenChildren tr"));
        List<WebElement> cells = page.findElements(By.cssSelector("#whitespaceOnlyBetweenChildren td"));

        assertEquals(1, rows.size(), "rendered rows");
        assertEquals(List.of("b", "c"), cells.stream().map(WebElement::getText).toList(), "rendered cells");
    }

}
