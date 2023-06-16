package pj.domain

import domain.Runway

final case class Plan(runways: Seq[Runway]):
    def cost: Int = Runway.cost(runways)

    def update(r: Runway): Plan =
        val i = runways.indexWhere(old => old.id == r.id)
        copy(runways = runways.updated(i, r))
