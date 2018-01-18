package com.thoughtworks.expressions

import com.thoughtworks.feature.Factory.Factory0
import com.thoughtworks.feature.Factory.{Factory1, Factory2, Factory3, inject}
import shapeless.Lazy

/**
  * @author 杨博 (Yang Bo)
  */
trait Expressions {

  object Operator0 {
    implicit def operator0[Out](implicit factory: Factory1[DebuggingInformation, Out]): Operator0[Out] =
      new Operator0[Out] {
        def apply()(implicit debuggingInformationFacotry: ImplicitlyAppliedFactory[DebuggingInformation]): Out = {
          factory.newInstance(debuggingInformation)
        }
      }
  }

  trait Operator0[Out] {
    def apply()(implicit debuggingInformationFacotry: ImplicitlyAppliedFactory[DebuggingInformation]): Out
  }

  object Operator1 {
    implicit def operator1[Operand0, Out](
        implicit factory: Factory2[DebuggingInformation, Operand0, Out]): Operator1[Operand0, Out] =
      new Operator1[Operand0, Out] {
        def apply(operand0: Operand0)(implicit debuggingInformationFacotry: ImplicitlyAppliedFactory[DebuggingInformation]): Out = {
          factory.newInstance(debuggingInformation, operand0)
        }
      }
  }

  trait Operator1[Operand0, Out] {
    def apply(operand0: Operand0)(implicit debuggingInformationFacotry: ImplicitlyAppliedFactory[DebuggingInformation]): Out
  }

  object Operator2 {
    implicit def operator2[Operand0, Operand1, Out](
        implicit factory: Factory3[DebuggingInformation, Operand0, Operand1, Out]): Operator2[Operand0, Operand1, Out] =
      new Operator2[Operand0, Operand1, Out] {
        def apply(operand0: Operand0, operand1: Operand1)(
            implicit debuggingInformationFacotry: ImplicitlyAppliedFactory[DebuggingInformation]): Out = {
          factory.newInstance(debuggingInformation, operand0, operand1)
        }
      }
  }

  trait Operator2[Operand0, Operand1, Out] {
    def apply(operand0: Operand0, operand1: Operand1)(
        implicit debuggingInformationFacotry: ImplicitlyAppliedFactory[DebuggingInformation]): Out
  }

  protected trait TermApi extends ExpressionApi {
    val `type`: Type
    type Self = `type`.TypedTerm
    val debuggingInformation: DebuggingInformation
    override def name: String = debuggingInformation.name.value

    def self: Self
  }

  /** @template */
  type Term <: (Expression with Any) with TermApi

  protected trait TypeApi extends ExpressionApi { this: Type =>
    override def name: String

    protected trait TypedTermApi extends TermApi { this: TypedTerm =>
      val `type`: TypeApi.this.type = TypeApi.this
      def self: Self = this
    }

    type TypedTerm <: (Term with Any) with TypedTermApi

    /** @template */
    type Identifier <: TypedTerm

    // FIXME: Some identifiers need additional settings,
    // so the arity may be not nullary,
    // and this method will be removed then.
    @inject def Identifier: Operator0[Identifier]

  }

  /** @template */
  type Type <: (Expression with Any) with TypeApi

  @inject def debuggingInformationFactory: ImplicitlyAppliedFactory[DebuggingInformation]

  val debuggingInformation: DebuggingInformation = debuggingInformationFactory.newInstance()

  type DebuggingInformation <: Debugging.Name
  protected trait ExpressionApi {
    def name: String
  }
  type Expression <: ExpressionApi

}
