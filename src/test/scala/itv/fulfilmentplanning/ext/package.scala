package itv.fulfilmentplanning

package object ext {

  import org.scalactic._

  val withoutWhiteSpaces: Uniformity[String] =
    new AbstractStringUniformity {
      def normalized(s: String): String = s.replaceAll(" ", "")

      override def toString: String = "withoutWhiteSpaces"
    }
}
