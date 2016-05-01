import scalatest.UnitSpec
import higherorder.StayDry
import higherorder.StayDry.{Email, EmailFilter}

/**
  * Created by steve on 4/30/16.
  */

class StayDryTest extends UnitSpec {

  val emailFilter: EmailFilter = StayDry.every(
    StayDry.notByOneOf(Set("johndoe@example.com")),
    StayDry.minSize(100),
    StayDry.maxSize(10000)
  )

  val mails = Email (
    subject = "It's me again, your stalker friend!",
    text = "How are you?",
    sender = "jooDov@ex.coo",
    recipient = "me@me.me") :: Nil

  assert(StayDry.newMailForUser(mails, emailFilter) == Seq.empty)
}
