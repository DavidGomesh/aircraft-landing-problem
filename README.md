
# Milestone 3

The **objective** of milestone three is to develop a code that performs scheduling with a **cost equal to or lower** than the one achieved in milestone one. To accomplish this, a **brute-force algorithm** has been employed. This algorithm exhaustively tests all possible combinations to find the most efficient arrangement of aircraft on runways. By exploring every potential solution, the algorithm ensures the identification of the optimal scheduling arrangement for the given scenario.

## Classes

The classes are utilized to determine the time **separation** between aircraft and to specify the types of aircraft each runway can **accommodate**. An **enum** has been created with six classes to categorize them accordingly. Additionally, an extension includes a function that **calculates the separation** time between these classes.

```scala
enum ClassType:
    case Class1, Class2, Class3, Class4, Class5, Class6
```

## Simple Types

The **Simple Types** were created to be used in classes in order to validate values at the moment they are created. In total, we have four Simple Types.

**AircraftId:** Aircraft Identifier.

```scala
type AircraftId = String

object AircraftId:
    def apply(id: String): Result[AircraftId] = Right(id)
```

**RunwayId:** Runway Identifier.
```scala
type RunwayId = String

object RunwayId:
    def apply(id: String): Result[RunwayId] = Right(id)
```

**NonNegativeInt:** Positive Numbers and Zero.
```scala
type NonNegativeInt = Integer

object NonNegativeInt:
    def apply(n: Int): Result[NonNegativeInt] =
        if n > 0 then Right(n) 
        else Left(NonNegativeIntError("Number must be greater then zero"))
```

**PositiveInt:** Positive Numbers Only.
```scala
type PositiveInt = Integer

object PositiveInt:
    def apply(n: Int): Result[PositiveInt] =
        if n >= 0 then Right(n) 
        else Left(PositiveIntError("Number must be positive"))
```

## Domain

The domain encompasses **Agenda**, **Aircraft**, and **Runway**. The `final case class` was employed to construct these classes, with a focus on including **only the mandatory** fields in their respective constructors. As the algorithm executes, the remaining fields are populated accordingly. The extensions serve to define and **provide methods** for the classes, ensuring their functionality and enhancing their capabilities.

**Agenda**
```scala
final case class Agenda(
    aircrafts: Seq[Aircraft],
    runways: Seq[Runway],
    maxDelayTime: NonNegativeInt
)
```

**Aircraft**
```scala
final case class Aircraft(
    id: AircraftId,
    classType: ClassType,
    target: NonNegativeInt,
    emergency: Option[PositiveInt],
    time: Option[NonNegativeInt]
)
```

**Runway**
```scala
final case class Runway(
    id: RunwayId,
    classes: Seq[ClassType],
    aircrafts: Seq[Aircraft]
)
```

