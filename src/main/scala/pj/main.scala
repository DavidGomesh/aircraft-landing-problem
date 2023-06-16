

// import domain.Aircraft
// import domain.Runway
// import simpleTypes.identifier.RunwayId
// import enumerate.ClassType.*
// import pj.domain.schedule.ScheduleMS03.schedule

// @main
// def main =
//     val la = List(
//         Aircraft("A1", Class5, 0),
//         Aircraft("A2", Class2, 8),
//         Aircraft("A4", Class4, 67),
//         Aircraft("A5", Class2, 155),
//         Aircraft("A6", Class2, 179),
//         Aircraft("A7", Class3, 221),
//         Aircraft("A3", Class4, 271),
//         Aircraft("A8", Class6, 296),
//         Aircraft("A9", Class2, 319),
//         Aircraft("A10", Class5, 363),
//         Aircraft("A11", Class6, 380),
//         Aircraft("A12", Class1, 435),
//         Aircraft("A13", Class6, 497),
//         Aircraft("A14", Class3, 531),
//         Aircraft("A15", Class5, 563),
//         Aircraft("A16", Class5, 573),
//         Aircraft("A17", Class4, 588),
//         Aircraft("A18", Class5, 670),
//         Aircraft("A19", Class6, 677),
//         Aircraft("A20", Class5, 682),
//         Aircraft("A21", Class3, 686),
//         Aircraft("A22", Class6, 693),
//         Aircraft("A23", Class2, 764),
//         Aircraft("A24", Class2, 816),
//         Aircraft("A25", Class1, 922),
//         Aircraft("A26", Class3, 1018),
//         Aircraft("A27", Class5, 1039),
//         Aircraft("A28", Class5, 1066),
//         Aircraft("A29", Class6, 1186),
//         Aircraft("A30", Class3, 1199),
//         Aircraft("A31", Class3, 1208),
//         Aircraft("A32", Class1, 1238),
//         Aircraft("A33", Class2, 1267),
//         Aircraft("A34", Class2, 1287),
//         Aircraft("A35", Class6, 1306),
//         Aircraft("A36", Class3, 1312),
//         Aircraft("A37", Class6, 1378),
//         Aircraft("A38", Class3, 1418),
//         Aircraft("A39", Class4, 1454),
//         Aircraft("A40", Class1, 1501),
//         Aircraft("A41", Class6, 1539),
//         Aircraft("A42", Class3, 1679),
//         Aircraft("A43", Class2, 1693),
//         Aircraft("A44", Class5, 1769),
//         Aircraft("A45", Class1, 1781),
//         Aircraft("A46", Class4, 1797),
//     )

//     val lr = List(
//         Runway("R1", List(Class1, Class2, Class3), List()),
//         Runway("R2", List(Class1, Class2, Class3, Class4, Class5, Class6), List()),
//     )

//     schedule(la, lr) match
//     case Right(lr) =>
//         lr.flatMap(r => r.aircrafts).sortBy(_.time) foreach println
//         // lr.foreach(r => 
//         //     println("====================================")
//         //     r.aircrafts foreach println
//         // ) 