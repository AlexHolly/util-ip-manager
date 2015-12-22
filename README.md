# How to use

1. Clone this project
2. ```cd [projectfolder]```
3. ```sbt publish-local```
4. Go to your project build.sbt and add ```"de.alexholly.util" %% "ip-manager" % "0.0.2-SNAPSHOT"```

# Imports
	
```import de.alexholly.util.IPManager```

# Methods

getAllIPs() : List[String]

getHostname(): String

getLocalIP(): String

isIPv4(ip: String): Boolean

getInternetIP(): String 

