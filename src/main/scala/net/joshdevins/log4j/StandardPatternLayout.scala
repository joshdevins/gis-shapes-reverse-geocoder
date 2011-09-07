package net.joshdevins.log4j

import scala.reflect.BeanProperty
import org.apache.log4j.EnhancedPatternLayout
import org.apache.log4j.spi.LoggingEvent

/**
 * A simple extension to the standard EnhancedPatternLayout. Associated exception stack traces are logged on the following lines, preceded
 * by an exclamation point. This allows for easy mechanical extraction of stack traces from log files via standard tools like grep (e.g.,
 * {@code tail -f logula.log | grep '^\!' -B 1}). In order for the stack trace to NOT be output, the property OutputThrowable must be set
 * to false (it's true by default).
 *
 * @author coda
 * @author Josh Devins
 *
 * @see <a href="https://github.com/codahale/logula/blob/development/src/main/scala/com/codahale/logula/Formatter.scala">com.codahale.logula.Formatter</a>
 */
class StandardPatternLayout extends EnhancedPatternLayout {

  @BeanProperty
  var outputThrowable = true

  override def format(event: LoggingEvent) = {

    val msg = new StringBuilder
    msg.append(super.format(event))

    if (outputThrowable && event.getThrowableInformation != null) {

      for (line <- event.getThrowableInformation.getThrowableStrRep) {
        msg.append("! ")
        msg.append(line)
        msg.append("\n")
      }
    }

    msg.toString
  }
}
