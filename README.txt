The purpose of this project is to supply enough AWT in order to be able to use xmlgraphics-commons on Android (see https://github.com/plutext/ae-xmlgraphics-commons).  (For docx4j on Android to be able to load images, it needs xmlgraphics-commons).

The source code came from OpenJDK (licence is GPL2 + Classpath exception).

The following packages are re-packaged here (prepending 'ae' to the package name):
- com.sun.imageio
- java.awt
- javax.accessibility
- javax.imageio
- sun.awt
- sun.font
- sun.java2d

Android requires us to re-package javax.accessibility and javax.imageio

The others I have re-packaged as well, since their contents have been altered.

WARNING: this is not a full implementation of AWT; it is just sufficient for purpose.  For example, the following are excluded from the build:

            <exclude name="ae/sun/awt/im/"/>
            <exclude name="ae/java/awt/im/"/>
            <exclude name="ae/java/awt/im/spi/"/>

Generally, you can search for '//ae' to find places where I have commented things out.

To build:

    ant dist
