import akka.actor.{Actor, ActorLogging, ActorPath, ActorRef, ActorSelection, ActorSystem, Props}

object Sys {
  val actorSystem: ActorSystem = ActorSystem("myhelloAkka")
}

class MyActor extends Actor with ActorLogging{

  //private var myActor2: ActorRef = _
  private var myActor2: ActorSelection = _

  override def preStart(): Unit = {
    //这里创建的是新的actor
    //myActor2 = context.actorOf(Props[MyActor2], "myActor2")s
    val path: ActorPath = Sys.actorSystem.child("myActor2") //  获取myActor的actorPath
    myActor2 = Sys.actorSystem.actorSelection(path) //根据actorPath获取actor
  }

  override def receive: Receive = {
    case CaseActor(x) => {
      log.info(s"MyActor CaseActor => $x")
      myActor2 ! CaseActor1(x);
      sender ! CaseActor1(x) //sender 再次发送消息,sender起作用必须使用actorRef.tell(msg,actor)
    }
    case CaseActor1(x) => log.info(s"MyActor CaseActor1 => $x");
    case "hello" => log.info("MyActor hello actor....")
    case _ => log.info("MyActor null matches")
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
    case CaseActor(x) => log.info(s"MyActor2  CaseActor => $x"); myActor ! CaseActor(x)
    case CaseActor1(x) => log.info(s"MyActor2 CaseActor1 => $x");
    case _ => log.info("MyActor2 null matches")
  }
}

object TestActor {

  def main(args: Array[String]): Unit = {

    val actor: ActorRef = Sys.actorSystem.actorOf(Props[MyActor], "myActor")
    val myActor2: ActorRef = Sys.actorSystem.actorOf(Props[MyActor2], "myActor2") //创建actor2

    //val actor1Path: ActorPath = actorSystem.child("myActor1")
    //val actor1: ActorSelection = actorSystem.actorSelection(actor1Path)
    //val re = actor ! CaseActor("111")  // 给myActor发送信息
    actor.tell(CaseActor("111"),myActor2)
    // 某个actor发送信息只能自己的actor能收到信息
  }
}


case class CaseActor(p: String)

case class CaseActor1(p: String)