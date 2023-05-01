## Development methodology

To develop the algorithm that solves the problem addressed, the following steps were used:

- We received a list of aircraft already scheduled, a list of aircraft not yet scheduled, a list
  of emergency aircraft not yet scheduled and a list of runwaysOnce this is done, we try to schedule
  the first aircraft from the list of unscheduled aircraft. To schedule an aircraft, we first calculate
  its delay ("F#02") on all runways and store it in an ordered list from the shortest delay to the longest.

- So we take the first runway on the list and try to schedule the aircraft there (F#04). For this, it
  is necessary to verify that placing that aircraft on that runway does not result in the impediment of
  scheduling the next emergency aircraft ("F#03"). If it does not interfere with the aircraft in an emergency,
  that aircraft can be inserted into that runway without problems. And we move on to the next aircraft If it interferes
  with the aircraft in emergency, we repeat the process trying to assign the aircraft to the next runway.
  If you run out of runways, it means that the next emergency aircraft needs to be scheduled before the current aircraft.
  So we try to schedule the emergency aircraft like a normal aircraft, with the exception of not being able to pass the
  emergency time. To do this, we place the aircraft in emergency at the first position in the list of aircraft not yet scheduled.

- Function to calculate delay of an aircraft on a runway. We received the aircraft in question and the runway.
  First we get the target of the aircraft in question. Then we go through each aircraft on the runway and calculate
  the delay it generates.
  To calculate the delay: - We took the aircraft team from the runway - We add with the gap for the aircraft in question - From the result we subtract the target of the aircraft in question - The result will then be the delay - The delay that will be imported will be the biggest delay, as it is what determines the time of the aircraft in question (target + delay).

- Function to insert an aircraft on a runway:
  - We received the aircraft and the runway;
  - First calculates whether the delay of the aircraft on the runway;
  - Then add the aircraft target with the delay to get the time;
  - Converts from Aircraft to AircraftResp and inserts into the runways.

## Models

The Agenda class was created as follows:

- A list of aircraft ("aircrafts") of type "Aircraft"
- A list of runways of type "Runway"
- A maximum allowed delay time ("maximumDelayTime") of type "PositiveInteger"

???

## Tests

- The ScalaTest library was used to develop the application's Functional and Unit tests. Among the developed tests,
  tests were made for the SimpleTypes and for the functions separately. Subsequently, the XML files were tested, giving
  the algorithm an input file and waiting for a certain output file to confirm whether the developed solution is
  generating the aircraft scheduling correctly.
