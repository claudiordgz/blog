import org.scalatest.FreeSpec

import github._

class SetSpec extends FreeSpec {

  "A Request" - {
    "when empty" - {
      "should have size 0" in {
          assert(Set.empty.size == 0)
      }

      "should produce NoSuchElementException when head is invoked" in {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}
