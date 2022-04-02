#!/bin/bash -xe
#
# Copyright (c) 2022 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0, which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# This Source Code may also be made available under the following Secondary
# Licenses when the conditions for such availability set forth in the
# Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
# version 2 with the GNU Classpath Exception, which is available at
# https://www.gnu.org/software/classpath/license.html.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0

if [ -z "$WORKSPACE" ]; then
  export WORKSPACE=`pwd`
fi

export BASEDIR=${WORKSPACE}/faces-tck

if [ -z "$JAKARTA_JARS" ]; then
  export JAKARTA_JARS=$BASEDIR/lib
fi

export PATH=$JAVA_HOME/bin:$ANT_HOME/bin:$PATH

which ant
ant -version

which java
java -version

export ANT_OPTS="-Xmx2G -Djavax.xml.accessExternalStylesheet=all \
-Djavax.xml.accessExternalSchema=all \
-DenableExternalEntityProcessing=true \
-Djavax.xml.accessExternalDTD=file,http,https"

export TCK_NAME="jsf"

cd $BASEDIR

mkdir -p $JAKARTA_JARS
mvn -f $BASEDIR/docker/pom.getmodules.xml clean -Pstaging dependency:copy-dependencies -DoutputDirectory="${JAKARTA_JARS}" -Dmdep.stripVersion=true
mvn -f $BASEDIR/docker/pom.getlibs.xml clean -Pstaging dependency:copy-dependencies -DoutputDirectory="${JAKARTA_JARS}" #-Dmdep.stripVersion=true


RMI_CLASSES="-Drmi.classes=$JAKARTA_JARS/glassfish-corba-omgapi.jar"

TCK_SPECIFIC_PROPS="-Djsf.classes=$JAKARTA_JARS/jakarta.enterprise.cdi-api.jar:$JAKARTA_JARS/jakarta.servlet.jsp.jstl-api.jar:$JAKARTA_JARS/jakarta.inject-api.jar:$JAKARTA_JARS/jakarta.faces-api.jar:$JAKARTA_JARS/jakarta.servlet.jsp-api.jar:$JAKARTA_JARS/jakarta.servlet-api.jar:$JAKARTA_JARS/jakarta.el-api.jar:$JAKARTA_JARS/jakarta.annotation-api.jar:$JAKARTA_JARS/glassfish-corba-omgapi.jar"

echo "########## $TCK_NAME BUILD Started##########"
ant -f $BASEDIR/install/$TCK_NAME/bin/build.xml -Ddeliverabledir=$TCK_NAME -Dbasedir=$BASEDIR/install/$TCK_NAME/bin $RMI_CLASSES $TCK_SPECIFIC_PROPS  clean.all build.all.jars 

ant -f $BASEDIR/install/$TCK_NAME/bin/build.xml -Ddeliverabledir=$TCK_NAME -Dbasedir=$BASEDIR/install/$TCK_NAME/bin $RMI_CLASSES $TCK_SPECIFIC_PROPS  build.all 
  
mkdir -p $BASEDIR/internal/docs/$TCK_NAME
cp $BASEDIR/internal/docs/dtd/*.dtd $BASEDIR/internal/docs/$TCK_NAME/
ant -f $BASEDIR/release/tools/build.xml -Ddeliverabledir=$TCK_NAME -Dbasedir=$BASEDIR/release/tools -Dskip.createbom="true" -Dskip.build="true" $TCK_SPECIFIC_PROPS $TCK_NAME
echo "########## $TCK_NAME BUILD Completed ##########"

# Copy build to archive path
# mkdir -p ${BASEDIR}/standalone-bundles/
# UPPER_TCK=`echo "${TCK_NAME}" | tr '[:lower:]' '[:upper:]'`
# cd ${BASEDIR}/release/${UPPER_TCK}_BUILD/latest/
# for entry in `ls *.zip`; do
#   date=`echo "$entry" | cut -d_ -f2`
#   strippedEntry=`echo "$entry" | cut -d_ -f1`
#   if [ "$strippedEntry" == "excludelist" ]; then
#       strippedEntry=${strippedEntry}_`echo "$entry" | cut -d_ -f2`
#   fi
#   echo "copying ${BASEDIR}/release/${UPPER_TCK}_BUILD/latest/$entry to ${BASEDIR}/standalone-bundles/${strippedEntry}_latest.zip"
#   echo "copying ${BASEDIR}/release/${UPPER_TCK}_BUILD/latest/$entry to ${BASEDIR}/standalone-bundles/${strippedEntry}.zip"
#   cp ${BASEDIR}/release/${UPPER_TCK}_BUILD/latest/$entry ${BASEDIR}/standalone-bundles/${strippedEntry}.zip
# done



