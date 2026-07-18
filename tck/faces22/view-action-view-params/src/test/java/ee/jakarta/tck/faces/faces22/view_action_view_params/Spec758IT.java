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

package ee.jakarta.tck.faces.faces22.view_action_view_params;

import static ee.jakarta.tck.faces.faces22.view_action_view_params.Spec758NewsReader.MISSING_ID_MESSAGE;
import static ee.jakarta.tck.faces.faces22.view_action_view_params.Spec758NewsReader.NON_POSITIVE_ID_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import jakarta.faces.component.UIViewAction;
import jakarta.faces.component.UIViewParameter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Spec758IT extends BaseITNG {

    private static final String HOME_TITLE = "The big news stories of the day";

    // ------------------------------------------------------------------ view action outcomes

    /**
     * A view action whose outcome resolves to another view must navigate to it by redirecting, since
     * the request which triggered it is a plain GET and its URL would otherwise no longer match the
     * view being rendered.
     *
     * @see UIViewAction#broadcast(jakarta.faces.event.FacesEvent)
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @Test
    void redirectingViewActionSendsRedirect() {
        assertRedirectsTo("spec758-result.xhtml", "spec758-redirect.xhtml");

        WebPage page = getPage("spec758-redirect.xhtml");
        assertEquals("Result page", page.findElement(By.id("page")).getText(), "landed page");
    }

    /**
     * The empty outcome must not navigate, so the requested view renders itself and no redirect is
     * sent.
     *
     * @see UIViewAction#broadcast(jakarta.faces.event.FacesEvent)
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @Test
    void viewActionEmptyOutcomeDoesNotRedirect() {
        assertDoesNotRedirect("spec758-empty.xhtml");
        assertEquals("empty action", getPage("spec758-empty.xhtml").findElement(By.id("message")).getText(), "message");
    }

    /**
     * The null outcome must not navigate, so the requested view renders itself and no redirect is
     * sent.
     *
     * @see UIViewAction#broadcast(jakarta.faces.event.FacesEvent)
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @Test
    void viewActionNullOutcomeDoesNotRedirect() {
        assertDoesNotRedirect("spec758-null.xhtml");
        assertEquals("null action", getPage("spec758-null.xhtml").findElement(By.id("message")).getText(), "message");
    }

    /**
     * A view action navigating through a case which itself declares a redirect must redirect, even
     * where that target is the very view being requested. The view therefore never renders, hence
     * this only ever requests it without following the redirect.
     *
     * @see UIViewAction#broadcast(jakarta.faces.event.FacesEvent)
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @Test
    void viewActionExplicitRedirectNavigationCaseRedirects() {
        assertRedirectsTo("spec758-explicit-redirect.xhtml", "spec758-explicit-redirect.xhtml");
    }

    // ------------------------------------------------------------------ view action broadcasting

    /**
     * A view action must broadcast to every nested action listener, in declaration order, before it
     * navigates away.
     *
     * @see UIViewAction#addActionListener(jakarta.faces.event.ActionListener)
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @Test
    void viewActionActionListenersFireInOrder() {
        WebPage page = getPage("spec758-actionlistener.xhtml");
        assertEquals("Result page", page.findElement(By.id("page")).getText(), "landed page");
        assertEquals("1 viewAction1 2 viewAction1", page.findElement(By.id("result")).getText(), "trace");
    }

    /**
     * A view action must broadcast to the action listener named by its {@code actionListener}
     * attribute as well, before it navigates away.
     *
     * @see UIViewAction#setActionExpression(jakarta.el.MethodExpression)
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @Test
    void viewActionActionListenerMethodExpressionFires() {
        WebPage page = getPage("spec758-methodlistener.xhtml");
        assertEquals("Result page", page.findElement(By.id("page")).getText(), "landed page");
        assertEquals("method viewAction1", page.findElement(By.id("result")).getText(), "trace");
    }

    // ------------------------------------------------------------------ view action + view param flow

    /**
     * A postback which resubmits the story view must retain its view parameter, so the very same
     * story is loaded and rendered again.
     *
     * @see UIViewParameter
     * @see UIViewAction#isOnPostback()
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void refreshKeepsViewParams(int storyId) {
        WebPage page = openStory(storyId);

        page.guardHttp(page.findElement(By.id("form:refresh"))::click);
        assertStory(page, storyId);
    }

    /**
     * A postback which clears the required view parameter must fail validation, so the view action
     * scheduled for process validations navigates back to the home view, carrying the view
     * parameter's required message along.
     *
     * @see UIViewParameter#setRequiredMessage(String)
     * @see UIViewAction#setPhase(String)
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void refreshClearingParamShowsRequiredMessage(int storyId) {
        WebPage page = openStory(storyId);

        page.guardHttp(page.findElement(By.id("form:refreshClearParam"))::click);
        assertOnHome(page);
        assertEquals(MISSING_ID_MESSAGE, messages(page), "messages");
    }

    /**
     * A redirect after post back to the story view which includes the view parameters must retain
     * the story.
     *
     * @see UIViewParameter
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void redirectWithIncludeViewParamsKeepsStory(int storyId) {
        WebPage page = openStory(storyId);

        page.guardHttp(page.findElement(By.id("form:refreshWithRedirectParams"))::click);
        assertStory(page, storyId);
    }

    /**
     * A redirect after post back to the story view which does not include the view parameters must
     * lose the story, so the required view parameter fails validation and the view action navigates
     * back to the home view.
     *
     * @see UIViewParameter
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void redirectWithoutIncludeViewParamsLosesStory(int storyId) {
        WebPage page = openStory(storyId);

        page.guardHttp(page.findElement(By.id("form:refreshWithRedirect"))::click);
        assertOnHome(page);
        assertEquals(MISSING_ID_MESSAGE, messages(page), "messages");
    }

    /**
     * Navigating home without including the view parameters must land on a home view without any
     * message and without a selection.
     *
     * @see UIViewParameter
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void homeButtonShowsNoMessages(int storyId) {
        WebPage page = openStory(storyId);

        page.guardHttp(page.findElement(By.id("form:home"))::click);
        assertOnHome(page);
        assertEquals("", messages(page), "messages");
        assertEquals("", page.findElement(By.id("selection")).getText(), "selection");
    }

    /**
     * Navigating home through an implicit outcome which includes the view parameters must carry the
     * selection over to the home view.
     *
     * @see UIViewParameter#getStringValueFromModel(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void rememberedSelectionViaIncludeViewParams(int storyId) {
        WebPage page = openStory(storyId);

        page.guardHttp(page.findElement(By.id("form:homeRememberSelection"))::click);
        assertOnHome(page);
        assertEquals(String.valueOf(storyId), page.findElement(By.id("selection")).getText(), "selection");
    }

    /**
     * Navigating home through a navigation case which redirects including the view parameters must
     * carry the selection over to the home view.
     *
     * @see UIViewParameter#getStringValueFromModel(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void rememberedSelectionViaNavCase(int storyId) {
        WebPage page = openStory(storyId);

        page.guardHttp(page.findElement(By.id("form:homeRememberSelectionNavCase"))::click);
        assertOnHome(page);
        assertEquals(String.valueOf(storyId), page.findElement(By.id("selection")).getText(), "selection");
    }

    /**
     * Navigating to a third view through a navigation case which redirects including the view
     * parameters must carry the selection over to that view as well.
     *
     * @see UIViewParameter#getStringValueFromModel(jakarta.faces.context.FacesContext)
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void story2ViaNavCaseKeepsSelection(int storyId) {
        WebPage page = openStory(storyId);

        page.guardHttp(page.findElement(By.id("form:story2RememberSelectionNavCase"))::click);
        assertEquals("Story 2", page.findElement(By.id("title")).getText(), "title");
        assertEquals(String.valueOf(storyId), page.findElement(By.id("selection")).getText(), "selection");
    }

    /**
     * A view parameter rejected by its validator must yield that view parameter's validator message,
     * upon which the view action scheduled for process validations navigates back to the home view.
     *
     * @see UIViewParameter#setValidatorMessage(String)
     * @see https://github.com/jakartaee/faces/issues/758
     */
    @Test
    void viewParamValidatorMessageOnNonPositiveId() {
        WebPage page = getPage("spec758-story.xhtml?id=0");
        assertOnHome(page);
        assertEquals(NON_POSITIVE_ID_MESSAGE, messages(page), "messages");
    }

    // ------------------------------------------------------------------ helpers

    /**
     * Opens the home view and clicks the link of the given story, which is how the story view is
     * reached with its view parameter set.
     */
    private WebPage openStory(int storyId) {
        WebPage page = getPage("spec758-home.xhtml");
        assertOnHome(page);

        List<WebElement> links = page.getAnchors();
        page.guardHttp(links.get(storyId - 1)::click);
        assertStory(page, storyId);

        return page;
    }

    private static void assertStory(WebPage page, int storyId) {
        assertEquals("Story " + storyId + " Headline", page.findElement(By.id("headline")).getText(), "headline");
        assertEquals("Story " + storyId + " Content", page.findElement(By.id("content")).getText(), "content");
    }

    private static void assertOnHome(WebPage page) {
        assertEquals(HOME_TITLE, page.findElement(By.id("title")).getText(), "home title");
    }

    /**
     * The messages of a view are framework generated, so they cannot be pinned to an element of a
     * known value; they are however pinned to the id of the {@code h:messages} of the template, which
     * renders nothing at all when there are no messages.
     */
    private static String messages(WebPage page) {
        List<WebElement> messages = page.findElements(By.id("messages"));
        return messages.isEmpty() ? "" : messages.get(0).getText().trim();
    }

    private void assertRedirectsTo(String expectedView, String page) {
        String location = getResponseLocation(page);
        assertTrue(location != null && location.endsWith(expectedView),
            page + " expected to redirect to " + expectedView + " but Location was " + location);
    }

    private void assertDoesNotRedirect(String page) {
        assertNull(getResponseLocation(page), page + " expected not to redirect");
    }
}
