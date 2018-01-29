package com.thoughtworks.expressions.tree

import java.util.IdentityHashMap

import scala.collection.mutable.ArrayBuffer

/**
  * @author 杨博 (Yang Bo)
  */
trait StructuralTrees extends Trees {

  // TODO: enable sbt-example for this plug-in.
  /** The key type for [[kernelCache]], which structurally performs [[hashCode]] and [[equals]] on [[term]].
    *
    * @example Given two [[Term]]s that contains the same operations but different [[Type#Identifier]],
    *
    *          {{{
    *          val a = float.Identifier()
    *          val b = float.Identifier()
    *          val x = float.Identifier()
    *          val y = float.Identifier()
    *          val aPlusB = a + b
    *          val xPlusY = x + y
    *          }}}
    *
    *          then they should not equal to each other,
    *
    *          {{{
    *          aPlusB shouldNot be(xPlusY)
    *          }}}
    *
    *          However, when wrappingThem as [[StructuralKey]],
    *
    *          {{{
    *          val structuralAPlusB = StructuralKey(aPlusB)
    *          val structuralXPlusY = StructuralKey(xPlusY)
    *          }}}
    *
    *          then they should equal to each other,
    *
    *          {{{
    *          structuralAPlusB should be(structuralXPlusY)
    *          }}}
    *
    *          and they should have the same [[hashCode]].
    *
    *          {{{
    *          structuralAPlusB.hashCode should be(structuralXPlusY.hashCode)
    *          }}}
    */
  protected trait TermApi extends super.TermApi { this: Term =>
    override def hashCode(): Int = {
      tree.structuralHashCode(new HashCodeContext)
    }

    override def equals(that: Any): Boolean = {
      that match {
        case that: TermApi =>
          tree.isSameStructure(that.tree, new StructuralComparisonContext)
        case _ =>
          false
      }
    }
  }

  type Term <: TermApi
}
