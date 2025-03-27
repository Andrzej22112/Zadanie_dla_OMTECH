# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.4/maven-plugin/build-image.html)

### Maven Parent overrides

Due to Maven's design, items are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted items like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these items.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

