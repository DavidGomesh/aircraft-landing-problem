# Milestone 3

The **objective** of milestone three is to develop a code that performs scheduling with a **cost equal to or lower** than the one achieved in milestone one. To accomplish this, a **brute-force algorithm** has been employed. This algorithm exhaustively tests all possible combinations to find the most efficient arrangement of aircraft on runways. By exploring every potential solution, the algorithm ensures the identification of the optimal scheduling arrangement for the given scenario.

## Classes

The classes are utilized to determine the time **separation** between aircraft and to specify the types of aircraft each runway can **accommodate**. An **enum** has been created with six classes to categorize them accordingly.

```scala
enum ClassType:
    case Class1, Class2, Class3, Class4, Class5, Class6
```

## Separation

The **Separation** contains three methods: **delay**, **minTime**, **separation**. And that functions provides a way to calculate the minimum needed time to separate two aircrafts based on their classes categories and the present schedule aircraft arriving on a runway.

```scala
object Separation:

    def delay(a: Aircraft, r: Runway): Int =
        minTime(a, r) - a.target
```

```scala
    def minTime(a: Aircraft, r: Runway): Int =
        r.aircrafts.foldLeft(0)((minTime, scheduled) =>
            val time = scheduled.getTime + separation(scheduled, a)
            if time >= minTime then time else minTime
        )
```

```scala
    def separation(al: Aircraft, at: Aircraft): Int =
        val cl = al.classType
        val ct = at.classType
        separation(cl, ct)

```

```scala
    def separation(cl: ClassType, ct: ClassType): Int = (cl, ct) match
        case (Class1, Class1) => 82
        case (Class1, Class2) => 69
        case (Class1, Class3) => 60
        case (Class1, _)      => 75

        case (Class2, Class1) => 131
        case (Class2, Class2) => 69
        case (Class2, Class3) => 60
        case (Class2, _)      => 75

        case (Class3, Class1) => 196
        case (Class3, Class2) => 157
        case (Class3, Class3) => 96
        case (Class3, _)      => 75

        case (Class4, _) => 60
        case (Class5, _) => 60

        case (Class6, Class1 | Class2 | Class3) => 60
        case (Class6, Class4 | Class5) => 120
        case (Class6, _) => 90
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
type NonNegativeInt = Int

object NonNegativeInt:
    def apply(n: Int): Result[NonNegativeInt] =
        if n > 0 then Right(n)
        else Left(NonNegativeIntError("Number must be greater then zero"))
```

**PositiveInt:** Positive Numbers Only.

```scala
type PositiveInt = Int

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
    runways: Seq[Runway]
)
```

**Aircraft**

```scala
final case class Aircraft(
    iid: AircraftId,
    classType: ClassType,
    target: NonNegativeInt,
    maxTime: PositiveInt,
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

## Solution Algorithm

For this milestone, three main algorithms were used:

**Brute Force Algorithm**

The `bruteForce` method implements a brute force algorithm to find the optimal solution for aircraft scheduling. It performs an exhaustive search by testing all possible assignments of aircraft to runways, considering constraints to optimize or reduce the search space, and minimizing the total scheduling cost.

The brute force algorithm starts with a list of aircraft `la` and a list of runways `lr`. It iterates over each aircraft in the list, attempting to assign it to each available runway.

For each aircraft-to-runway assignment, the algorithm checks if it violates any constraints, such as the aircraft's operation time window or compatibility between aircraft type and runway. If the assignment does not violate any constraints, the algorithm updates the runway list with the assigned aircraft and recursively calls the `bruteForce` method for the remaining aircraft.

The algorithm continues attempting to assign aircraft to runways until all aircraft have been allocated or no valid assignment is possible. It returns the sequence of runways that achieves the minimum total scheduling cost among all possible combinations.

**Combination Algorithm**

The combination algorithm is used in the `createAllPossibilities` method to generate all possible combinations of aircraft scheduling for a specific runway.

This algorithm utilizes the concept of partitions of already scheduled aircraft on a runway. It iterates over the partitions, which consist of a sequence of already assigned aircraft and a sequence of remaining aircraft.

For each partition, the algorithm assigns the current aircraft (the one being considered) to the sequence of already assigned aircraft, generating a new combination. This combination is added to the list of possible runways, provided it does not violate any constraints.

The algorithm continues iterating over all partitions and constructs all possible combinations of aircraft scheduling. It returns the resulting list of runways containing all generated combinations.

**Recursive Algorithm**

The `bruteForce` and `loop` methods utilize recursion to systematically traverse the lists of aircraft and runways.

The `bruteForce` method is primarily responsible for recursively calling the `loop` method for each aircraft in the list. It passes the remaining aircraft list and the updated runway list as arguments. The recursion occurs until all aircraft have been assigned or no valid assignment is possible.

`loop`, on the other hand, receives as an argument the list of aircraft groups and the list of runways. It iterates over the aircraft groups and recursively calls the `bruteForce` method for each group, passing the updated runway list as an argument. The recursion runs until all aircraft groups have been processed.

The aircraft are divided into groups of 10 aircraft each. For example, if the original aircraft list has 60 aircraft, there will be 6 groups of 10 aircraft. This approach efficiently reduces the execution time of the brute force algorithm, making it more efficient and accelerating the search for a solution.

These algorithms are combined to perform an exhaustive search of all possible scheduling combinations, aiming to find the optimal solution that minimizes the total scheduling cost.
