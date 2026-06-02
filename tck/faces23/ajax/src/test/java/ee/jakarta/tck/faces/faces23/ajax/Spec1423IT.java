/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.faces23.ajax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIViewRoot;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec1423IT extends BaseITNG {

	/**
	 * @see UIViewRoot#addComponentResource(jakarta.faces.context.FacesContext,
	 *      jakarta.faces.component.UIComponent, String)
	 * @see https://github.com/jakartaee/faces/issues/1423
	 */
	@Test
	void spec1423Basic() throws Exception {
		WebPage page = getPage("spec1423.xhtml");
		WebElement button;

		assertTrue(page.findElement(By.id("scriptResult")).getText().isEmpty());
		assertEquals(NO_BACKGROUND_COLOR, backgroundColor(page));

		button = page.findElement(By.id("form1:addViaHead"));
		page.guardAjax(button::click);
		assertEquals("addedViaHead", page.findElement(By.id("scriptResult")).getText().trim());
		assertEquals(NO_BACKGROUND_COLOR, backgroundColor(page));
	}

	/**
	 * @see UIViewRoot#addComponentResource(jakarta.faces.context.FacesContext,
	 *      jakarta.faces.component.UIComponent, String)
	 * @see https://github.com/jakartaee/faces/issues/1423
	 */
	@Test
	void spec1423() throws Exception {
		WebPage page = getPage("spec1423.xhtml");
		WebElement button;

		assertTrue(page.findElement(By.id("scriptResult")).getText().isEmpty());
		assertEquals(NO_BACKGROUND_COLOR, backgroundColor(page));

		button = page.findElement(By.id("form1:addViaHead"));
		page.guardAjax(button::click);

		waitForScriptAndStylesheetResult(page, "addedViaHead", NO_BACKGROUND_COLOR);
		assertEquals("addedViaHead", page.findElement(By.id("scriptResult")).getText().trim());
		assertEquals(NO_BACKGROUND_COLOR, backgroundColor(page));

		button = page.findElement(By.id("form2:addViaInclude"));
		page.guardAjax(button::click);

		waitForScriptAndStylesheetResult(page, "addedViaInclude", "rgb(255, 0, 0)");

		button = page.findElement(By.id("form1:addViaBody"));
		page.guardAjax(button::click);

		waitForScriptAndStylesheetResult(page, "addedViaBody", "rgb(255, 0, 0)");

		button = page.findElement(By.id("form2:addViaInclude"));
		page.guardAjax(button::click);

		assertEquals("addedViaBody", page.findElement(By.id("scriptResult")).getText().trim());
		assertEquals("rgb(255, 0, 0)", backgroundColor(page));

		button = page.findElement(By.id("form1:addProgrammatically"));
		page.guardAjax(button::click);

		waitForScriptAndStylesheetResult(page, "addedProgrammatically", "rgb(0, 255, 0)");

		button = page.findElement(By.id("form1:addViaHead"));
		page.guardAjax(button::click);

		assertEquals("rgb(0, 255, 0)", backgroundColor(page));
		assertEquals("addedProgrammatically", page.findElement(By.id("scriptResult")).getText().trim());

		button = page.findElement(By.id("form1:addViaBody"));
		page.guardAjax(button::click);
		assertEquals("addedProgrammatically", page.findElement(By.id("scriptResult")).getText().trim());
		assertEquals("rgb(0, 255, 0)", backgroundColor(page));

		button = page.findElement(By.id("form2:addViaInclude"));
		page.guardAjax(button::click);
		assertEquals("addedProgrammatically", page.findElement(By.id("scriptResult")).getText().trim());
		assertEquals("rgb(0, 255, 0)", backgroundColor(page));
	}
	
	// Computed background-color of an unstyled body in Chrome (i.e. no stylesheet resource applied yet).
	private static final String NO_BACKGROUND_COLOR = "rgba(0, 0, 0, 0)";

	// guardAjax returns once the partial response has been processed and the new <script>/<link>
	// resource tags have been inserted into the DOM, but the browser still has to fetch, parse and
	// apply them asynchronously before the script result and the matching stylesheet take effect.
	// The injected <link> loads over the network, so its rules only enter the CSSOM some frames later;
	// read the live computed style on every poll until both the script and the stylesheet have settled.
	private void waitForScriptAndStylesheetResult(WebPage page, String expectedScriptResult, String expectedBackgroundColor) {
		page.waitForCondition(wd -> page.findElement(By.id("scriptResult")).getText().trim().equals(expectedScriptResult)
				&& backgroundColor(page).equals(expectedBackgroundColor));
	}

	private static String backgroundColor(WebPage page) {
		return String.valueOf(page.executeScript("return window.getComputedStyle(document.body).getPropertyValue('background-color')"));
	}

}
