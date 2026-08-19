/*
 * Copyright (c) Contributors to Eclipse Foundation.
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

package jakarta.faces.component.html;

import static jakarta.faces.component.html.HtmlEvents.ADDITIONAL_HTML_EVENT_NAMES_PARAM_NAME;
import static java.util.Comparator.naturalOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import jakarta.faces.context.CurrentFacesContext;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The HTML spec supports three sets of event handler content attributes: those which every element supports and which
 * fire on the element itself, those which every element supports but which are forwarded to the window object when
 * they are declared on the body element, and those which only the body element supports and which always fire on the
 * window object. A component representing the body element therefore exposes all three of them as behavior events,
 * while every other component exposes the first two.
 */
class HtmlEventsTest {

    private ExternalContext externalContext;

    @BeforeEach
    void setUpCurrentFacesContext() {
        externalContext = mock(ExternalContext.class);
        when(externalContext.getApplicationMap()).thenReturn(new HashMap<>());

        FacesContext facesContext = mock(FacesContext.class);
        when(facesContext.getExternalContext()).thenReturn(externalContext);

        CurrentFacesContext.set(facesContext);
    }

    @Test
    void bodyExposesElementAndBodyAndWindowEvents() {
        Collection<String> eventNames = new HtmlBody().getEventNames();

        assertTrue(eventNames.contains("click"));
        assertTrue(eventNames.contains("scrollend"));
        assertTrue(eventNames.contains("load"));
        assertTrue(eventNames.contains("unload"));
        assertTrue(eventNames.contains("pagehide"));
        assertEquals(HtmlEvents.HtmlElementEvent.values().length + HtmlEvents.HtmlBodyEvent.values().length + HtmlEvents.HtmlWindowEvent.values().length, eventNames.size());
    }

    @Test
    void otherComponentsExposeElementAndBodyEventsButNoWindowEvents() {
        Collection<String> eventNames = new HtmlPanelGroup().getEventNames();

        assertTrue(eventNames.contains("click"));
        assertTrue(eventNames.contains("scrollend"));
        assertTrue(eventNames.contains("load"));
        assertFalse(eventNames.contains("unload"));
        assertFalse(eventNames.contains("pagehide"));
    }

    @Test
    void windowEventNamesHoldTheWindowEventsOnly() {
        Collection<String> eventNames = HtmlEvents.getHtmlWindowEventNames(FacesContext.getCurrentInstance());

        assertTrue(eventNames.contains("unload"));
        assertTrue(eventNames.contains("pagehide"));
        assertFalse(eventNames.contains("click"));
        assertFalse(eventNames.contains("load"));
        assertEquals(HtmlEvents.HtmlWindowEvent.values().length, eventNames.size());
    }

    @Test
    void actionSourceAndEditableValueHolderComponentsExposeTheirComponentEvent() {
        Collection<String> commandEventNames = new HtmlCommandButton().getEventNames();
        Collection<String> inputEventNames = new HtmlInputText().getEventNames();

        assertTrue(commandEventNames.contains("action"));
        assertTrue(commandEventNames.contains("input"));
        assertFalse(commandEventNames.contains("valueChange"));

        assertTrue(inputEventNames.contains("valueChange"));
        assertTrue(inputEventNames.contains("input"));
        assertFalse(inputEventNames.contains("action"));
    }

    @Test
    void additionalEventNamesAreMergedAndContributeNoBlankName() {
        when(externalContext.getInitParameter(ADDITIONAL_HTML_EVENT_NAMES_PARAM_NAME)).thenReturn("  animationend \t transitionend  ");

        Collection<String> eventNames = new HtmlPanelGroup().getEventNames();

        assertTrue(eventNames.contains("animationend"));
        assertTrue(eventNames.contains("transitionend"));
        assertFalse(eventNames.contains(""));
        assertEquals(HtmlEvents.HtmlElementEvent.values().length + HtmlEvents.HtmlBodyEvent.values().length + 2, eventNames.size());
    }

    @Test
    void blankAdditionalEventNamesParamContributesNothing() {
        when(externalContext.getInitParameter(ADDITIONAL_HTML_EVENT_NAMES_PARAM_NAME)).thenReturn(" ");

        Collection<String> eventNames = new HtmlPanelGroup().getEventNames();

        assertFalse(eventNames.contains(""));
        assertEquals(HtmlEvents.HtmlElementEvent.values().length + HtmlEvents.HtmlBodyEvent.values().length, eventNames.size());
    }

    @Test
    void eventNamesAreSortedAndDistinct() {
        List<String> eventNames = new ArrayList<>(new HtmlBody().getEventNames());
        List<String> sortedAndDistinct = eventNames.stream().distinct().sorted(naturalOrder()).toList();

        assertEquals(sortedAndDistinct, eventNames);
    }
}
