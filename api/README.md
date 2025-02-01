# Jakarta Faces API

This project generates the Jakarta Faces API, source and javadoc JAR files.

## Building

**Build JAR files and API docs**:

```bash
mvn clean package
```

The JAR files and javadocs can be found in `target`.

**Browse API docs**:

```bash
browse ./target/apidocs/index.html
```


## Docs

**Generate TS, VDL and RenderKit docs**:

```bash
mvn clean package -P docs
```

The docs can be found in `target`.

**Browse TS docs**:

```bash
browse ./target/tsdoc/docs/index.html
```

**Browse VDL docs**:

```bash
browse ./target/vdldoc/index.html
```

The CSS of vdldoc can be customized via `src/main/vdldoc/resources/faces-api.css`.
It is primarily used to adjust the `.changed_added_***`, `.changed_modified_***` and `.changed_deleted_***` styles in the vdldoc.

**Browse RenderKit docs**:

```bash
browse ./target/renderkitdoc/index.html
```

The CSS of renderkitdoc can be customized via `src/main/renderkitdoc/resources/stylesheet.css`.
It is primarily used to adjust the `.changed_added_***`, `.changed_modified_***` and `.changed_deleted_***` styles in the renderkitdoc.
