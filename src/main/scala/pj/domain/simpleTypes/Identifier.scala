package pj.domain.simpleTypes

import pj.domain.Result
import pj.domain.DomainError.IllegalArgumentError

opaque type Identifier = String

object Identifier:
    def apply(id: String): Result[Identifier] = Right(id)
