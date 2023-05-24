package pj.domain

type Result[A] = Either[DomainError,A]

enum DomainError:

    // Standart Error
    case Error(error: String)

    // XML Erros
    case IOFileProblem(error: String)
    case XMLError(error: String)

    // Simple Types Errors
    // Integers
    case NonNegativeIntError(error: String)
    case PositiveIntError(error: String)
