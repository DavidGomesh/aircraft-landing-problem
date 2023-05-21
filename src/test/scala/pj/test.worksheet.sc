
import org.scalacheck.Gen
import pj.domain.Properties.AircraftProperties.genAircraftTimeNew
import pj.domain.Properties.AircraftProperties.genAircraftEmergency
import pj.domain.Properties.AircraftProperties.genAircraftTarget
import pj.domain.Properties.AircraftProperties.genAircraftClass
import pj.domain.Properties.AircraftProperties.genAircraftId
import pj.domain.Properties.RunwaysProperties.genRunway
import pj.domain.Properties.AircraftProperties.genAircraft
import pj.domain.Properties.AircraftProperties
import pj.domain.Properties.RunwaysProperties


val num: Option[Int] = Some(10)
val n1 = num.fold(0)(n => n)

val n = 1 + n1


