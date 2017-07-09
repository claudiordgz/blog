name := "blog-service"

version in ThisBuild := "0.0.1"

scalaVersion in ThisBuild := "2.12.2"

organization in ThisBuild := "com.claudiordgz"

// Custom keys for this build.

val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")

val makeVersionProperties = taskKey[Seq[File]]("Creates a version.properties file we can find at runtime.")

// Common settings/definitions for the build

def BlogServiceProject(name: String): Project = (
  Project(name, file(name))
  settings(
    libraryDependencies ++= Seq(
        "org.scalactic" %% "scalactic" % "3.0.1",
        "org.scalatest" %% "scalatest" % "3.0.1" % "test",
        "com.typesafe.akka" %% "akka-http" % "10.0.9",
        "com.typesafe.akka" %% "akka-http-testkit" % "10.0.9" % Test
    )
  )
)

gitHeadCommitSha in ThisBuild := Process("git rev-parse HEAD").lines.head


// Projects in this build

lazy val common = (
  BlogServiceProject("common")
  settings(
    makeVersionProperties := {
      val propFile = (resourceManaged in Compile).value / "version.properties"
      val content = "version=%s" format (gitHeadCommitSha.value)
      IO.write(propFile, content)
      Seq(propFile)
    },
    resourceGenerators in Compile <+= makeVersionProperties
  )
)

lazy val dynamo = (
  BlogServiceProject("dynamo")
  dependsOn(common)
  settings()
)

lazy val github = (
  BlogServiceProject("github")
  dependsOn(common)
  settings()
)
