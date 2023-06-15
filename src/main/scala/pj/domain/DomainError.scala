package pj.domain

type Result[A] = Either[DomainError,A]

enum DomainError:

    // Standart Error
    case Error(error: String)

    // XML Erros
    case IOFileProblem(error: String)
    case XMLError(error: String)

    case RepeatedAircraftId(error: String)
    case RepeatedRunwayId(error: String)

    // Simple Types Errors
    // Integers
    case NonNegativeIntError(error: String)
    case PositiveIntError(error: String)

    case ScheduleError(error: String)
    case RunwayNotCompatible(error: String)
    case OperationTimeWindow(error: String)

    case IllegalArgumentError(error: String)
