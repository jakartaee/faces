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
package ee.jakarta.tck.faces.faces50.facelets;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec2247IT extends BaseITNG {

    /**
     * The attributes supplied where a tag file is used are the parameters of that invocation: a tag file invoked from within it does not inherit them, and a
     * name the enclosing invocation supplied resolves to nothing rather than to the enclosing value.
     *
     * @see https://github.com/jakartaee/faces/issues/2247
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3263
     */
    @Test
    void testNestedInvocationDoesNotInheritTheAttributesOfTheEnclosingOne() {
        WebPage page = getPage("spec2247.xhtml");

        assertEquals(
            "(A:(:))", page.findElement(By.id("nestedInvocation")).getText(),
            "the nested invocation supplies no attribute, so its own #{attribute} resolves to nothing"
        );
    }

    /**
     * The body of an invocation is markup of the page which wrote it, not of the tag file inserting it, so it keeps resolving names against the scope that page
     * is in: a name the enclosing invocation supplied holds its value in the body where the tag file inserting it resolves the same name to nothing. This holds
     * for a nameless insertion and for a named one, and for markup evaluated while the view is built as much as for a value expression evaluated when it
     * renders.
     *
     * @see https://github.com/jakartaee/faces/issues/2247
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6027
     */
    @Test
    void testInsertedBodyResolvesTheParametersOfTheInvocationItWasWrittenIn() {
        WebPage page = getPage("spec2247.xhtml");

        assertEquals(
            "[A|(I::BA/CA:NA)]", page.findElement(By.id("insertedBody")).getText(),
            "the body written at the nested invocation resolves #{attribute} to the value the enclosing invocation was given"
        );
    }

    /**
     * A ui:param written in the body of an invocation reaches the markup it is written among, wherever the tag file inserts it, as it does on a ui:include or a
     * ui:decorate.
     *
     * @see https://github.com/jakartaee/faces/issues/2247
     * @see https://github.com/eclipse-ee4j/mojarra/issues/6027
     */
    @Test
    void testParameterWrittenInAnInsertedBodyReachesThatBody() {
        WebPage page = getPage("spec2247.xhtml");

        assertEquals(
            "(I::BP:)", page.findElement(By.id("parameterInInsertedBody")).getText(),
            "the body resolves the ui:param it carries"
        );
    }

    /**
     * A variable a tag file sets itself does not outlive the invocation, whether it is set by a scopeless c:set or by a ui:param written in the body of the
     * invocation, and the same holds for a composite component usage.
     *
     * @see https://github.com/jakartaee/faces/issues/2247
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3263
     */
    @Test
    void testWhatIsSetInsideAnInvocationDoesNotOutliveIt() {
        WebPage page = getPage("spec2247.xhtml");

        assertAll(
            () -> assertEquals(
                "SET[]", page.findElement(By.id("variableEscape")).getText(),
                "a variable the tag file sets with c:set"
            ),
            () -> assertEquals(
                "IN()[]", page.findElement(By.id("parameterEscape")).getText(),
                "a ui:param written in the body of the invocation, which reaches the tag file itself either"
            ),
            () -> assertEquals(
                "(A::[body])[]", page.findElement(By.id("compositeParameterEscape")).getText(),
                "a ui:param written in the body of a composite component usage, which reaches neither its implementation nor the page"
            )
        );
    }

    /**
     * A c:set carrying an explicit scope writes to that scope rather than to the variable mapper, so isolating the parameters of an invocation does not hide it
     * from the tag file.
     *
     * @see https://github.com/jakartaee/faces/issues/2247
     */
    @Test
    void testScopedVariablesRemainVisibleInsideATagFile() {
        WebPage page = getPage("spec2247.xhtml");

        assertEquals(
            "[APP|SES|VIEW|REQ]", page.findElement(By.id("scopedVariables")).getText(),
            "the application, session, view and request scopes are reached by the scoped attribute EL resolver from anywhere"
        );
    }

}
