package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait SignInPageObject extends WebBrowser {
  def SignInButton: IdQuery = id("not_signed_inusaznejotche")
}

trait GoogleAuthPageObject extends WebBrowser {
  def Email          = id("identifierId")
  def Password       = name("password")
  def IdentifierNext = id("identifierNext")
  def PasswordNext   = id("passwordNext")
  def OldEmail       = id("Email")
  def OldPassword    = id("Passwd")

}

trait OfflineAccess extends WebBrowser {
  def AllowOfflineAccess: IdQuery = id("submit_approve_access")
}
