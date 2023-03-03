/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.test.util.selenium;

import org.openqa.selenium.*;

import java.util.List;

/**
 * Helper to provide a set value and get value functionality
 * on top of Selenium functionality. Something Selenium has omitted functionality wise
 * (similar to what Select as Utils class does)
 */
public class ExtendedTextInput implements WebElement {
    WebElement delegate;
    ExtendedWebDriver webDriver;

    public ExtendedTextInput(ExtendedWebDriver webDriver, WebElement delegate) {
        this.delegate = delegate;
        this.webDriver = webDriver;
    }

    public void setValue(String value) {
        webDriver.getJSExecutor().executeScript("arguments[0].value='"+value+"'", delegate);
    }

    public void fireEvent(String eventName) {
        webDriver.getJSExecutor().executeScript("var __evt__ =  new Event('"+eventName+"', {bubbles: true});" +
                "arguments[0].dispatchEvent(__evt__)", delegate);
    }

    public String getValue() {
        return delegate.getAttribute("value");
    }

    public void click() {
        delegate.click();
    }

    public void submit() {
        delegate.submit();
    }

    public void sendKeys(CharSequence... keysToSend) {
        delegate.sendKeys(keysToSend);
    }

    public void clear() {
        delegate.clear();
    }

    public String getTagName() {
        return delegate.getTagName();
    }

    public String getDomProperty(String name) {
        return delegate.getDomProperty(name);
    }

    public String getDomAttribute(String name) {
        return delegate.getDomAttribute(name);
    }

    public String getAttribute(String name) {
        return delegate.getAttribute(name);
    }

    public String getAriaRole() {
        return delegate.getAriaRole();
    }

    public String getAccessibleName() {
        return delegate.getAccessibleName();
    }

    public boolean isSelected() {
        return delegate.isSelected();
    }

    public boolean isEnabled() {
        return delegate.isEnabled();
    }

    public String getText() {
        return delegate.getText();
    }

    public List<WebElement> findElements(By by) {
        return delegate.findElements(by);
    }

    public WebElement findElement(By by) {
        return delegate.findElement(by);
    }

    public SearchContext getShadowRoot() {
        return delegate.getShadowRoot();
    }

    public boolean isDisplayed() {
        return delegate.isDisplayed();
    }

    public Point getLocation() {
        return delegate.getLocation();
    }

    public Dimension getSize() {
        return delegate.getSize();
    }

    public Rectangle getRect() {
        return delegate.getRect();
    }

    public String getCssValue(String propertyName) {
        return delegate.getCssValue(propertyName);
    }

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return delegate.getScreenshotAs(target);
    }
}
