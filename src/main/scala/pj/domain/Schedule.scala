package pj.domain

import domain.Runway

final case class Schedule(runways: Seq[Runway]):
    def cost: Int = runways.map(_.cost).sum

    def update(r: Runway): Schedule =
        val i = runways.indexWhere(old => old.id == r.id)
        copy(runways = runways.updated(i, r))