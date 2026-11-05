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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The runtime makes the collection of {@code @FacesDataModel} DataModels available as a CDI bean named
 * {@link jakarta.faces.model.FacesDataModel#DATA_MODEL_CLASSES_MAP_NAME}, a {@link java.util.Map} from wrapped class to DataModel class which iterates
 * subclasses before their superclasses.
 */
class Spec2184IT extends BaseITNG {

    /**
     * Every DataModel of this module is registered under the class it wraps.
     *
     * @see jakarta.faces.model.FacesDataModel#DATA_MODEL_CLASSES_MAP_NAME
     * @see https://github.com/jakartaee/faces/issues/2184
     */
    @Test
    void registryBeanMapsWrappedClassToDataModelClass() {
        Set<String> expected = Set.of("Parent=ParentModel", "Child1=Child1Model", "Child11=Child11Model", "Child2=Child2Model", "Child21=Child21Model");
        List<String> entries = entries();

        assertEquals(expected, Set.copyOf(entries), "entries: " + entries);
        assertEquals(expected.size(), entries.size(), "entries: " + entries);
    }

    /**
     * Iterating the registry bean yields a subclass before its superclass.
     *
     * @see jakarta.faces.model.FacesDataModel#DATA_MODEL_CLASSES_MAP_NAME
     * @see https://github.com/jakartaee/faces/issues/2184
     */
    @Test
    void registryBeanIteratesSubclassesBeforeSuperclasses() {
        List<String> entries = entries();

        assertBefore(entries, "Child11=Child11Model", "Child1=Child1Model");
        assertBefore(entries, "Child1=Child1Model", "Parent=ParentModel");
        assertBefore(entries, "Child21=Child21Model", "Child2=Child2Model");
        assertBefore(entries, "Child2=Child2Model", "Parent=ParentModel");
    }

    private List<String> entries() {
        WebPage page = getPage("spec2184.xhtml");
        return page.findElements(By.className("entry")).stream().map(WebElement::getText).toList();
    }

    private static void assertBefore(List<String> entries, String subclassEntry, String superclassEntry) {
        int subclassIndex = entries.indexOf(subclassEntry);
        assertTrue(
            subclassIndex >= 0 && subclassIndex < entries.indexOf(superclassEntry), subclassEntry + " must precede " + superclassEntry + " in " + entries
        );
    }

}
