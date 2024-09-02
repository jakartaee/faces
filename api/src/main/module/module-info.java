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
module jakarta.faces {
    
    requires jakarta.inject;
    requires jakarta.validation;
    requires java.logging;
    requires java.naming;

    requires static jakarta.cdi;
    requires static jakarta.cdi.el;
    requires static jakarta.el;
    requires static jakarta.servlet;
    requires static jakarta.websocket.client;
    requires static jakarta.websocket;
    requires static jakarta.persistence;
    requires static jakarta.ejb;
    requires static jakarta.json;
    requires transitive java.desktop;
    requires transitive java.sql;
    requires transitive java.xml;

    exports jakarta.faces;
    exports jakarta.faces.annotation;
    exports jakarta.faces.application;
    exports jakarta.faces.component;
    exports jakarta.faces.component.behavior;
    exports jakarta.faces.component.html;
    exports jakarta.faces.component.search;
    exports jakarta.faces.component.visit;
    exports jakarta.faces.context;
    exports jakarta.faces.convert;
    exports jakarta.faces.el;
    exports jakarta.faces.event;
    exports jakarta.faces.flow;
    exports jakarta.faces.flow.builder;
    exports jakarta.faces.lifecycle;
    exports jakarta.faces.model;
    exports jakarta.faces.push;
    exports jakarta.faces.render;
    exports jakarta.faces.validator;
    exports jakarta.faces.view;
    exports jakarta.faces.view.facelets;
    exports jakarta.faces.webapp;
}
