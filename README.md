
# LoganSquareX
![build status](https://travis-ci.org/LoganSquareX/LoganSquareX.svg?branch=master)

base on logansquare

The fastest JSON parsing and serializing library available for Android. Based on Jackson's streaming API, LoganSquare is able to consistently outperform GSON and Jackson's Databind library by 400% or more<sup>[1](#1)</sup>. By relying on compile-time annotation processing to generate code, you know that your JSON will parse and serialize faster than any other method available.

By using this library, you'll be able to utilize the power of Jackson's streaming API without having to code tedius, low-level code involving `JsonParser`s or `JsonGenerator`s. Instead, just annotate your model objects as a `@JsonObject` and your fields as `@JsonField`s and we'll do the heavy lifting for you.

Don't believe it could improve upon Jackson Databind's or GSON's performance that much? Well, then check out the nifty graphs below for yourself. Not convinced? Feel free to build and run the BenchmarkDemo app included in this repository.

<a name="1"></a>
*<sup>1</sup> <sub>Note: Our "400% or more" performance improvement metric was determined using ART. While LoganSquare still comes out on top with Dalvik, it seems as though the comparison is much closer. The benchmarks shown are actual screenshots taken from a 2nd gen Moto X.<sub>*

![Benchmarks](docs/benchmarks.jpg)
## Premise
If your IDE is IntelliJ IDEA, please configure Annotation Processing first.
If you want annotation processors to be run during compilation, select this checkbox and specify associated options:
#### -Obtain processors from project classpath
Select this option, if you want IntelliJ IDEA to obtain the annotation processors from the project classpath. This is useful, for example, if you use a custom annotation processor as part of your project, or if the processor is stored in a .jar file attached to all the corresponding modules as a library.
#### -Processor path
Select this option and specify in the field to the right the path to the annotation processor, if it is not desirable to include the processor into the project or project libraries.
#### -Store generated sources relative to
Use the fields below to define where the sources, generated by the annotation processors, are stored, and to override the default behaviour for a profile.
##### --Module output directory
By default, the sources generated by annotation processors are stored relative to the module output directory.
##### --Module content root
Choose this option to override the default behaviour for a profile.
At a later time, if you want to use the generated classes as your own sources, you can mark the corresponding directories as source roots.

<a name="2"></a>
*<sup>2</sup> <sub>On rebuild, the directories in which the generated sources are stored will be cleaned up as ordinary output directories. So it is not recommended to store non-generated sources in such directories. Otherwise, the corresponding sources will be lost on rebuild.<sub>*


## Download
#### Gradle
To add the library to your app's build.gradle file.
If your Gradle plugin version is higher than 2.3.
```groovy
dependencies {
    annotationProcessor 'io.logansquarex:logansquareX-compiler:2.0.0'
    compile 'io.logansquarex:logansquareX:2.0.0'
}
```

Below version 2.3
```groovy
buildscript {

    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}
apply plugin: 'com.neenbedankt.android-apt'

dependencies {
    apt 'io.logansquarex:logansquareX-compiler:2.0.0'
    compile 'io.logansquarex:logansquareX:2.0.0'
}
```

#### Maven
```groovy
<dependency>
       <groupId>io.logansquarex</groupId>
       <artifactId>logansquareX</artifactId>
       <version>2.0.0</version>
       <type>pom</type>
     </dependency>
```
For the curious, the buildscript and apply plugin lines add the [apt plugin](https://bitbucket.org/hvisser/android-apt), which is what allows us to do compile-time annotation processing. The first dependency is what tells Gradle to process your JSON annotations, and the second dependency is our tiny 19kb runtime library that interfaces with the generated code for you.

## Usage

Using LoganSquare is about as easy as it gets. Here are a few docs to get you started:

 * [Creating your models](docs/Models.md)
 * [Parsing from JSON](docs/Parsing.md)
 * [Serializing to JSON](docs/Serializing.md)
 * [Supporting custom types](docs/TypeConverters.md)

## Warn
When using in Spring need to validate @Configuration classes,Specifically refer to this [library(spring-configuration-validation-processor)](https://github.com/pellaton/spring-configuration-validation-processor)

## Proguard

Like all libraries that generate dynamic code, Proguard might think some classes are unused and remove them. To prevent this, the following lines can be added to your proguard config file.

```
//nothing need keep
```

## Why LoganSquareX?

We're BlueLine Labs, a mobile app development company based in Chicago. We love this city so much that we named our company after the blue line of the iconic 'L.' And what's one of the most popular stops on the blue line? Well, that would be Logan Square of course. Does it have anything to do with JSON? Nope, but we're okay with that.

## Props

 * [Jackson's streaming API](https://github.com/FasterXML/jackson-core) for being a super-fast, awesome base for this project.
 * [Instagram's ig-json-parser](https://github.com/Instagram/ig-json-parser) for giving us the idea for this project.
 * [Jake Wharton's Butterknife](https://github.com/JakeWharton/butterknife) for being a great reference for annotation processing and code generation.
 * [Annotation processors settings  in IDEA](https://www.jetbrains.com/help/idea/annotation-processors.html) for being Configure the official guidance of annotation processing in IDEA


## License

    Copyright 2018 LoganSquareX
 
    Copyright 2015 BlueLine Labs, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


