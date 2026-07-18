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

package ee.jakarta.tck.faces.faces22.file_upload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.servlet.http.Part;

/**
 * Rejects an uploaded file whose bytes do not carry the expected marker, so that the model update of the sibling
 * inputs in the same form is suppressed.
 */
@FacesValidator("Spec802Validator")
public class Spec802Validator implements Validator<Part> {

    static final String MARKER = "JSR-344";

    static final String INVALID_FILE_MESSAGE = "Invalid file";

    @Override
    public void validate(FacesContext context, UIComponent component, Part value) throws ValidatorException {
        String text;

        try (InputStream input = value.getInputStream()) {
            text = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ValidatorException(new FacesMessage(INVALID_FILE_MESSAGE), e);
        }

        if (!text.contains(MARKER)) {
            throw new ValidatorException(new FacesMessage(INVALID_FILE_MESSAGE));
        }
    }
}
