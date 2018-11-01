package actors

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

class RecommendActorInit {

}

object DemoActor {

  def props(magicNumber: Int): Props = Props(new DemoActor(magicNumber))
}

class DemoActor(magicNumber: Int) extends Actor {
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case x: Int => {
      log.info("received sd")
      sender() ! (x + magicNumber)
    }
  }
}

class SomeOtherActor extends Actor {
  val log = Logging(context.system, this)

  val demo = context.actorOf(DemoActor.props(42), "demo")
  demo ! 53
  override def receive: Receive = {
    case _ => log.info("recived some")
  }
}

object SomeOtherActor {
  def props = Props[SomeOtherActor]
}

object RecommendActorInit {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("first")
    system.actorOf(SomeOtherActor.props)
  }
}
