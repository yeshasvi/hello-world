name := "ScalaMongoDB"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "com.novus" %% "salat" % "1.9.5",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "me.lessis" %% "base64" % "0.1.0"
)     

resolvers += "softprops-maven" at "http://dl.bintray.com/content/softprops/maven"

libraryDependencies += "me.lessis" %% "base64" % "0.1.0"

play.Project.playScalaSettings
