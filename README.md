RESTAPIUnifier
==============

RESTAPIUnifier - lightweight Java library that brings together RESTful APIs under one roof! Eventually making such libraries available for different programming enviornments like Javascript, PHP, Python, etc...

License
-------
Currently covered by GPLv2. See LICENSE file (https://github.com/neomatrix369/RESTAPIUnifier/blob/master/LICENSE) in root and other directories.

Purpose and audience
--------------------
To simplify the use and maintainence of dependencies on external or internal APIs (RESTful APIs). Create an abstraction layer between your application and APIs from disparate vendors and increase cohesion, reduce coupling.

Developers, OpenSource committers, API vendors, API Service Providers, etc... will benefit from such a library.

Examples implemented using RESTAPIUnifier
-----------------------------------------
<b>Java</b>
- MuzuTV API (search, browse, etc...) - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/muzu_tv_api
- Flickr API (search and getRecent) - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/flickr_api
- Twitter API (search) - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/twitter_api
- Import.IO API - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/importio_api (underway)
- YQL! - https://github.com/neomatrix369/RESTAPIUnifier/tree/master/java/src/main/java/org/neomatrix369/examples/yql_api

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

