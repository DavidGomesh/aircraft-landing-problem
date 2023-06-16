package pj.domain

import domain.*
import domain.Runway.*
import pj.domain.Plan.createPartitions
import pj.domain.BruteForce.bruteForce

object PossibilitiesGenerator:

    // Generate another branch of possibilities in case a scheduled aircraft causes delays.
    def generateOtherPossibilities(r: Runway, ur: Runway, a: Aircraft, rm: Seq[Aircraft], lr: Seq[Runway]): Option[Seq[Runway]] =
        generateOtherPossibilities(r, ur, a).flatMap { r =>
            bruteForce(rm, Runway.update(lr, r))
        }

    // Auxiliary function to generate other possibilities in case of delays on a runway.
    def generateOtherPossibilities(r: Runway, ur: Runway, a: Aircraft) = 
        (r.cost < ur.cost) match
            case true  => generateBetterPossibility(r, a)
            case false => Some(ur)
    
    // Generate the best possibility of an aircraft on a runway
    def generateBetterPossibility(r: Runway, a: Aircraft): Option[Runway] =
        generateAllPossibilities(r, a).minByOption(_.cost)

    // Generate all the possibilities of an aircraft on a runway.
    def generateAllPossibilities(r: Runway, a: Aircraft): Seq[Runway] =
        createPartitions(r.aircrafts).flatMap { case (la, remaining) =>
            for
                r <- assign(a, r.setAircrafts(la)).toOption
                r <- assign(remaining, r).toOption
            yield r
        }


    