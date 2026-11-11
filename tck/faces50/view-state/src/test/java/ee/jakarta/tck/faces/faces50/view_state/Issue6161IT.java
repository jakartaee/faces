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
package ee.jakarta.tck.faces.faces50.view_state;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue6161IT extends BaseITNG {

    private static final List<String> INPUTS = List.of("form:plain", "form:composite:input", "form:table:0:input", "form:table:1:input");
    private static final List<String> BUTTONS = List.of("form:plainButton", "form:composite:button", "form:table:0:button", "form:table:1:button");

    /**
     * The <code>actionListener</code>, <code>valueChangeListener</code> and <code>validator</code> attributes are invoked on every postback, on a plain
     * component, on the target of a composite component attribute and in a row of a <code>h:dataTable</code> with <code>rowStatePreserved</code>. Their method
     * expressions come from the build, not from the view state.
     *
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6161
     */
    @Test
    void testListenersFireOnEveryPostback() {
        for (String button : BUTTONS) {
            WebPage page = getPage("issue6161.xhtml");

            for (String value : List.of("a", "b")) {
                for (String input : INPUTS) {
                    WebElement element = getWebDriver().findElement(By.id(input));
                    element.clear();
                    element.sendKeys(value);
                }

                page.guardHttp(getWebDriver().findElement(By.id(button))::click);
                assertEquals(expectedEvents(button), actualEvents(), "events after clicking " + button + " with value " + value);
            }
        }
    }

    private static String expectedEvents(String button) {
        return Stream.of(INPUTS.stream().map(input -> "validate:" + input), INPUTS.stream().map(input -> "change:" + input), Stream.of("action:" + button))
            .flatMap(stream -> stream)
            .sorted()
            .collect(joining(" "));
    }

    private String actualEvents() {
        return Arrays.stream(getWebDriver().findElement(By.id("form:events")).getText().split(" ")).sorted().collect(joining(" "));
    }

}
