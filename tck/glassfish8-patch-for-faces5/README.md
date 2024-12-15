This folder contains modified JAR files originally coming from GlassFish 8.0.0-JDK17-M7.

The goal is to be able to use Faces 5.0 instead of Faces 4.1 on GlassFish 8.

These JAR files are modified as follows:

- Open `META-INF/MANIFEST.MF`
- Find `Import-Package` entry
- For each item matching `*faces*;version="[4.1,5)"`
- Replace it by `*faces*;version="[5.0,6)"`

This is processed by `<id>glassfish8-patch-for-faces5</id>` in `tck/pom.xml`.

This patch must be removed once GlassFish 9 is released and used in TCK.
