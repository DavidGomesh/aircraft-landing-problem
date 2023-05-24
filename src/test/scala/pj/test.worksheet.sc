import domain.properties.AircraftsProperties
import domain.properties.RunwaysProperties.genRunways
import domain.properties.AircraftsProperties.*

// genClassType.sample
// genClassType.sample
// genClassType.sample

val n = for
        r <- genRunways
        a <- genAircrafts(r)
    yield a

n.sample
n.sample
n.sample
