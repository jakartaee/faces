/*
 * Copyright (c) 2021, 2022 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.faces23.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIWebsocket;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec1396IT extends BaseITNG {

	/**
	 * @see UIWebsocket
	 * @see https://github.com/jakartaee/faces/issues/1396
	 */
	@Test
	void enableWebsocketEndpoint() throws Exception {
		WebPage page = getPage("spec1396-1.xhtml");
		assertEquals("true", page.findElement(By.id("param")).getText());
	}

	/**
	 * @see UIWebsocket
	 * @see https://github.com/jakartaee/faces/issues/1396
	 */
	@Test
	void defaultWebsocket() throws Exception {
		WebPage page = getPage("spec1396-2.xhtml");

		String pageSource = page.getSource();
		assertTrue(pageSource.contains("faces.push.init("));
		assertTrue(pageSource.contains("/jakarta.faces.push/push?"));

		waitUntilWebsocketIsOpened(page);

		WebElement button = page.findElement(By.id("form:button"));
		button.click();

		waitUntilWebsocketIsPushed(page);
	}

	/**
	 * @see UIWebsocket#setUser(java.io.Serializable)
	 * @see https://github.com/jakartaee/faces/issues/1396
	 */
	@Test
	void userScopedWebsocket() throws Exception {
		WebPage page = getPage("spec1396-3.xhtml");

		String pageSource = page.getSource();
		assertTrue(pageSource.contains("faces.push.init("));
		assertTrue(pageSource.contains("/jakarta.faces.push/user?"));

		waitUntilWebsocketIsOpened(page);

		WebElement button = page.findElement(By.id("form:button"));
		button.click();

		waitUntilWebsocketIsPushed(page);
	}

	/**
	 * @see UIWebsocket#setScope(String)
	 * @see https://github.com/jakartaee/faces/issues/1396
	 */
	@Test
	void viewScopedWebsocket() throws Exception {
		WebPage page = getPage("spec1396-4.xhtml");

		String pageSource = page.getSource();
		assertTrue(pageSource.contains("faces.push.init("));
		assertTrue(pageSource.contains("/jakarta.faces.push/view?"));

		waitUntilWebsocketIsOpened(page);

		WebElement button = page.findElement(By.id("form:button"));
		button.click();

		waitUntilWebsocketIsPushed(page);
	}

	/**
	 * @see UIWebsocket
	 * @see https://github.com/eclipse-ee4j/mojarra/issues/4332
	 */
	@Test
	void websocketAfterPostback() throws Exception {
		WebPage page = getPage("spec1396-5.xhtml");

		String pageSource = page.getSource();
		assertTrue(pageSource.contains("faces.push.init("));
		assertTrue(pageSource.contains("/jakarta.faces.push/push?"));

		waitUntilWebsocketIsOpened(page);

		WebElement postback = page.findElement(By.id("form:postback"));
		page.guardHttp(postback::click);

		pageSource = page.getSource();
		assertTrue(pageSource.contains("faces.push.init("));
		assertTrue(pageSource.contains("/jakarta.faces.push/push?"));

		waitUntilWebsocketIsOpened(page);

		WebElement button = page.findElement(By.id("form:button"));
		button.click();

		waitUntilWebsocketIsPushed(page);
	}

	/**
	 * WebDriver doesn't expose WebSocket lifecycle events; the page's JS sets #opened on onopen, which we poll here.
	 */
	private static void waitUntilWebsocketIsOpened(WebPage page) throws Exception {
		page.waitForCondition($ -> "yes".equals(page.findElement(By.id("opened")).getText()));
	}

	/**
	 * WebDriver doesn't expose WebSocket message events; the page's JS sets #message on onmessage, which we poll here.
	 */
	private static void waitUntilWebsocketIsPushed(WebPage page) throws Exception {
		page.waitForCondition($ -> "pushed!".equals(page.findElement(By.id("message")).getText()));
	}

}
