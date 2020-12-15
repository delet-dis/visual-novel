package com.delet_dis.vusialnovel

data class Scene(
  val arrayOfVariants: List<ArrayOfVariant>,
  val backgroundPath: String,
  val header: String,
  val id: Int
)

data class ArrayOfVariant(
  val nextId: Int,
  val variantText: String
)