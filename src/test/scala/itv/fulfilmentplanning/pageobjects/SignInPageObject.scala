package itv.fulfilmentplanning.pageobjects

import org.scalatest.selenium.WebBrowser

trait SignInPageObject extends WebBrowser {
  def SignInButton: IdQuery = id("not_signed_inusaznejotche")
}

trait GoogleAuthPageObject extends WebBrowser {
  def Email: IdQuery    = id("Email")
  def Password: IdQuery = id("Passwd")
}

trait OfflineAccess extends WebBrowser {
  def AllowOfflineAccess: IdQuery = id("submit_approve_access")
}
