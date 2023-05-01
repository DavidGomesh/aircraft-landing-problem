package pj.domain.simpleTypes

import pj.domain.Result
import pj.domain.DomainError.IllegalArgumentError
import scala.annotation.targetName

opaque type Identifier = String

object Identifier:
    def apply(id: String): Result[Identifier] = Right(id)

extension (id: Identifier)
    @targetName("Identifier.to")
    def to: String = id
