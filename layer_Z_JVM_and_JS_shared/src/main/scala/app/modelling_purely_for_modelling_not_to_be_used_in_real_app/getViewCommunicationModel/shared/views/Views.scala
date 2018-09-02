package app.modelling_purely_for_modelling_not_to_be_used_in_real_app.getViewCommunicationModel.shared.views

abstract class View {
  type Par <: Parameter
  type Res <: Result
}
trait Result
trait Parameter

// Random UUID: 99b7907fbdbd4554b2c7f4634cf2a376
// commit 3a7d0bc1c81a6f3d8e6aa3b6d286e8e0291af5d5
// Date: Tue Aug 14 08:04:25 EEST 2018

object View1_HolderObject {

  // nemm kell parazni,az osszes View nev az itt van definialva

  case class View1_Par(s: String ) extends Parameter

  case class View1_Res(res: String ) extends Result

  class View1 extends View {
    type Par = View1_Par
    type Res = View1_Res
  }
}

object View2_HolderObject {

  case class View2_Par(i: Int ) extends Parameter

  case class View2_Res(res: Int ) extends Result

  class View2 extends View {
    type Par = View2_Par
    type Res = View2_Res
  }
}
