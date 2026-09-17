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
package jakarta.faces.validator;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.Locale;

import jakarta.faces.application.Application;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.NumberConverter;
import jakarta.faces.render.RenderKit;

import org.junit.jupiter.api.AfterEach;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

abstract class ValidatorTestBase {

    private MockedStatic<FacesContext> mockStaticFacesContext;
    private Locale previousDefaultLocale;

    FacesContext mockFacesContext() {
        return mockFacesContextWithLocale(Locale.getDefault());
    }

    FacesContext mockFacesContextWithLocale(Locale locale) {
        if (previousDefaultLocale == null) {
            previousDefaultLocale = Locale.getDefault();
        }
        Locale.setDefault(locale);

        UIViewRoot mockedViewRoot = Mockito.mock(UIViewRoot.class);
        when(mockedViewRoot.getLocale()).thenReturn(locale);
        when(mockedViewRoot.getViewId()).thenReturn("/viewId");

        Application mockedApplication = Mockito.mock(Application.class);
        when(mockedApplication.<Number>createConverter(NumberConverter.CONVERTER_ID)).thenReturn(new NumberConverter());

        RenderKit mockedRenderKit = Mockito.mock(RenderKit.class);

        FacesContext mockedFacesContext = Mockito.mock(FacesContext.class);
        when(mockedFacesContext.getViewRoot()).thenReturn(mockedViewRoot);
        when(mockedFacesContext.getApplication()).thenReturn(mockedApplication);
        when(mockedFacesContext.getRenderKit()).thenReturn(mockedRenderKit);

        closeMockStaticFacesContextIfNecessary();
        mockStaticFacesContext = mockStatic(FacesContext.class);
        mockStaticFacesContext.when(FacesContext::getCurrentInstance).thenReturn(mockedFacesContext);

        return mockedFacesContext;
    }

    @AfterEach
    void closeMockStaticFacesContextIfNecessary() {
        if (mockStaticFacesContext != null) {
            mockStaticFacesContext.close();
            mockStaticFacesContext = null;
        }
        if (previousDefaultLocale != null) {
            Locale.setDefault(previousDefaultLocale);
            previousDefaultLocale = null;
        }
    }

}
