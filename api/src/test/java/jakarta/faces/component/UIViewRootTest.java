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

package jakarta.faces.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Locale;

import jakarta.el.ValueExpression;
import jakarta.faces.context.CurrentFacesContext;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UIViewRootTest {

    /**
     * A locale string must resolve to the same locale on every server. Under a Turkish or Azeri default locale
     * {@code I} lowercases to dotless {@code i} (U+0131), which would turn a language such as {@code IT} into
     * {@code ıt}.
     */
    @Test
    public void testGetLocaleFromStringIsIndependentOfDefaultLocale() {
        FacesContext facesContext = Mockito.mock(FacesContext.class);
        ExternalContext externalContext = Mockito.mock(ExternalContext.class);

        CurrentFacesContext.set(facesContext);
        when(facesContext.getExternalContext()).thenReturn(externalContext);
        when(externalContext.getApplicationMap()).thenReturn(null);

        Locale defaultLocale = Locale.getDefault();

        try {
            for (Locale locale : List.of(Locale.US, Locale.forLanguageTag("tr-TR"), Locale.forLanguageTag("az-AZ"))) {
                Locale.setDefault(locale);
                assertEquals("it", localeOf(facesContext, "IT").getLanguage(), locale.toString());
                assertEquals("fi", localeOf(facesContext, "FI").getLanguage(), locale.toString());
                assertEquals("it", localeOf(facesContext, "it").getLanguage(), locale.toString());
            }
        } finally {
            Locale.setDefault(defaultLocale);
            CurrentFacesContext.set(null);
        }
    }

    private static Locale localeOf(FacesContext facesContext, String localeString) {
        ValueExpression expression = Mockito.mock(ValueExpression.class);
        when(expression.getValue(facesContext.getELContext())).thenReturn(localeString);

        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setValueExpression("locale", expression);

        return viewRoot.getLocale();
    }

}
