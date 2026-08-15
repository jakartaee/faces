/*
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

package jakarta.faces.view;

import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;

/**
 * <p class="changed_added_2_0">
 * <span class="changed_modified_2_2">Encapsulate</span> the saving and restoring of the view to enable the VDL to take
 * over the responsibility for handling this feature.
 * </p>
 *
 * <p class="changed_added_2_2">
 * Implementations must call {@link jakarta.faces.component.UIComponent#visitTree} on the
 * {@link jakarta.faces.component.UIViewRoot} to perform the saving and restoring of the view in the {@link #saveView}
 * and {@link #restoreView} methods, respectively.
 * </p>
 *
 * @since 2.0
 */
public abstract class StateManagementStrategy {

    /**
     * <p class="changed_added_2_0">
     * <span class="changed_modified_2_2">Return</span> the state of the current view in an <code>Object</code> that
     * implements <code>Serializable</code> <span class="changed_modified_2_2">and can be passed to
     * <code>java.io.ObjectOutputStream.writeObject()</code> without causing a <code>java.io.NotSerializableException</code>
     * to be thrown.</span> The default implementation must perform the following algorithm or its semantic equivalent,
     * <span class="changed_modified_2_2">explicitly performing all the steps listed here.</span>
     * </p>
     *
     * <div class="changed_added_2_0">
     *
     * <ol>
     *
     * <li>
     * <p>
     * If the <code>UIViewRoot</code> of the current view is marked <code>transient</code>, return <code>null</code>
     * immediately.
     * </p>
     * </li>
     *
     * <li>
     * <p>
     * Traverse the view and verify that each of the client ids are unique. Throw <code>IllegalStateException</code> if more
     * than one client id are the same.
     * </p>
     * </li>
     *
     * <li>
     * <p>
     * Visit the tree using {@link jakarta.faces.component.UIComponent#visitTree}. For each node, call
     * {@link jakarta.faces.component.UIComponent#saveState}, saving the returned <code>Object</code> in a way such that it
     * can be restored given only its client id. <span class="changed_modified_5_0">Record, in addition, every component
     * that the application added to, removed from or moved within the view after the view was built, so that
     * {@link #restoreView} can reproduce those manipulations. For a move, record the new parent, the facet name if the
     * component is a facet, and the index among its siblings.</span>
     * </p>
     * </li>
     *
     * </ol>
     *
     * <p class="changed_added_5_0">
     * The manipulations must be recorded in the order in which the application performed them, and the recorded position
     * must travel with the manipulation rather than with the component it applies to. The view build recreates the
     * components it created at their declared position on every build, so information kept on such a component may be
     * lost before the manipulation is replayed.
     * </p>
     *
     * <p class="changed_added_5_0">
     * A component whose manipulation the view build performs again on the next request need not be recorded, since the
     * build reproduces it. This is the case for components added, removed or moved while the view is being built, for
     * example from a listener for {@link jakarta.faces.event.PostAddToViewEvent}.
     * </p>
     *
     * <p>
     * The implementation must ensure that the {@link jakarta.faces.component.UIComponent#saveState} method is called for
     * each node in the tree.
     * </p>
     *
     * <p>
     * The data structure used to save the state obtained by executing the above algorithm must be
     * <code>Serializable</code>, and all of the elements within the data structure must also be <code>Serializable</code>.
     * </p>
     *
     * </div>
     *
     * @param context the <code>FacesContext</code> for this request.
     *
     * @since 2.0
     *
     * @return the saved view state
     */
    public abstract Object saveView(FacesContext context);

    /**
     * <p class="changed_added_2_0">
     * <span class="changed_modified_2_2">Restore</span> the state of the view with information in the request. The default
     * implementation must perform the following algorithm or its semantic equivalent.
     * </p>
     *
     * <div class="changed_added_2_0">
     *
     * <ol>
     *
     * <li>
     *
     * <p class="changed_added_2_2">
     * As in the case of restore view on an initial request, the view metadata must be restored and properly handled as
     * well. Obtain the {@link ViewMetadata} for the current <code>viewId</code>, and from that call
     * {@link ViewMetadata#createMetadataView}. Store the resultant {@link UIViewRoot} in the {@link FacesContext}. Obtain
     * the state of the <code>UIViewRoot</code> from the state <code>Object</code> returned from
     * {@link jakarta.faces.render.ResponseStateManager#getState} and pass that to {@link UIViewRoot#restoreViewScopeState}.
     * </p>
     *
     *
     * <p>
     * Build the view from the markup. For all components in the view that do not have an explicitly assigned id in the
     * markup, the values of those ids must be the same as on an initial request for this view.
     * <span class="changed_modified_5_0">This view contains what the view build produces, which includes the components
     * added, removed or moved while the view was being built. It does not reflect the manipulations the application
     * performed after the view was built: components added afterwards are absent, components removed afterwards are
     * present, and components moved afterwards are at their declared position. All of these cases must be
     * handled.</span>
     * </p>
     *
     * <p class="changed_added_5_0">
     * What the build produces also follows from the build time conditions it evaluates: the test of a conditional, the
     * branch of a choice, the range or the items of an iteration, the path of a dynamic inclusion. Whether this build
     * evaluates such a condition against the current state of the model, or reproduces the value it had in the build
     * which rendered the view, is implementation dependent, and an application must not depend on either. It is
     * responsible for a build time condition evaluating over a value which survives the postback.
     * </p>
     *
     * <p class="changed_added_5_0">
     * A component this build does not produce is therefore not necessarily one the application removed. It may be one
     * whose build time condition no longer holds, in which case the state saved for it has no node to be restored into
     * and a value submitted for it is not decoded. Reproducing the view that was rendered is in particular not
     * required: an iteration over items the model no longer holds cannot be reproduced at all, since the components it
     * produced read their item from those items.
     * </p>
     *
     *
     * </li>
     *
     * <li>
     * <p>
     * Call {@link jakarta.faces.render.ResponseStateManager#getState} to obtain the data structure returned from the
     * previous call to {@link #saveView}.
     * </p>
     * </li>
     *
     * <li>
     * <p>
     * Visit the tree using {@link jakarta.faces.component.UIComponent#visitTree}. For each node, call
     * {@link jakarta.faces.component.UIComponent#restoreState}, passing the state saved corresponding to the current client
     * id.
     * </p>
     * </li>
     *
     * <li>
     * <p>
     * <span class="changed_modified_5_0">Replay the manipulations recorded by {@link #saveView}, in the order in which
     * the application performed them: ensure that removed components are removed, that added components are added, and
     * that moved components are at the recorded parent, facet name and index among their siblings. A component that the
     * view build already produced at its recorded position must not be added a second time.</span>
     * </p>
     * </li>
     *
     * </ol>
     *
     * <p>
     * The implementation must ensure that the {@link jakarta.faces.component.UIComponent#restoreState} method is called for
     * each node in the tree, except for those that were programmatically deleted on the previous run through the lifecycle.
     * </p>
     *
     * </div>
     *
     * @param context the <code>FacesContext</code> for this request
     *
     * @param viewId the view identifier for which the state should be restored
     *
     * @param renderKitId the render kit id for this state.
     *
     * @since 2.0
     *
     * @return the root of the restored view
     */
    public abstract UIViewRoot restoreView(FacesContext context, String viewId, String renderKitId);

}
