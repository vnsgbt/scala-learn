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

  val addMissingSubject = (email: Email) =>
    if (email.subject.isEmpty) email.copy(subject = "No subject") else email
  val checkSpelling = (email: Email) =>
    email.copy(text = email.text.replaceAll("your","you're"))
  val removeOffensiveLanguage = (email: Email) =>
    email.copy(text = email.text.replaceAll("Dynamic typing","**CENSORED"))
  val addAdToFooter = (email: Email) =>
    email.copy(text = email.text + "\nThis email sent via Super Mail")

  val pipeline = Function.chain(Seq(
    addMissingSubject, checkSpelling, removeOffensiveLanguage, addAdToFooter
  ))
}
