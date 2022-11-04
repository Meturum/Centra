# Centra

Centra is a Wrapper API for [Centre](),  

## Documentation
___

Visit our official documentation! [(Click here)](https://docs.meturum.com/projects/centra/)

JavaDocs: [(Click here)](https://docs.meturum.com/projects/centra/javadocs/)

## Usage (Maven)
___

Add the following to your _pom.xml_ file in your project:

```xml
<repository>
    <id>centra-api</id>
    <url>http://api.meturum.com:8081/repository/centra-api/</url>
</repository>
```

```xml
<dependency>
    <groupId>com.centra</groupId>
    <artifactId>centra-api</artifactId>
    <version>{VERSION}-SNAPSHOT</version>
</dependency>
```

Currently, Centra does not support https. If you are using Maven `3.8.1` or higher, please include this snippet in your _settings.xml_ file.

```xml
<mirror>
    <id>centra-http-unblocker</id>
    <mirrorOf>centra-api</mirrorOf>
    <name></name>
    <url>http://api.meturum.com:8081/repository/centra-api/</url>
</mirror>
```

This allows for Centra to bypass the https requirement. Only the url specified in the mirror will be affected.