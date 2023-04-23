package pj.domain.model

import pj.domain.simpleTypes.Identifier

final case class Runway(
    id: Identifier, 
    classes: List[Handles] 
)
