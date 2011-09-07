package net.joshdevins.gis.geocoder.spray

import akka.event.EventHandler._
import akka.actor._
import com.codahale.logula.Logging
import com.codahale.logula.Log

object Log4jEventHandler {
  val format = "[%s] [%s] [%s]"
}

/**
 * An Akka event handler pushing to log4j via logula.
 *
 * @author Josh Devins
 * @see <a href="https://github.com/jboner/akka/blob/master/akka-slf4j/src/main/scala/akka/event/slf4j/SLF4J.scala">akka.event.slf4j.SLF4J</a>
 */
class Log4jEventHandler extends Actor {

  val log = Log.forName("akka")

  def receive = {
    case event @ Error(cause, instance, message) =>
      log.error(cause, Log4jEventHandler.format, instanceName(instance), event.thread.getName, message.asInstanceOf[String])

    case event @ Warning(instance, message) =>
      log.warn(Log4jEventHandler.format, instanceName(instance), event.thread.getName, message.asInstanceOf[String])

    case event @ Info(instance, message) =>
      log.info(Log4jEventHandler.format, instanceName(instance), event.thread.getName, message.asInstanceOf[String])

    case event @ Debug(instance, message) =>
      log.debug(Log4jEventHandler.format, instanceName(instance), event.thread.getName, message.asInstanceOf[String])

    case event => log.debug("[%s]", event.toString)
  }

  private def instanceName(instance: AnyRef): String = instance match {
    // case a: ActorRef => a.getActorClassName
    case _ => instance.getClass.toString
  }
}
