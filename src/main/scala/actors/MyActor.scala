package actors

import akka.actor.{Actor, Props}
import akka.event.Logging

class MyActor extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case "test" => log.info("received test")
    case _ => log.info("received unknown message")
  }
}

case class MyValueClass(v: Int) extends AnyVal

class ValueActor(value: MyValueClass) extends Actor {
  override def receive: Receive = {
    case multiplier: Long => sender() ! (value.v * multiplier)
  }
}

class DefaultValueActor(a: Int, b: Int = 5) extends Actor {
  override def receive: Receive = {
    case x: Int => sender() ! (x * b)
  }
}

object Main1 {
  val props1 = Props[MyActor]
  val valueClassProp = Props(classOf[ValueActor], MyValueClass(5)) // Unsupported
  val defaultValueProp1 = Props(classOf[DefaultValueActor], 2.0)
}
