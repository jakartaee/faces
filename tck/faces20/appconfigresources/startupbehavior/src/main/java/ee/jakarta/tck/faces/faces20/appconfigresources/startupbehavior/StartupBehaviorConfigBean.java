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
package ee.jakarta.tck.faces.faces20.appconfigresources.startupbehavior;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.FacesException;
import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.Renderer;
import jakarta.inject.Named;

/**
 * Looks the artifacts declared in {@code /META-INF/testCase2-config.xml} up in the application and
 * in the render kit. That file is only reachable because it is listed in
 * {@code jakarta.faces.CONFIG_FILES}, while {@code /WEB-INF/appconfig-unlisted-config.xml} is not
 * listed and its artifacts must therefore not be registered.
 */
@Named
@RequestScoped
public class StartupBehaviorConfigBean {

    static final String COMPONENT_TYPE = "StartupBehaviorConfigComponent";
    static final String CONVERTER_ID = "StartupBehaviorConfigConverter";
    static final String VALIDATOR_ID = "StartupBehaviorConfigValidator";
    static final String RENDERER_TYPE = "StartupBehaviorConfigRenderer";
    static final String UNLISTED_COMPONENT_TYPE = "StartupBehaviorUnlistedComponent";

    private static final String NOT_REGISTERED = "NOT-REGISTERED";

    public String getComponent() {
        return simpleName(application().createComponent(COMPONENT_TYPE));
    }

    public String getConverter() {
        return simpleName(application().createConverter(CONVERTER_ID));
    }

    public String getValidator() {
        return simpleName(application().createValidator(VALIDATOR_ID));
    }

    public String getRenderer() {
        Renderer renderer = FacesContext.getCurrentInstance().getRenderKit()
                .getRenderer(StartupBehaviorConfigComponent.COMPONENT_FAMILY, RENDERER_TYPE);
        return simpleName(renderer);
    }

    public String getUnlisted() {
        try {
            return simpleName(application().createComponent(UNLISTED_COMPONENT_TYPE));
        } catch (FacesException e) {
            return NOT_REGISTERED;
        }
    }

    private static Application application() {
        return FacesContext.getCurrentInstance().getApplication();
    }

    private static String simpleName(Object artifact) {
        return artifact == null ? NOT_REGISTERED : artifact.getClass().getSimpleName();
    }
}
