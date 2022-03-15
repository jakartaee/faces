[[GBFVU]][[configuring-your-environment-to-run-the-tck-against-the-compatible-implementation]]

4.1 Configuring Your Environment to Run the TCK Against the Compatible Implementation (CI)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

After configuring your environment as described in this section,
continue with the instructions in link:using.html#GBFWO[Chapter 5, "Executing Tests."]


[NOTE]
=======================================================================

In these instructions, variables in angle brackets need to be expanded
for each platform. For example, `<JAVA_HOME>` becomes `$JAVA_HOME` on
Solaris/Linux and `%JAVA_HOME%` on Windows. In addition, the forward
slashes (`/`) used in all of the examples need to be replaced with
backslashes (`\`) for Windows. Finally, be sure to use the appropriate
separator for your operating system when specifying multiple path
entries (`;` on Windows, `:` on UNIX/Linux).

On Windows, you must escape any backslashes with an extra backslash in
path separators used in any of the following properties, or use forward
slashes as a path separator instead.

=======================================================================


1.  Set the following environment variables in your shell environment:
  a.  `JAVA_HOME` to the directory in which Java SE {SEversion} is installed
  b.  `M2_HOME` to the directory in which the Apache Maven build tool is installed.
  c.  +{TechnologyHomeEnv}+ to the directory in which the {TechnologyShortName}
  {TechnologyVersion} CI has been installed
  d.  `PATH` to include the following directories: `JAVA_HOME/bin`,
  and `M2_HOME/bin`
2.  Set the following System properties in pom.xml:
  a.  Set the `webServerHost` property to the name of the host on which
  Jakarta EE {JakartaEEVersion} CI is running. +
  The default setting is `localhost`.
  b.  Set the `webServerPort` property to the port number of the host on
  which Jakarta EE {JakartaEEVersion} CI is running. +
  The default setting is `8080`.
  c.  Set the `web.home` property to the installation directory of Jakarta EE
  {JakartaEEVersion} CI.

3. Set the below jars to the classpath 
  a. JAR file for the {TechnologyShortName} {TechnologyVersion} API. +
  eg.  `${web.home}/modules/jakarta.faces-api.jar`.
  b. Arquillian JAR arquillian-junit5-container
  Maven cordinates :  
+
[source,oac_no_warn]
----
      <dependency>
            <groupId>org.jboss.arquillian.junit5</groupId>
            <artifactId>arquillian-junit5-container</artifactId>
            <version>1.7.0.Alpha10</version>
      </dependency>
----
+
  c. JUnit 4 jars (4.13.2)
  Maven cordinates :
+
[source,oac_no_warn]
----
       <dependency>
          <groupId>org.junit</groupId>
          <artifactId>junit-bom</artifactId>
          <version>4.13.2</version>
          <type>pom</type>
          <scope>import</scope>
       </dependency>
----
+
   d. sigtest-maven-plugin (1.5) to run the signature tests.
   Maven cordinates :
+
[source,oac_no_warn]
----
      <dependency>
            <groupId>org.netbeans.tools</groupId>
            <artifactId>sigtest-maven-plugin</artifactId>
            <version>1.5</version>
        </dependency>
----
+
   e.  {TechnologyRI} CI jars +
   For eg, if you are using the {TechnologyRI} CI below jars need to be added to Classpath


+
[source,oac_no_warn]
----
${web.home}/modules/jakarta.inject-api.jar:
${web.home}/modules/jakarta.annotation-api.jar:
${web.home}/modules/jakarta.xml.bind-api.jar:
${web.home}/modules/jakarta.activation.jar:
----
+


[[GCLHU]][[configuring-your-environment-to-run-the-tck-against-the-vendor-implementation]]

4.2 Configuring Your Environment to Run the TCK Against the Vendor Implementation (VI)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Follow the instructions above, customized as necessary for the
Vendor implementation.
A custom deployment handler will need to be created for the
Vendor implementation.


