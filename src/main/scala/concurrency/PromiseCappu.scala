package concurrency

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future, Promise}
//import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import PromiseCappu.executionContext

object PromiseCappu extends App {

  val executorSvc = Executors.newFixedThreadPool(4)
  implicit val executionContext = ExecutionContext.fromExecutor(executorSvc)

  val taxCutF: Future[TaxCut] = Government.redeemCampaignPledge()

  println("Now that they're elected, let's see if they remember their promises...")
  Thread.sleep(4000)

  taxCutF.onComplete {
    case Success(TaxCut(reduction)) =>
      println(s"A miracle! They really cut our taxes by $reduction percentage points!")
    case Failure(ex) =>
      println(s"They broke their promises! Again! Because of a ${ex.getMessage}")
  }
  Thread.sleep(8000)
}

object Government {
  def redeemCampaignPledge (): Future[TaxCut] = {
    val p = Promise[TaxCut]
    Future {
      println("Starting the new legislative period.")
      Thread.sleep(2000)
      p.failure(LameExcuse("Global economy crisis"))
      println("We reduced taxes! You must reelect us!")
    }
    p.future
    
  }
}

case class TaxCut (reduction: Int)
case class LameExcuse(excuse: String) extends Exception(excuse)