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

package ee.jakarta.tck.faces.faces50.ajax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The partial response renders into {@code jakarta.faces.Resource} only the component resources which were added to the view during the current request: a
 * named one when it is not rendered yet, and a nameless one always. So each is delivered to the client once.
 */
class Spec2243IT extends BaseITNG {

    private static final String INLINE_COUNT = "return window.spec2243InlineCount";
    private static final String NAMED_COUNT = "return window.spec2243NamedCount";
    private static final String BODY_COUNT = "return window.spec2243BodyCount";
    private static final String PAGE_COUNT = "return window.spec2243PageCount";
    private static final String PENDING_SCRIPT_LOADS = "return window.spec2243PendingScriptLoads || 0";
    private static final String RECORD_RESOURCE_UPDATES = "faces.ajax.addOnEvent(function(data) { if (data.status === 'success') {"
        + " window.spec2243ResourceUpdate = !!data.responseXML.querySelector('update[id=\"jakarta.faces.Resource\"]'); } });";
    private static final String TRACK_PENDING_SCRIPT_LOADS = "window.spec2243PendingScriptLoads = 0;"
        + " new MutationObserver(function(records) { records.forEach(function(record) { record.addedNodes.forEach(function(node) {"
        + " if (node.tagName === 'SCRIPT' && node.src.indexOf('spec2243') >= 0) { window.spec2243PendingScriptLoads++;"
        + " var done = function() { window.spec2243PendingScriptLoads--; }; node.addEventListener('load', done); node.addEventListener('error', done); }"
        + " }); }); }).observe(document, { childList: true, subtree: true });";

    @FindBy(id = "form:show")
    private WebElement show;

    @FindBy(id = "form:refresh")
    private WebElement refresh;

    @FindBy(id = "form:navigate")
    private WebElement navigate;

    @FindBy(id = "form:showWithBookmarkableURL")
    private WebElement showWithBookmarkableURL;

    @FindBy(id = "form:bookmarkableURL")
    private WebElement bookmarkableURL;

    /**
     * An inline script which is added during an ajax request runs once, and not again on the next ajax request.
     *
     * @see jakarta.faces.context.PartialViewContext#processPartial(jakarta.faces.event.PhaseId)
     * @see https://github.com/jakartaee/faces/issues/2243
     */
    @Test
    void inlineScriptAddedDuringAjaxRunsOnce() {
        WebPage page = getPageRecordingResourceUpdates();
        assertNull(page.executeScript(INLINE_COUNT));

        page.guardAjax(show::click);
        assertCount(page, INLINE_COUNT, 1);

        page.guardAjax(refresh::click);
        assertNoResourceUpdate(page);
        assertCount(page, INLINE_COUNT, 1);
    }

    /**
     * A named script which is added during an ajax request loads once, and not again on the next ajax request.
     *
     * @see jakarta.faces.context.PartialViewContext#processPartial(jakarta.faces.event.PhaseId)
     * @see https://github.com/jakartaee/faces/issues/2243
     */
    @Test
    void namedScriptAddedDuringAjaxLoadsOnce() {
        WebPage page = getPageRecordingResourceUpdates();
        assertNull(page.executeScript(NAMED_COUNT));

        page.guardAjax(show::click);
        assertCount(page, NAMED_COUNT, 1);

        page.guardAjax(refresh::click);
        assertNoResourceUpdate(page);
        assertCount(page, NAMED_COUNT, 1);
    }

    /**
     * A named script of the initial page does not run again on an ajax request.
     *
     * @see jakarta.faces.context.PartialViewContext#processPartial(jakarta.faces.event.PhaseId)
     * @see https://github.com/jakartaee/faces/issues/2243
     */
    @Test
    void namedScriptOfInitialPageDoesNotRunAgain() {
        WebPage page = getPageRecordingResourceUpdates();
        assertCount(page, BODY_COUNT, 1);

        page.guardAjax(refresh::click);
        assertNoResourceUpdate(page);
        assertCount(page, BODY_COUNT, 1);
    }

    /**
     * An inline script which is added during an ajax request runs, also when the request obtains a bookmarkable URL with the view parameters of another view,
     * for which the implementation may inspect the metadata of that view.
     *
     * @see jakarta.faces.context.PartialViewContext#processPartial(jakarta.faces.event.PhaseId)
     * @see https://github.com/jakartaee/faces/issues/2243
     */
    @Test
    void inlineScriptAddedDuringAjaxRunsAlsoWhenRequestObtainsBookmarkableURL() {
        WebPage page = getPageRecordingResourceUpdates();
        assertNull(page.executeScript(INLINE_COUNT));

        page.guardAjax(showWithBookmarkableURL::click);
        assertTrue(bookmarkableURL.getText().contains("param=spec2243"), bookmarkableURL.getText());
        assertCount(page, INLINE_COUNT, 1);
    }

    /**
     * An inline script of the view does not run again after an ajax request which navigates to a new instance of the same view.
     *
     * @see jakarta.faces.context.PartialViewContext#processPartial(jakarta.faces.event.PhaseId)
     * @see https://github.com/jakartaee/faces/issues/2243
     */
    @Test
    void inlineScriptOfViewDoesNotRunAgainAfterAjaxNavigationToSameView() {
        WebPage page = getPageRecordingResourceUpdates();
        assertCount(page, PAGE_COUNT, 1);

        page.guardAjax(navigate::click);
        assertNoResourceUpdate(page);
        assertCount(page, PAGE_COUNT, 1);
    }

    /**
     * A named stylesheet whose name carries a query string, which is added during an ajax request, applies, and is not rendered again on the next ajax request.
     *
     * @see jakarta.faces.context.PartialViewContext#processPartial(jakarta.faces.event.PhaseId)
     * @see https://github.com/jakartaee/faces/issues/2243
     * @see https://github.com/jakartaee/faces/issues/2242
     */
    @Test
    void namedStylesheetWithQueryStringAddedDuringAjaxAppliesOnce() {
        WebPage page = getPageRecordingResourceUpdates();

        page.guardAjax(show::click);
        page.waitForCondition(driver -> "rgb(0, 0, 255)".equals(color(page, "styled")));

        page.guardAjax(refresh::click);
        assertNoResourceUpdate(page);
    }

    /**
     * An inline stylesheet which is added during an ajax request applies, and is not rendered again on the next ajax request.
     *
     * @see jakarta.faces.context.PartialViewContext#processPartial(jakarta.faces.event.PhaseId)
     * @see https://github.com/jakartaee/faces/issues/2243
     */
    @Test
    void inlineStylesheetAddedDuringAjaxAppliesOnce() {
        WebPage page = getPageRecordingResourceUpdates();

        page.guardAjax(show::click);
        page.waitForCondition(driver -> "rgb(0, 128, 0)".equals(color(page, "inlineStyled")));

        page.guardAjax(refresh::click);
        assertNoResourceUpdate(page);
    }

    /**
     * Opens the page, records for each successful ajax response whether it has an <code>update</code> element with an identifier of
     * <code>jakarta.faces.Resource</code>, and tracks the number of external scripts which are added to the document and not loaded yet.
     */
    private WebPage getPageRecordingResourceUpdates() {
        WebPage page = getPage("spec2243.xhtml");
        page.executeScript(RECORD_RESOURCE_UPDATES);
        page.executeScript(TRACK_PENDING_SCRIPT_LOADS);
        return page;
    }

    private static void assertNoResourceUpdate(WebPage page) {
        assertEquals(Boolean.FALSE, page.executeScript("return window.spec2243ResourceUpdate"));
    }

    /**
     * Asserts the count once every external script which was added to the document has loaded, so that a script which is loaded twice is counted twice.
     */
    private static void assertCount(WebPage page, String script, long expected) {
        page.waitForCondition(driver -> page.executeScript(script) != null && ((Number) page.executeScript(PENDING_SCRIPT_LOADS)).longValue() == 0);
        assertEquals(expected, ((Number) page.executeScript(script)).longValue());
    }

    private static String color(WebPage page, String id) {
        return (String) page.executeScript("return getComputedStyle(document.getElementById(arguments[0])).color", id);
    }

}
