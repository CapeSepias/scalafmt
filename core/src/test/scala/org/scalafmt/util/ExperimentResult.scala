package org.scalafmt.util

import scala.meta.parsers.common.ParseException

sealed abstract class ExperimentResult(fileUrl: String) {
  def key: String
}
object ExperimentResult {
  case class Success(fileUrl: String, nanos: Long) extends ExperimentResult(fileUrl) {
    override def key = "Success"
  }
  case class Timeout(fileUrl: String) extends ExperimentResult(fileUrl) {
    override def key = "Formatter timed out"
  }
  case class Skipped(fileUrl: String) extends ExperimentResult(fileUrl) {
    override def key = "Ignored, scalac won't parse"
  }
  case class UnknownFailure(fileUrl: String, e: Throwable)
    extends ExperimentResult(fileUrl) {
    override def key: String = e.getClass.getName

    override def toString: String = s"$fileUrl $e"
  }
  case class ParseErr(fileUrl: String, e: ParseException)
    extends ExperimentResult(fileUrl) {
    override def key: String = e.getClass.getName

    def err: String = e.getMessage.replaceAll(" at .*", "")

    def lineNumber = e.pos.point.line

    def content = s"cols:${e.pos.start.column}-${e.pos.end.column}"

    override def toString: String = s"$fileUrl#L${e.pos.start.line + 1} $cols"

    def cols = s"cols:${e.pos.start.column}-${e.pos.end.column}"
  }
}

