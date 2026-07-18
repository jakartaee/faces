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

package ee.jakarta.tck.faces.faces23.resource_library_contracts_vdl;

import java.util.List;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewDeclarationLanguage;
import jakarta.faces.view.ViewDeclarationLanguageFactory;
import jakarta.faces.view.ViewDeclarationLanguageWrapper;

/**
 * Computes the resource library contracts of every single request, the way an application serving
 * several tenants from one war would do.
 */
public class Spec971VDLFactory extends ViewDeclarationLanguageFactory {

    public Spec971VDLFactory(ViewDeclarationLanguageFactory wrapped) {
        super(wrapped);
    }

    @Override
    public ViewDeclarationLanguage getViewDeclarationLanguage(String viewId) {
        return new Spec971VDL(getWrapped().getViewDeclarationLanguage(viewId));
    }

    private static class Spec971VDL extends ViewDeclarationLanguageWrapper {

        private static final String CONTRACT_PARAMETER_NAME = "contract";
        private static final List<String> SELF_CONTAINED_CONTRACTS = List.of("host1", "host2");
        private static final String EXTENDING_CONTRACT = "host4";
        private static final String EXTENDED_CONTRACT = "host2";

        private Spec971VDL(ViewDeclarationLanguage wrapped) {
            super(wrapped);
        }

        @Override
        public List<String> calculateResourceLibraryContracts(FacesContext context, String viewId) {
            String contract = context.getExternalContext().getRequestParameterMap().get(CONTRACT_PARAMETER_NAME);

            if (SELF_CONTAINED_CONTRACTS.contains(contract)) {
                return List.of(contract);
            }

            if (EXTENDING_CONTRACT.equals(contract)) {
                return List.of(EXTENDING_CONTRACT, EXTENDED_CONTRACT);
            }

            return null;
        }
    }
}
