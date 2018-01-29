import akka.actor.{Actor, ActorLogging, ActorPath, ActorRef, ActorSelection, ActorSystem, Props}

object Sys {
  val actorSystem: ActorSystem = ActorSystem("myhelloAkka")
}

class MyActor extends Actor with ActorLogging{

  private var myActor2: ActorRef = _

  override def preStart(): Unit = {
    //这里创建的是新的actor
    //myActor2 = context.actorOf(Props[MyActor2], "myActor2")s
    myActor2 = Sys.actorSystem.actorOf(Props[MyActor2], "myActor2") //创建actor2，这里可以在main方法中创建，然后直接获取
  }

  override def receive: Receive = {
    case CaseActor(x) => log.info(s"CaseActor => $x"); myActor2 ! CaseActor1(x)
    case CaseActor1(x) => log.info(s"CaseActor1 => $x")
    case "hello" => log.info("hello actor....")
    case _ => log.info("null matches")
  }
}

class MyActor2 extends Actor with ActorLogging {

  //private var myActor: ActorRef = _
  var myActor: ActorSelection = _

  override def preStart(): Unit = {
    //myActor = context.parent
    val path: ActorPath = Sys.actorSystem.child("myActor") //  获取myActor的actorPath
    myActor = Sys.actorSystem.actorSelection(path) //根据actorPath获取actor
  }

  override def receive: Receive = {
    case CaseActor(x) => log.info(s"CaseActor => $x");
    case CaseActor1(x) => log.info(s"CaseActor1 => $x"); myActor ! CaseActor(x)
    case _ => log.info("null matches")
  }
}

object TestActor {

  def main(args: Array[String]): Unit = {

    val actor: ActorRef = Sys.actorSystem.actorOf(Props[MyActor], "myActor")
    //val actor1Path: ActorPath = actorSystem.child("myActor1")
    //val actor1: ActorSelection = actorSystem.actorSelection(actor1Path)
    actor ! CaseActor("111")  // 给myActor发送信息

    // 某个actor发送信息只能自己的actor能收到信息
  }
}


case class CaseActor(p: String)

case class CaseActor1(p: String)