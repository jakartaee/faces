/**
 * Copyright Werner Punz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Original code stemming 100% from me, hence relicense from EPL
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
