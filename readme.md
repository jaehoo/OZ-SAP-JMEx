OZ SAP JMEx (Java Message Extractor)
===================
This project is a tool to get the messages in xml format (payload) from SAP with the JCO connector. You can extract messages from your SAP clients, for example I use this with SAP PI to get multiple messages.

#####Build
 -----------------------------
 1. Install native library for your operating system (SAP Note [1077727](https://service.sap.com/sap/support/notes/1077727))
 2. Execute top maven pom.xml:
	 	mvn clean package

######Note: if you need download another connector you can get it from [SAP Service Marketplace](https://service.sap.com/connectors)

Credits
-------

![thoughtbot](https://lh6.googleusercontent.com/-gXFiyKSSZ4E/UewkL6Eez8I/AAAAAAAADpg/Phifd0oafkc/s288/OZ%2520logo.png)

This project is maintained by [Orbital Zero, inc](http://www.orbitalzero.com/community)
and people like you :) . Thank you!

License
-------

**This project** is copyright 2015 by Jaehoo (Alberto Sánchez) and Orbital Zero,inc. It is free software, and may be redistributed under the terms specified in the `LICENSE` file.

The names and logos for this code are trademarks of their respective owners, which are in no way associated or affiliated with Orbital Zero.
Product names and logos are used solely for the purpose to show specific examples of software development, not for comercial use. Use of these names does not imply any co-operation or endorsement.