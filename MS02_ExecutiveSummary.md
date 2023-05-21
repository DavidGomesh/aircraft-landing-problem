# Property-based test development methodology
--- 
Properties are general principles that describe the expected behavior of a program. Unlike conventional test suites, properties operate at a more abstract level and generically describe the desired result of an operation. They allow automatically validating whether a program fulfills these properties in a variety of scenarios, generating test cases based on them. This helps identify unwanted behavior or errors that may occur in different situations, providing more comprehensive coverage. Properties are widely used in property-based testing to improve program reliability and robustness.

This project includes defining numerous properties and generators to evaluate a scheduling approach for landing and taking off aircraft on runways.

The project defines generators for random aircraft instances, as well as implements property-based tests for all generations.
Within the project, the 'AttributeGenerator' file is created, in which generators for various attributes used in the properties are defined.
These properties and generators are used in conjunction with a property testing library such as ScalaCheck to produce and validate inputs for the specified properties.

## Properties

### AgendaProperties
It imports necessary `Runway` and `Aircraft` classes and generators from other property suites.

- The `AgendaProperties` object defines a `Runway` object generator and an `Agenda` object generator.The `genAgenda` generator generates an `Agenda` object from a list of `Aircraft` objects ordered by goal integer value.

- The property method specifies a property-based test that determines whether the resulting `Agenda` object has non-empty lists of `Aircraft` and `Runway` objects. The forAll function does the test using a large number of randomly generated inputs.

### AircraftProperties
- The `genAircraft` method generates random `Aircraft` instances by using generators for its characteristics such as Identifier, ClassType, NonNegativeInteger, and Option[AircraftEmergency].

- The property function defines a property-based test that determines whether the ClassType of the created `Aircraft` instances matches one of the ClassType values defined in the `Runway` instances.

- The forAll function is used to perform the test, which generates random `Runway` and `Aircraft` objects and validates the property for each combination. The produced `Aircraft` instance for each test case is included in the test output.

### RunwaysProperties
- We have a `genRunway` function that generates a random `Runway` object.

- The property function implements a property-based test that determines whether the created `Runway` object has a list of runway classes that is not empty. 

- When the test is run, ScalaCheck will build many random `Runway` objects with the `genRunway` generator and see if the property is true for all of them. ScalaCheck will notice a failure and provide a counter example if the property for any created `Runway` object fails.

### ScheduleProperties

> In a valid schedule, every aircraft was assigned a runway

- The property test ensures that every aircraft is allotted a runway in every valid timetable. It accomplishes this by generating random sets of runways and aircrafts, using the schedule function to generate a schedule, and then ensuring that every aircraft in the original set gets allocated to a runway in the resultant schedule.

> Each aircraft should be scheduled for a runway which can handle it

- It generates random runway and aircraft agendas, then schedules the planes on the runways. Finally, it determines if all scheduled aircrafts are assigned to a runway capable of handling their class type.

> An aircraft can never be scheduled before its “target time

- It generates random scenarios of runways and aircrafts using the `genAgenda` generator, schedules the aircrafts on the runways beginning at time 0, and then verifies if all scheduled aircrafts have a target time that is less than or equal to their actual planned time. The test fails if any aircraft is scheduled before its planned time.

> An emergency aircraft should never exceed its allotted time

- It generates random runway and aircraft scenarios, schedules them, and checks to see if all emergency aircrafts in the schedule have a time that is less than or equal to their target time plus their allocated emergency time.

> Schedule the non-emergency aircraft in a 'first come, first served' manner

- It generates random runway and aircraft scenarios, then checks to see if the planned aircrafts are sorted by target time and arrival time, and if both lists are equal.

> Two or more aircraft on a runway should never be assigned simultaneous times

- This code defines a property that checks whether two or more aircraft on a runway are assigned simultaneous times. It generates test cases using a generator called ``genAgenda`` and then verifies that the scheduled aircrafts on the runways do not have overlapping times.

## Generators

### AttributeGenerator

- The `genIdentifier` function generates an Identifier type by constructing an Identifier from a string of alphanumeric characters of a certain length. If the construction fails, so will the generator.

- The `genClassType` method creates a ClassType type by selecting a random byte value from a given range and attempting to construct a ClassType from that value. If the construction fails, so will the generator.

- The `genAircraftTarget` and `genAircraftTime` functions build NonNegativeInteger types by selecting a random integer value within a certain range and attempting to construct a NonNegativeInteger from it. If the construction fails, so will the generator.

- By selecting a random positive integer value and attempting to construct a PositiveInteger from that value, the `genAircraftEmergency` function generates an optional PositiveInteger type. If the construction fails, so will the generator. The PositiveInteger that results is then wrapped in an Option. 

- The `genClassRunway` method generates a list of ClassType types by selecting a random length for the list and then using the `genClassType` function to generate that many ClassType values.