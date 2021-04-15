name := "caseclasses"

version := "0.1"

scalaVersion := "2.12.13"

//Compile / scalacOptions += "-print"

InputKey[Unit]("scalap") := {
  import complete.DefaultParsers._
  val classes = spaceDelimited("<class names>").parsed
  val pathSeparator = java.lang.System.getProperty("path.separator")
  val classpath = "-classpath" :: (Compile / fullClasspath).value.map(_.data).mkString(pathSeparator) :: Nil
  val args = "-private" :: "-verbose" :: classpath ::: classes.toList
  scala.tools.scalap.Main main args.toArray
}

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.7" % "test"
)

addCommandAlias("showPersonMethods", "scalap Person")

Global / onChangedBuildSource := ReloadOnSourceChanges
