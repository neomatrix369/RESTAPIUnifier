RESTAPIUnifier
==============

<b>RESTAPIUnifier</b> - lightweight Java library that brings together RESTful APIs under one roof! Eventually making such libraries available for different programming enviornments like Javascript, PHP, Python, etc...

License
-------
Currently covered by GPLv2. See LICENSE file (https://github.com/neomatrix369/RESTAPIUnifier/blob/master/LICENSE) in root and other directories.

Use the following maven command to format source files with license headers 

<code>  $ mvn license:format </code>
        
Use the following maven command to perform license checks on source files 

<code>  $ mvn license:check </code> 

Use the following maven command to remove license headers from source files 

<code>  $ mvn license:remove </code>


Java 8 Compatibility
--------------------
<b>[Find out how you can also use this logo with your F/OSS projects](https://java.net/projects/adoptopenjdk/pages/TestingJava8)</b>

![Compatibility Badge](https://java.net/downloads/adoptopenjdk/compat.svg)

<b>JSR 353 - JSON-processing API has been used as part of the implementation of this library.</b>

We are supporters of the <b>[@adoptopenjdk](https://twitter.com/adoptopenjdk)</b> and <b>[@adoptajsr](https://twitter.com/adoptajsr)</b> programs!

Purpose and audience
--------------------
To simplify the use and maintainence of dependencies on external or internal APIs (RESTful APIs). Create an abstraction layer between your application and APIs from disparate vendors and increase cohesion, reduce coupling.

Developers, OpenSource committers, API vendors, API Service Providers, etc... will benefit from such a library.

IDE
---
 For the java version of the libary, when loading the project into IDEs like Eclipse or IntelliJ please use the pom.xml file within the java sub-folder and not the pom.xml in the root folder.

Examples implemented using RESTAPIUnifier
-----------------------------------------
<b>Java</b>
- MuzuTV API (search, browse, etc...) - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/muzutv
- Flickr API (search and getRecent) - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/flickr
- Twitter API (search) - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/twitter
- Import.IO API - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/importio
- YQL! API - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/yql
- Heroku API - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/heroku
- Discogs API - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/discogs

<b>C#</b>
- We have a C# implementation of the Java library which has been written based on the test cases in the Java project, without referring to the model code. 
- Examples are on the way. Credit for this task goes to @angiemai . She spent several hours going through the Java tests and writing tests and implementations in C# during the Yahoo! Europe Hackday in London to accomplish the feat!

<b><i>Note: each of the examples require their own settings file containing properties. Also most of them expect you to have your API key atleast to access their services. Information for these requirements are mentioned within comments of each if the source codes.</i></b>

Timeline
--------
* 21 April 2012: discovery and inception of idea at the Google Hackathon
*      May 2012: came across Mashery I/O Wraps Client Library Generator (see below)
*   August 2012: continued improving library at hackathon
* 23 April 2013: came across Import.IO (see below)
* 24 April 2013: further improvements to library - refactoring, tests, tidying up, etc
* 27 April 2013: more improvements, added examples in Java & inception of C# version at Yahoo! Hack Europe: London
* <i>9 May 2013: University student from Brazil is going to use the RestAPIUnifier for his thesis work (with help from the contributors and his professors).</i>
* 20 July  2013: Heroku API implementation, OAuth - first steps in place. <b>Won Heroku "star prize" (1st of 5 winners) at Hacked.IO, O2 Indigo, London.</b>
* 19-22 Sep '13: Socrates UK 2013 - major refactoring, design improvements with help from @Alex (@Mosaic Works), created more opportunities for further collaborations.


Implementations based on the principles behind this idea
--------------------------------------------------------
* Import.IO - imports and reformats data using APIs from other sites/providers: http://Import.IO 
* Mashery I/O Wraps Client Library Generator - 
Native language client library (SDK) builder for platforms using I/O Docs: https://github.com/mashery/io-wraps/

You can...
----------
- ...fork the repo and create a version for your own development environment, and create pull requests.
- ...comment, suggest design improvements, take the idea to the next level. 
- ...combine it with other interesting ideas, libraries, etc...
- ...do the above and submit pull requests.
- ...create new examples of existing APIs which can be accessed through the library.
- ...as far as file formats are concerned, again follow the existing format illustrated by the code-base of the respective languages i.e. for Java - the existing code-base is a good example.
- ...please follow the principles illustrated by the Java project
- ...please use Test-Driven Development (TDD) to spawn out your solutions, ideal a test for each feature / functionality. Behaviour-Driven Development (BDD) based solutions are also welcome.

Getting started
---------------

Fork repo into your own organisation using the github.com interface. For further help with using the github.com site please refer to https://help.github.com/.

Clone the github repo on your system:

<code>  $ git clone git@github.com:<[your user id]>/RESTAPIUnifier.git</code>
  
Note: local repo will be refered to by the name 'origin'.

Create a link from the local repo to the remote repo (upstream = parent repo):

<code>  $ git remote add upstream git@github.com:neomatrix369/RESTAPIUnifier.git</code>

To push changes from local repo to own github repo:
  
<code>  $ git push origin master</code>

To pull changes from <b>own github repo</b> to local repo:
  
<code>  $ git pull origin master</code>

To pull changes from <b>original remote repo</b> to local repo:
  
<code>  $ git pull upstream master</code>
  
For further help with git commands, please refer to one of the git cheatsheets at http://bit.ly/YxvRYV.

Resources
---------
RESTful API - https://en.wikipedia.org/wiki/Representational_state_transfer

Quotes
------
* Alone we can do so little; together we can do so much - Helen Keller
* Long live social coding! - @neomatrix369

