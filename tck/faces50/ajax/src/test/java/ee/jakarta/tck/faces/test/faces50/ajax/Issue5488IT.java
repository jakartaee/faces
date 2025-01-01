/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.faces50.ajax;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

class Issue5488IT extends BaseITNG {

    @FindBy(id = "form1:input")
    private WebElement form1input;

    @FindBy(id = "form1:button")
    private WebElement form1button;

    @FindBy(id = "form1:messages")
    private WebElement form1messages;

    @FindBy(id = "form2:input")
    private WebElement form2input;

    @FindBy(id = "form2:link")
    private WebElement form2link;

    @FindBy(id = "form2:messages")
    private WebElement form2messages;

    @FindBy(id = "form3:button1")
    private WebElement form3button1;

    @FindBy(id = "form3:button2")
    private WebElement form3button2;

    @FindBy(id = "form3:messages")
    private WebElement form3messages;

  /**
   * https://github.com/eclipse-ee4j/mojarra/issues/5488
   */
  @Test
  void commandButtonBlurred() {
        var page = getPage("issue5488.xhtml");
        form1input.sendKeys(Keys.TAB);
        page.guardAjax(() -> form1button.sendKeys(Keys.TAB));

        var messages = form1messages.getText();
        assertEquals("listener invoked on form1:button", messages); // and thus not action invoked as well
    }

  /**
   * https://github.com/eclipse-ee4j/mojarra/issues/5488
   */
  @Test
  void commandButtonClicked() {
        var page = getPage("issue5488.xhtml");
        page.guardAjax(form1button::click);
        assertEquals("action invoked on form1:button", form1messages.getText()); // and thus not listener invoked as well
    }

  /**
   * https://github.com/eclipse-ee4j/mojarra/issues/5488
   */
  @Test
  void commandLinkBlurred() {
        var page = getPage("issue5488.xhtml");
        form2input.sendKeys(Keys.TAB);
        page.guardAjax(() -> form2link.sendKeys(Keys.TAB));
        assertEquals("listener invoked on form2:link", form2messages.getText()); // and thus not action invoked as well
    }

  /**
   * https://github.com/eclipse-ee4j/mojarra/issues/5488
   */
  @Test
  void commandLinkClicked() {
        var page = getPage("issue5488.xhtml");
        page.guardAjax(form2link::click);
        assertEquals("action invoked on form2:link", form2messages.getText()); // and thus not listener invoked as well
    }

  /**
   * https://github.com/eclipse-ee4j/mojarra/issues/3355
   */
  @Test
  void plainButton1() {
        var page = getPage("issue5488.xhtml");
        page.guardAjax(form3button1::click);
        assertEquals("listener invoked on form3:button1", form3messages.getText()); // and thus not on form3:button2 as well
    }

  /**
   * https://github.com/eclipse-ee4j/mojarra/issues/3355
   */
  @Test
  void plainButton2() {
        var page = getPage("issue5488.xhtml");
        page.guardAjax(form3button2::click);
        assertEquals("listener invoked on form3:button2", form3messages.getText()); // and thus not on form3:button1 as well
    }
}
