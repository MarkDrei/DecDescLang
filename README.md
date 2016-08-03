Decision Description Language
=============================

The DDL project is an addition to the Eclipse Java IDE which allows the documentation and referencing of design decisions.
Design decisions can reference requirements, which are documented in the ReqIF format.

DDL is a domain specific language developed with Xtext.


Getting started
===============

Import all the provided projects into your Eclipse workspace.
Resolve the required dependencies (see chapter "Project structure").


Project structure
=================
In general the project consists of multiple Eclipse plug-ins.
The required plug-ins can be seen in the MANIFEST.MF files; here a high level overview:
- Eclipse IDE plug-ins: For providing Eclipse specific functionality, such as providing markers, views...
- Xtext plug-ins: The DDL language is based on [Xtext](https://eclipse.org/Xtext/)
- RMF / ProR plug-ins: The link requirements requires [RMF](https://eclipse.org/rmf/) models and the [ProR](https://eclipse.org/rmf/pror/), the user interface on top of RMF
- Javadoc Extender plug-ins: [Javadoc Extender](https://github.com/MarkDrei/JavadocExtender) is used to generate references from Java to both requirements and decisions

The provided plug-ins
---------------------
- `net.dreiucker.DecDescLanguage`

   The definition of the Decision Description Language
   
- `net.dreiucker.DecDescLanguage.ide` 
- `net.dreiucker.DecDescLanguage.tests`
- `net.dreiucker.DecDescLanguage.ui`
- `net.dreiucker.DecDescLanguage.ui.tests`

   Definitions of the UI (aka the Eclipse IDE) behavior for the DDL editor.
   Structure follows the suggested structure for Xtext projects.
   
- `net.dreiucker.DecDescLanguage.ReqIf`
- `net.dreiucker.DecDescLanguage.ReqIf.ui`

   This is the connection between DDL and the requirements documented in RMF / ProR.

  
- `net.dreiucker.DecDescLanguage.tracebility`

   Provides a new view called "Tracebility Matrix" which visualizes the relations between requirements and design decisions.
   
- `net.dreiucker.emfVisitor`
   
   Helper package to avoid code duplication. 
   Provides functionality that allows to traverse EMF resources inside the Eclipse workspace
   
- `net.dreiucker.DecDescLanguage.javadoc`

   Extension for javadoc comments which allows to reference DDL design decisions.

- `net.dreiucker.reqifJavadocExtender`

   Extension for javadoc comments which allows to reference requirements in RMF format.
   
- `net.dreiucker.DecDescLanguage.javadocFeature`
- `net.dreiucker.DecDescLanguage.tracebilityFeature`
- `net.dreiucker.DecDescLanguageFeature`
- `net.dreiucker.DecDescLanguage.UpdateSite`
   
   Definition of the features which can directly be added to an eclipse installation and the
   update site, which is used to install and update these features.

   
   
License
========
See LICENCE.md
