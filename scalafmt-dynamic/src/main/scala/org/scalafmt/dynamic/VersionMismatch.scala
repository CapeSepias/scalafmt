package org.scalafmt.dynamic

case class VersionMismatch(obtainedVersion: String, expectedVersion: String)
    extends Exception(
      s"Scalafmt version mismatch. Version in .scalafmt.conf is '$obtainedVersion' and running version is '$expectedVersion'."
    )
