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

    FacesContext mockFacesContext() {
        return mockFacesContextWithLocale(Locale.getDefault());
    }

    FacesContext mockFacesContextWithLocale(Locale locale) {
        Locale.setDefault(locale);

        UIViewRoot mockedViewRoot = Mockito.mock(UIViewRoot.class);
        when(mockedViewRoot.getLocale()).thenReturn(locale);
        when(mockedViewRoot.getViewId()).thenReturn("/viewId");

        Application mockedApplication = Mockito.mock(Application.class);
        when(mockedApplication.createConverter(NumberConverter.CONVERTER_ID)).thenReturn(new NumberConverter());

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
    }
}
