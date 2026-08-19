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
 * <p class="changed_added_5_0">
 * The <em>rendering build</em> is the build which precedes Render Response and produces the view that
 * {@link #saveView} saves. The <em>restoring build</em> is the build which {@link #restoreView} performs to recover
 * that view on the next request.
 * </p>
 *
 * <p class="changed_added_5_0">
 * What a build produces follows in part from the <em>build time conditions</em> it evaluates, and the value such a
 * condition produces is its <em>decision</em>. The build time conditions the implementation itself provides are the
 * test of a conditional, the branch of a choice, the range or the items of an iteration, and the path of a dynamic
 * inclusion. For these, the rendering build evaluates the condition and the restoring build reproduces the decision
 * the rendering build reached.
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
     * can be restored given only its client id. <span class="changed_modified_5_0">Record, in addition, each addition,
     * removal and move the application performed after the view was built, so that {@link #restoreView} can reproduce
     * it. A move records the new parent, the facet name if the component is a facet, and the index among its siblings.
     * An addition records the added component along with its state, since the restoring build does not produce that
     * component and therefore cannot restore state into it.</span>
     * </p>
     * </li>
     *
     * </ol>
     *
     * <p class="changed_added_5_0">
     * The manipulations must be recorded in the order in which the application performed them, and the recorded
     * position must travel with the manipulation rather than with the component it applies to. Each build creates the
     * components it produces anew, at the position the markup declares for them, so anything kept on such a component
     * may be lost before the manipulation is replayed.
     * </p>
     *
     * <p class="changed_added_5_0">
     * A manipulation that the restoring build performs again need not be recorded, since that build reproduces it.
     * This is the case for the manipulations performed while the view is being built, for example from a listener for
     * {@link jakarta.faces.event.PostAddToViewEvent}.
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
     * <p class="changed_added_5_0">
     * This method returns the view that was saved: restoring the <code>Object</code> that {@link #saveView} returned
     * for a view must produce that view again, holding the same components at the same positions with the same state.
     * The algorithm below produces it by performing the restoring build and reconciling from the saved state what that
     * build does not produce. Where the restoring build cannot reproduce a decision of the rendering build, this
     * method returns the view it could build, as described below.
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
     * <span class="changed_modified_5_0">This is the restoring build, and the view it produces holds the components
     * added, removed or moved while the view was being built. It does not reflect the manipulations the application
     * performed after the view was built: components added afterwards are absent, components removed afterwards are
     * present, and components moved afterwards are at the position the markup declares for them. All of these cases
     * must be handled.</span>
     * </p>
     *
     * <p class="changed_added_5_0">
     * What the restoring build produces also follows from the build time conditions it evaluates. For those the
     * implementation provides, it must reproduce the decision the rendering build reached, rather than evaluate the
     * condition again. The rendering build that follows evaluates them again, and it is that build which produces the
     * view the current state of the model asks for, and which is saved in turn.
     * </p>
     *
     * <p class="changed_added_5_0">
     * Failing to reproduce a decision must not fail the request. An iteration over items which no longer hold the
     * elements its rows were rendered over is the case which cannot be reproduced from saved state at all: this method
     * returns the view it could build, and a component of that view which the model no longer backs is answered for by
     * the phase which reads that model, not by Restore View.
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
     * the application performed them: ensure that removed components are removed, that added components are added with
     * the state recorded for them, and that moved components are at the recorded parent, facet name and index among
     * their siblings. A component that the restoring build already produced at its recorded position must not be added
     * a second time.</span>
     * </p>
     * </li>
     *
     * </ol>
     *
     * <p>
     * The implementation must ensure that the {@link jakarta.faces.component.UIComponent#restoreState} method is called
     * for each node in the tree, <span class="changed_modified_5_0">except for those the application removed after the
     * view was built.</span>
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
