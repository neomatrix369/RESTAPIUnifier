APIUnifier
==========

APIUnifier - lightweight Java library that brings together all the APIs of various formats under one roof! Eventually making such libraries available for different programming enviornments like Javascript, PHP, Python, etc...

Purpose and audience
--------------------
To simplify the use and maintainence of dependencies on external or internal APIs (RESTful or otherwise). Create an abstraction layer between your application and APIs from disparate vendors and increase cohesion, reduce coupling.

Developers, OpenSource committers, API vendors, API Service Providers, etc... will benefit from such a library.

Examples implemented using APIUnifier
-------------------------------------
<b>Java</b>
- MuzuTV API (search, browse, etc...) - https://github.com/neomatrix369/APIUnifier/tree/master/java/src/examples/GoogleTVHackathon
- Flickr API (search and getRecent) - https://github.com/neomatrix369/APIUnifier/tree/master/java/src/examples/FlickrAPI
- Twitter API (search) - https://github.com/neomatrix369/APIUnifier/tree/master/java/src/examples/TwitterAPI
- Import.IO API - https://github.com/neomatrix369/APIUnifier/tree/master/java/src/examples/ImportIOAPI (underway)
- YQL! - https://github.com/neomatrix369/APIUnifier/tree/master/java/src/examples/YQLAPI

<b>C#</b>
We have a C# implementation of the Java library which has been written based on the test cases in the Java project, without referring to the model code. Examples are on the way. Credit goes to @angiemae for the tasks. 
She spent several hours during the Yahoo! Europe Hackday at London to accomplish the feat!

Timeline
--------
* 21 April 2012: discovery and inception of idea at the Google Hackathon
*      May 2012: came across Mashery I/O Wraps Client Library Generator (see below)
*   August 2012: continued improving library at hackathon
* 23 April 2013: came across Import.IO (see below)
* 24 April 2013: further improvements to library - refactoring, tests, tidying up, etc
* 27 April 2013: more improvements, added examples in Java & inception of C# version at Yahoo! Hack Europe: London

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
- ...please follow the principles behind the Java project
- ...please use Test-Driven Development (TDD) to spawn out your solutions, ideal a test for each feature / functionality. Behaviour-Driven Development (BDD) based solutions are also welcome.
