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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * The build which restores a postback reproduces the view that was submitted rather than the one the current state of the model asks for. Every view here
 * guards its input with a build time condition over a request scoped flag which the action of the previous request set, so the condition holds while the
 * response is rendered and no longer holds while the postback is restored.
 *
 * <p>
 * The value submitted for the guarded component must therefore reach the model, and the re-apply of the facelet which precedes rendering must evaluate the
 * condition again, so that the response no longer holds the component.
 *
 * @see https://github.com/eclipse-ee4j/mojarra/issues/5961
 */
class Issue5961IT extends BaseITNG {

    @FindBy(id = "form:show")
    private WebElement show;

    @FindBy(id = "form:input")
    private WebElement input;

    @FindBy(id = "form:inputA")
    private WebElement inputA;

    @FindBy(id = "form:input0")
    private WebElement firstInput;

    @FindBy(id = "form:input1")
    private WebElement secondInput;

    @FindBy(id = "form:submit")
    private WebElement submit;

    @FindBy(id = "form:ajaxSubmit")
    private WebElement ajaxSubmit;

    @FindBy(id = "form:value")
    private WebElement value;

    @FindBy(id = "form:otherValue")
    private WebElement otherValue;

    @FindBy(id = "form:values")
    private WebElement values;

    @FindBy(id = "form:names")
    private WebElement names;

    @FindBy(id = "form:setNames")
    private WebElement setNames;

    @FindBy(id = "form:entries")
    private WebElement entries;

    @FindBy(id = "form:numbers")
    private WebElement numbers;

    @FindBy(id = "form:row0")
    private WebElement firstRow;

    /**
     * A value submitted for an input the response held inside a <code>c:if</code> reaches the model, even though the test no longer holds while the postback is
     * restored.
     */
    @Test
    void testConditional() {
        var page = getPage("issue5961-conditional.xhtml");
        page.guardHttp(show::click);
        input.sendKeys("test");
        page.guardHttp(submit::click);
        assertEquals("test", value.getText());
        assertFalse(page.getSource().contains("form:input"), "the re-apply which precedes rendering drops the input");
    }

    /**
     * An ajax postback restores its view the same way, so a value submitted for an input the response held inside a <code>c:if</code> reaches the model there
     * as well.
     */
    @Test
    void testConditionalAjax() {
        var page = getPage("issue5961-conditional.xhtml");
        page.guardHttp(show::click);
        input.sendKeys("test");
        page.guardAjax(ajaxSubmit::click);
        assertEquals("test", value.getText());
    }

    /**
     * A value submitted for an input the response held inside one <code>c:when</code> reaches the property that branch binds, rather than the property of the
     * branch the restore would otherwise have taken. Both branches carry the same component id, which is what makes this the quietest variant: nothing but the
     * tag tells them apart.
     */
    @Test
    void testChoose() {
        var page = getPage("issue5961-choose.xhtml");
        page.guardHttp(show::click);
        input.sendKeys("test");
        page.guardHttp(submit::click);
        assertEquals("test", value.getText());
        assertEquals("", otherValue.getText());
    }

    /**
     * The values submitted for the rows a <code>c:forEach</code> over a build time range rendered reach the model, even though the range no longer covers them
     * while the postback is restored.
     */
    @Test
    void testIteration() {
        var page = getPage("issue5961-iteration.xhtml");
        page.guardHttp(show::click);
        firstInput.sendKeys("first");
        secondInput.sendKeys("second");
        page.guardHttp(submit::click);
        assertEquals("{0=first, 1=second}", values.getText());
    }

    /**
     * The values submitted for the rows a <code>c:forEach</code> over a map rendered reach the entries they were rendered for, since the key of each of those
     * rows is saved beside the range: an entry is reached by key rather than by position, so neither the range nor the row count names the one a row belongs
     * to.
     */
    @Test
    void testIterationOverMap() {
        var page = getPage("issue5961-map.xhtml");
        page.guardHttp(show::click);
        firstInput.clear();
        firstInput.sendKeys("first");
        secondInput.clear();
        secondInput.sendKeys("second");
        page.guardHttp(submit::click);
        assertEquals("{first=first, second=second}", entries.getText());
    }

    /**
     * A map may be keyed by the very type an index has, so the rows of an iteration over one reach their entries by key rather than by position there as well.
     */
    @Test
    void testIterationOverMapKeyedByInteger() {
        var page = getPage("issue5961-numberedmap.xhtml");
        page.guardHttp(show::click);
        firstInput.clear();
        firstInput.sendKeys("first");
        secondInput.clear();
        secondInput.sendKeys("second");
        page.guardHttp(submit::click);
        assertEquals("{1=first, 2=second}", numbers.getText());
    }

    /**
     * The values submitted for the rows a <code>c:forEach</code> over a set rendered reach the model as well, each row reading its own element live by
     * position, which is the only thing a set offers to reach an element by. The rows a range skipped are read past rather than counted twice, so the last row
     * of the iteration reads its own element rather than none at all.
     */
    @Test
    void testIterationOverSet() {
        var page = getPage("issue5961-set.xhtml");
        page.guardHttp(show::click);
        firstInput.clear();
        firstInput.sendKeys("first");
        secondInput.clear();
        secondInput.sendKeys("second");
        page.guardHttp(submit::click);
        assertEquals("a,first,second", setNames.getText());
    }

    /**
     * A value submitted for an input whose id an expression decided reaches the model, even though that expression yields another id while the postback is
     * restored, and the response which follows holds the id the model asks for now.
     */
    @Test
    void testDynamicId() {
        var page = getPage("issue5961-dynamicid.xhtml");
        page.guardHttp(show::click);
        inputA.sendKeys("test");
        page.guardHttp(submit::click);
        assertEquals("test", value.getText());
        assertTrue(page.getSource().contains("form:inputB"), "the re-apply which precedes rendering renames the input");
    }

    /**
     * Keys which cannot be saved with the state leave the rows they were rendered for unreproducible, which is reported rather than failing the build the
     * response is rendered by.
     */
    @Test
    void testIterationOverUnsavableKeys() {
        var page = getPage("issue5961-unsavablekeys.xhtml");
        firstInput.sendKeys("first");
        secondInput.sendKeys("second");
        page.guardHttp(submit::click);
        assertEquals("{0=first, 1=second}", values.getText());
    }

    /**
     * A step which does not advance walks the items one element at a time, which the restore of a postback counts the same way rather than dividing by it.
     */
    @Test
    void testIterationOverNonAdvancingStep() {
        var page = getPage("issue5961-zerostep.xhtml");
        input.sendKeys("test");
        page.guardHttp(submit::click);
        assertEquals("test", value.getText());
        assertTrue(page.getSource().contains("[a][b]"), "every row reads the element the walk stands on");
    }

    /**
     * A row of a map still names the entry it was rendered for once that entry is gone, so what the rest of the row reads the model by is the key it was
     * rendered for rather than no key at all.
     */
    @Test
    void testIterationOverVanishedEntry() {
        var page = getPage("issue5961-vanishedentry.xhtml");
        page.guardHttp(show::click);
        firstInput.clear();
        firstInput.sendKeys("first");
        secondInput.sendKeys("second");
        page.guardHttp(submit::click);
        assertEquals("{first=first, second=second}", entries.getText());
    }

    /**
     * Items which are gone are not items which hand out their elements another way: the rows a map iteration rendered are reproduced over them all the same, so
     * a value submitted for one of them still reaches the model, and each row reads the stand-in for the entry it no longer reaches.
     */
    @Test
    void testIterationOverVanishedEntries() {
        var page = getPage("issue5961-vanishedentries.xhtml");
        page.guardHttp(show::click);
        firstInput.sendKeys("first");
        secondInput.sendKeys("second");
        page.guardHttp(submit::click);
        assertEquals("{0=first, 1=second}", values.getText());
    }

    /**
     * Items which hand out their elements by key where the rows of this view were rendered over them by position supply none of those rows, so the re-apply
     * which precedes rendering builds the iteration the items now ask for.
     */
    @Test
    void testIterationOverItemsSwitchedBack() {
        var page = getPage("issue5961-switchedbackitems.xhtml");
        page.guardHttp(show::click);
        page.guardHttp(submit::click);
        assertFalse(firstRow.getText().isEmpty(), "the rows read the entries the items hold rather than a stand-in");
    }

    /**
     * Items which are no longer the map whose keys an iteration reproduces its rows from supply none of those rows, so the re-apply which precedes rendering
     * builds the iteration the items now ask for, and the postback after that one reproduces what that re-apply rendered rather than the keys again.
     */
    @Test
    void testIterationOverSwitchedItems() {
        var page = getPage("issue5961-switcheditems.xhtml");
        page.guardHttp(show::click);
        page.guardHttp(submit::click);
        assertEquals("a", firstRow.getText(), "the re-apply which precedes rendering builds the items as they are");
        firstInput.sendKeys("first");
        secondInput.sendKeys("second");
        page.guardHttp(submit::click);
        assertEquals("{0=first, 1=second}", values.getText());
    }

    /**
     * The elements of an iteration are not saved, only the range, the row count and, over a map, the keys, since each row reads its own element live. An
     * iteration whose items no longer hold the elements its rows were rendered for reproduces those rows all the same, each reading a stand-in for the element
     * that is gone: the postback completes rather than failing on a row whose var resolves to nothing, a warning reports the rows it happened to, and the
     * values submitted for them reach that stand-in rather than the model.
     */
    @Test
    void testIterationOverVanishedItems() {
        var page = getPage("issue5961-vanisheditems.xhtml");
        page.guardHttp(show::click);
        firstInput.sendKeys("first");
        secondInput.sendKeys("second");
        page.guardHttp(submit::click);
        assertEquals("a,b", names.getText());
        assertFalse(page.getSource().contains("form:input0"), "the re-apply which precedes rendering drops the rows");
    }

    /**
     * A decision is keyed by the id its tag generates for the build, which an iteration generates anew per round, so a <code>c:if</code> nested in a
     * <code>c:forEach</code> replays the value it had in its own round.
     */
    @Test
    void testNestedInIteration() {
        var page = getPage("issue5961-nested.xhtml");
        page.guardHttp(show::click);
        firstInput.sendKeys("first");
        secondInput.sendKeys("second");
        page.guardHttp(submit::click);
        assertEquals("{0=first, 1=second}", values.getText());
    }

    /**
     * A value submitted for an input the response held in a <code>ui:include</code> reaches the model, even though the path resolves to another facelet while
     * the postback is restored.
     */
    @Test
    void testInclude() {
        var page = getPage("issue5961-include.xhtml");
        page.guardHttp(show::click);
        assertTrue(page.getSource().contains("form:input"));
        input.sendKeys("test");
        page.guardHttp(submit::click);
        assertEquals("test", value.getText());
    }

}
