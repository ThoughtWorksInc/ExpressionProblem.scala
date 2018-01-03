dependsOn(
  ProjectRef(file("feature.scala"), "PartialApplyJVM"),
  ProjectRef(file("feature.scala"), "ImplicitApplyJVM"),
  ProjectRef(file("feature.scala"), "FactoryJVM")
)

libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3"

libraryDependencies += "com.dongxiguo" %% "fastring" % "0.3.1"

libraryDependencies += "com.lihaoyi" %% "sourcecode" % "0.1.4"

libraryDependencies += "org.lwjgl" % "lwjgl-opencl" % "3.1.5"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test

scalacOptions += "-Xexperimental"

scalacOptions += "-Ypartial-unification"

scalacOptions += "-explaintypes"

