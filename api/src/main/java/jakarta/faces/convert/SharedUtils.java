/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
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
package jakarta.faces.convert;

import static java.util.regex.Pattern.quote;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;

class SharedUtils {

    static <T> Class<T> getReturnTypeOfGetterMethodAssociatedWithModelValue(FacesContext context, UIComponent component) {
        var valueExpression = component.getValueExpression("value");

        if (valueExpression == null) {
            return null;
        }

        var valueExpressionString = valueExpression.getExpressionString();

        if (valueExpressionString != null) {
            var methodExpressionString = convertValueExpressionStringToMethodExpressionString(valueExpressionString);

            if (methodExpressionString != null) {
                return (Class<T>) context.getApplication().getExpressionFactory()
                        .createMethodExpression(context.getELContext(), methodExpressionString, Object.class, new Class[0])
                        .getMethodInfo(context.getELContext()).getReturnType();
            }
        }

        return null;
    }

    static String convertValueExpressionStringToMethodExpressionString(String valueExpressionString) {
        var parts = valueExpressionString.split(quote("."));
        var lastPart = parts[parts.length - 1];

        if (lastPart.contains("[") || lastPart.contains("]") || !lastPart.endsWith("}")) {
            return null; // TODO: implement.
        }

        parts[parts.length - 1] = "get" + Character.toUpperCase(lastPart.charAt(0)) + lastPart.substring(1, lastPart.length() - 1) + "()}";
        return String.join(".", parts);
    }
}
