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

package ee.jakarta.tck.faces.faces22.composite_component;

import jakarta.el.ExpressionFactory;
import jakarta.el.ValueExpression;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.NamingContainer;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ComponentSystemEventListener;
import jakarta.faces.event.PreRenderComponentEvent;

@FacesComponent("ee.jakarta.tck.faces.faces22.composite_component.Issue2093Component2")
public class Issue2093Component2 extends Issue2093ComponentBase
        implements NamingContainer, ComponentSystemEventListener {

    public static final String COMPONENT_FAMILY = "jakarta.faces.NamingContainer";

    public Issue2093Component2() {
        subscribeToEvent(PreRenderComponentEvent.class, this);
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    @Override
    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (ctx.isPostback()) {
            return;
        }
        UIComponent parent = findComponent("controls");
        ExpressionFactory ef = ctx.getApplication().getExpressionFactory();
        boolean itemIsNull = getAttributes().get("item") == null;
        HtmlOutputText itemCheck = new HtmlOutputText();
        itemCheck.setId("check2");
        itemCheck.setValue("Item Attribute is null: " + itemIsNull);
        HtmlInputText txt = new HtmlInputText();
        txt.setId("txt");
        ValueExpression ve = ef.createValueExpression(ctx.getELContext(), "#{cc.attrs.item.text}", String.class);
        txt.setValueExpression("value", ve);
        parent.getChildren().add(txt);
        parent.getChildren().add(itemCheck);
    }
}
