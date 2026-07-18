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
package ee.jakarta.tck.faces.faces20.composite.retargeting;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.view.ActionSource2AttachedObjectTarget;
import jakarta.faces.view.AttachedObjectTarget;
import jakarta.faces.view.EditableValueHolderAttachedObjectTarget;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class CompositeComponentsIT extends BaseITNG {

    private static final String NESTING05 = "retargeting/compositecomponents-nesting05.xhtml";
    private static final String NESTING06 = "retargeting/compositecomponents-nesting06.xhtml";
    private static final String VALIDATOR = "retargeting/compositecomponents-validator.xhtml";
    private static final String CONVERTER = "retargeting/compositecomponents-converter.xhtml";
    private static final String ACTIONSOURCE = "retargeting/compositecomponents-actionsource.xhtml";

    /**
     * Method-expression attributes on a composite component nested one level deep must be
     * retargeted to the intended inner action, action listener, custom action, validator and
     * value-change listener.
     *
     * @see AttachedObjectTarget
     */
    @Test
    void testNesting05() {
        assertNestedHooks("nesting6:nesting7");
    }

    /**
     * Same retargeting as {@link #testNesting05()} but through an additional composite nesting
     * level (nesting10 wrapping nesting6 wrapping nesting7).
     *
     * @see AttachedObjectTarget
     */
    @Test
    void testNesting08() {
        assertNestedHooks("nesting10:nesting6:nesting7");
    }

    private void assertNestedHooks(String prefix) {
        String view = pageFor(prefix);
        clickAndExpect(view, prefix + ":form1:command", "Action invoked");
        clickAndExpect(view, prefix + ":form2:command2", "ActionListener invoked");
        clickAndExpect(view, prefix + ":form3:command3", "Custom action invoked");

        WebPage page = getPage(view);
        page.findElement(By.id(prefix + ":form4:input")).sendKeys("foo");
        page.guardHttp(page.findElement(By.id(prefix + ":form4:command"))::click);
        assertTrue(page.containsText("validator invoked"), "Expected validator invoked for " + prefix);

        page = getPage(view);
        page.findElement(By.id(prefix + ":form5:input2")).sendKeys("changed");
        page.guardHttp(page.findElement(By.id(prefix + ":form5:command"))::click);
        assertTrue(page.containsText("ValueChange invoked"), "Expected ValueChange invoked for " + prefix);
    }

    private String pageFor(String prefix) {
        return prefix.startsWith("nesting10") ? NESTING06 : NESTING05;
    }

    /**
     * A validator attached on the using page via {@code f:validator} with only a {@code name} must
     * be retargeted to the composite's single editable value holder.
     *
     * @see EditableValueHolderAttachedObjectTarget
     */
    @Test
    void testValidator1() {
        clickAndExpect(VALIDATOR, "form:s1", "Validator Invoked");
    }

    /**
     * A validator attached via {@code f:validator} where the editable value holder declares a
     * {@code targets} must be retargeted to that target.
     *
     * @see EditableValueHolderAttachedObjectTarget
     */
    @Test
    void testValidator2() {
        clickAndExpect(VALIDATOR, "form2:s2", "Validator Invoked");
    }

    /**
     * A validator attached to a composite that nests another composite must be retargeted through
     * the nesting to the innermost editable value holder.
     *
     * @see EditableValueHolderAttachedObjectTarget
     */
    @Test
    void testValidator3() {
        clickAndExpect(VALIDATOR, "form3:s3", "Validator Invoked");
    }

    /**
     * A validator must be retargeted into an editable value holder that lives inside a nested
     * naming container of the composite.
     *
     * @see EditableValueHolderAttachedObjectTarget
     */
    @Test
    void testValidator4() {
        clickAndExpect(VALIDATOR, "form4:s4", "Validator Invoked");
    }

    /**
     * An action listener attached via {@code f:actionListener} with only a {@code name} must be
     * retargeted to the composite's single action source.
     *
     * @see ActionSource2AttachedObjectTarget
     */
    @Test
    void testActionSource1() {
        clickAndExpect(ACTIONSOURCE, "form:actionsource1:command", "Action Invoked");
    }

    /**
     * An action listener attached where the action source declares a {@code targets} must be
     * retargeted to that target.
     *
     * @see ActionSource2AttachedObjectTarget
     */
    @Test
    void testActionSource2() {
        clickAndExpect(ACTIONSOURCE, "form:actionsource2:ac2", "Action Invoked");
    }

    /**
     * An action listener attached to a composite that nests another composite must be retargeted
     * through the nesting to the innermost action source.
     *
     * @see ActionSource2AttachedObjectTarget
     */
    @Test
    void testActionSource3() {
        clickAndExpect(ACTIONSOURCE, "form:actionsource3:command:command", "Action Invoked");
    }

    /**
     * An action listener must be retargeted into an action source that lives inside a nested
     * naming container of the composite.
     *
     * @see ActionSource2AttachedObjectTarget
     */
    @Test
    void testActionSource4() {
        clickAndExpect(ACTIONSOURCE, "form:actionsource4:naming:command", "Action Invoked");
    }

    /**
     * Converters attached via {@code f:converter} must be retargeted to the composite's editable
     * value holders across all four variants (name-only, targets, nesting, nested naming
     * container); each retargeted converter runs on render and reports its client id.
     *
     * @see EditableValueHolderAttachedObjectTarget
     */
    @Test
    void testConverters() {
        WebPage page = getPage(CONVERTER);
        assertTrue(page.containsText("Converter Invoked : form:converter1:input"));
        assertTrue(page.containsText("Converter Invoked : form2:converter2:it2"));
        assertTrue(page.containsText("Converter Invoked : form3:converter3:input:input"));
        assertTrue(page.containsText("Converter Invoked : form4:converter4:naming:input"));
    }

    private void clickAndExpect(String view, String buttonId, String expectedMessage) {
        WebPage page = getPage(view);
        page.guardHttp(page.findElement(By.id(buttonId))::click);
        assertTrue(page.containsText(expectedMessage),
                "Expected '" + expectedMessage + "' after clicking " + buttonId);
    }
}
