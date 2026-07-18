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
package ee.jakarta.tck.faces.faces23.build_time_component_handler;

import java.io.IOException;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.view.facelets.ComponentConfig;
import jakarta.faces.view.facelets.ComponentHandler;
import jakarta.faces.view.facelets.FaceletContext;
import jakarta.faces.view.facelets.FaceletHandler;
import jakarta.faces.view.facelets.Tag;
import jakarta.faces.view.facelets.TagConfig;

public class ComponentHandlerInBuildTimeIfHandler extends ComponentHandler {

    public static final String MESSAGE = "handler applied";

    public static final String MESSAGE_KEY = "tagHandlerMessage";

    public ComponentHandlerInBuildTimeIfHandler(final TagConfig config) {
        super(new ComponentConfig() {

            @Override
            public String getComponentType() {
                return UIInput.COMPONENT_TYPE;
            }

            @Override
            public String getRendererType() {
                return "jakarta.faces.Text";
            }

            @Override
            public FaceletHandler getNextHandler() {
                return config.getNextHandler();
            }

            @Override
            public Tag getTag() {
                return config.getTag();
            }

            @Override
            public String getTagId() {
                return config.getTagId();
            }
        });
    }

    @Override
    public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
        ctx.getFacesContext().getAttributes().put(MESSAGE_KEY, MESSAGE);
        super.apply(ctx, parent);
    }
}
