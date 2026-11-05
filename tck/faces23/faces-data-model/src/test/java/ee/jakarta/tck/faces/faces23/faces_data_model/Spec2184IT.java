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

package ee.jakarta.tck.faces.faces23.faces_data_model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * {@link jakarta.faces.application.Application#createDataModel(jakarta.faces.context.FacesContext, Object)} returns the most specific {@code @FacesDataModel}
 * DataModel, and the components which adapt their value into a DataModel obtain it from there.
 */
class Spec2184IT extends BaseITNG {

    /**
     * A type without its own DataModel gets the DataModel of its closest registered superclass, wrapping the given value.
     *
     * @see jakarta.faces.application.Application#createDataModel(jakarta.faces.context.FacesContext, Object)
     * @see https://github.com/jakartaee/faces/issues/2184
     */
    @Test
    void createDataModelReturnsMostSpecificModel() {
        WebPage page = getPage("spec2184.xhtml");

        assertEquals("Child11Model", page.findElement(By.id("mostSpecificModel")).getText());
        assertEquals("true", page.findElement(By.id("mostSpecificModelWrapsValue")).getText());
    }

    /**
     * A type which no registered DataModel can wrap yields <code>null</code>.
     *
     * @see jakarta.faces.application.Application#createDataModel(jakarta.faces.context.FacesContext, Object)
     * @see https://github.com/jakartaee/faces/issues/2184
     */
    @Test
    void createDataModelReturnsNullForUnregisteredType() {
        WebPage page = getPage("spec2184.xhtml");

        assertEquals("true", page.findElement(By.id("unregisteredModelNull")).getText());
    }

    /**
     * h:dataTable adapts its value with the DataModel of a decorated Application.
     *
     * @see jakarta.faces.component.UIData#getValue()
     * @see https://github.com/jakartaee/faces/issues/2184
     */
    @Test
    void dataTableObtainsModelFromApplication() {
        assertEquals(List.of("decorated1", "decorated2"), rows("tableRow"));
    }

    /**
     * ui:repeat adapts its value with the DataModel of a decorated Application.
     *
     * @see "The value attribute of the ui:repeat tag"
     * @see https://github.com/jakartaee/faces/issues/2184
     */
    @Test
    void repeatObtainsModelFromApplication() {
        assertEquals(List.of("decorated1", "decorated2"), rows("repeatRow"));
    }

    private List<String> rows(String className) {
        WebPage page = getPage("spec2184.xhtml");
        return page.findElements(By.className(className)).stream().map(WebElement::getText).toList();
    }

}
