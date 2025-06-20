![NATS](src/main/javadoc/images/large-logo.png)

# JNATS JSON

This library is a JSON Parser built specifically for JNATS to avoid a 3rd party library dependency.

It has been extracted and repackaged from the JNATS library as part of the effort to upgrade and repackage the JNATS client. 

**Current Release**: 3.0.0 &nbsp; **Current Snapshot**: 0.0.0-SNAPSHOT

[![License Apache 2](https://img.shields.io/badge/License-Apache2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.synadia/jnats-json/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.synadia/jnats-json)
[![Javadoc](http://javadoc.io/badge/io.synadia/jnats-json.svg?branch=main)](http://javadoc.io/doc/io.synadia/jnats-json?branch=main)
[![Coverage Status](https://coveralls.io/repos/github/synadia-io/json.java/badge.svg?branch=main)](https://coveralls.io/github/synadia-io/json.java?branch=main)
[![Build Main Badge](https://github.com/synadia-io/json.java/actions/workflows/build-main.yml/badge.svg?event=push)](https://github.com/synadia-io/json.java/actions/workflows/build-main.yml)
[![Release Badge](https://github.com/synadia-io/json.java/actions/workflows/build-release.yml/badge.svg?event=release)](https://github.com/synadia-io/json.java/actions/workflows/build-release.yml)

### Gradle

```groovy
dependencies {
    implementation 'io.synadia:jnats-json:{major.minor.patch}'
}
```

If you need the latest and greatest before Maven central updates, you can use:

```groovy
repositories {
    mavenCentral()
    maven {
      url "https://repo1.maven.org/maven2/"
    }
}
```

If you need a snapshot version, you must add the url for the snapshots and change your dependency.

```groovy
repositories {
    mavenCentral()
    maven {
      url "https://central.sonatype.com/repository/maven-snapshots/"
    }
}

dependencies {
   implementation 'io.synadia:jnats-json:{major.minor.patch}-SNAPSHOT'
}
```

### Maven

```xml
<dependency>
    <groupId>io.synadia</groupId>
    <artifactId>jnats-json</artifactId>
    <version>{major.minor.patch}</version>
</dependency>
```

If you need the absolute latest, before it propagates to maven central, you can use the repository:

```xml
<repositories>
    <repository>
        <id>sonatype releases</id>
        <url>https://repo1.maven.org/maven2</url>
        <releases>
           <enabled>true</enabled>
        </releases>
    </repository>
</repositories>
```

If you need a snapshot version, you must enable snapshots and change your dependency.

```xml
<repositories>
    <repository>
        <id>sonatype snapshots</id>
        <url>https://central.sonatype.com/repository/maven-snapshots/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>

<dependency>
    <groupId>io.synadia</groupId>
    <artifactId>jnats-json</artifactId>
    <version>{major.minor.patch}-SNAPSHOT</version>
</dependency>
```


## License

Unless otherwise noted, the NATS source files are distributed
under the Apache Version 2.0 license found in the LICENSE file.
