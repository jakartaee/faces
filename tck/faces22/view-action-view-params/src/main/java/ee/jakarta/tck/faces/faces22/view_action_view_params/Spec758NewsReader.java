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

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Reads the story addressed by the {@code id} view parameter. Request scoped on purpose: the
 * selected story is derived from the view parameter of the request at hand, so it is exactly what a
 * redirect does or does not carry over which decides whether a story is still selected afterwards.
 */
@Named
@RequestScoped
public class Spec758NewsReader {

    static final String MISSING_ID_MESSAGE = "You did not specify a headline. (The id parameter is missing)";
    static final String NON_POSITIVE_ID_MESSAGE = "Invalid headline. (The id parameter is not a positive number)";
    static final String UNKNOWN_ID_MESSAGE = "The headline you requested does not exist.";

    @Inject
    private Spec758NewsIndex newsIndex;

    private Spec758NewsStory selectedStory;

    private Long selectedStoryId;

    /**
     * Invoked by the view action of the story view, after the view parameter has updated the model.
     */
    public void loadStory() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.isValidationFailed()) {
            return;
        }

        Spec758NewsStory story = newsIndex.getStory(selectedStoryId);

        if (story != null) {
            selectedStory = story;
        }
        else {
            context.addMessage(null, new FacesMessage(UNKNOWN_ID_MESSAGE));
        }
    }

    /**
     * Invoked by the view action of the story view during process validations, so that a rejected
     * view parameter navigates back to the home view carrying the validation message along.
     */
    public String goToHomeIfValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed() ? "/spec758-home" : null;
    }

    public List<Spec758NewsStory> getStories() {
        return newsIndex.getStories();
    }

    public Spec758NewsStory getSelectedStory() {
        return selectedStory;
    }

    public Long getSelectedStoryId() {
        return selectedStoryId;
    }

    public void setSelectedStoryId(Long selectedStoryId) {
        this.selectedStoryId = selectedStoryId;
    }

    public boolean isMissingStoryId() {
        return selectedStoryId == null;
    }
}
