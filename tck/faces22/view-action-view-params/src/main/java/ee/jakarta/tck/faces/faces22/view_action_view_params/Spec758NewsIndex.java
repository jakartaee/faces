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

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 * The fixed set of stories the news reader navigates between. The list order is the order in which
 * the home view lists them, so it must be stable.
 */
@Named
@ApplicationScoped
public class Spec758NewsIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Spec758NewsStory> stories = List.of(
        new Spec758NewsStory(1L, "Story 1 Headline", "Story 1 Content"),
        new Spec758NewsStory(2L, "Story 2 Headline", "Story 2 Content"));

    public List<Spec758NewsStory> getStories() {
        return stories;
    }

    public Spec758NewsStory getStory(Long id) {
        return stories.stream().filter(story -> story.getId().equals(id)).findFirst().orElse(null);
    }
}
