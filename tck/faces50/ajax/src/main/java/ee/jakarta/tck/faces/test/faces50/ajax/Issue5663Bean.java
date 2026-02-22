package ee.jakarta.tck.faces.test.faces50.ajax;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue5663Bean {

    private String result;

    public void form1button() {
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form2");
    }

    public void form2button() {
        result = "form2result";
    }

    public String getResult() {
        return result;
    }
}
