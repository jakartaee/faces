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
package ee.jakarta.tck.faces.test.faces50.facesConfig;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;

/**
 * https://github.com/eclipse-ee4j/mojarra/issues/5511
 */
public class Issue5511IT extends BaseITNG {

    @FindBy(id = "exampleFacesComponent")
    private WebElement exampleFacesComponent;

    @Test
    public void testJarWithMetadataCompleteFalse() {
        getPage("issue5511-using-jar-with-metadata-complete-false.xhtml");
        assertEquals("The @FacesComponent annotation SHOULD be processed because of metadata-complete=false on its JAR", "span", exampleFacesComponent.getTagName().toLowerCase());
        assertEquals("Because it renders a span via HtmlOutputText, it should output its value as well.", "Hello World", exampleFacesComponent.getText());
    }

    @Test
    public void testJarWithMetadataCompleteTrue() {
        getPage("issue5511-using-jar-with-metadata-complete-true.xhtml");
        assertEquals("The @FacesComponent annotation SHOULD NOT be processed because of metadata-complete=true on its JAR", "ex:examplefacescomponent", exampleFacesComponent.getTagName().toLowerCase());
        assertEquals("Because it does not render to a valid HTML element, it should not output anything either.", "", exampleFacesComponent.getText());
    }
}
