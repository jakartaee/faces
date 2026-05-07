package ee.jakarta.tck.faces.faces22.composite_component;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue5065Bean {
    
    private int inlineHashCode;
    private int htmlWrapperHashCode;
    private int componentWrapperHashCode;

    public void submit() {
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        inlineHashCode = view.findComponent("form:renderResponse").hashCode(); 
        htmlWrapperHashCode = view.findComponent("form:htmlWrapper:renderResponse").hashCode(); 
        componentWrapperHashCode = view.findComponent("form:componentWrapper:renderResponse").hashCode(); 
    }

    public int getInlineHashCode() {
        return inlineHashCode;
    }

    public int getHtmlWrapperHashCode() {
        return htmlWrapperHashCode;
    }

    public int getComponentWrapperHashCode() {
        return componentWrapperHashCode;
    }
}