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

package ee.jakarta.tck.faces.faces23.resource_basic_in_jar;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * Resources packaged inside a resource library contract in a JAR
 * ({@code META-INF/contracts/<contract>/...}) must resolve: an {@code h:graphicImage} referencing
 * the contract renders a request path pointing at the resource and carrying the contract
 * ({@code con=<contract>}) parameter.
 */
public class Spec1338IT extends BaseITNG {

    private static final String CONTRACT = "resourcesInContractInJar";

    /**
     * Both images resolve from the contract in the JAR, each with a contract-qualified request path.
     *
     * @see jakarta.faces.application.ResourceHandler#createResource(String, String)
     * @see https://github.com/jakartaee/faces/issues/1338
     */
    @Test
    void testResourcesFound() {
        WebPage page = getPage("spec1338.xhtml");
        assertImageFromContract(page, "img01", "img01.gif");
        assertImageFromContract(page, "img02", "img02.gif");
    }

    private void assertImageFromContract(WebPage page, String id, String resourceName) {
        String src = page.findElement(By.id(id)).getAttribute("src");
        assertTrue(src.contains("jakarta.faces.resource/" + resourceName),
                id + " must point at the contract resource but was " + src);
        assertTrue(src.contains("con=" + CONTRACT),
                id + " must carry the contract parameter but was " + src);
    }
}
