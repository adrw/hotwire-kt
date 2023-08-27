package misk.db.feature

import misk.db.Features
import misk.db.protos.feature.CreateOrUpdateFeatureResponse
import misk.db.protos.feature.Feature
import misk.db.protos.feature.FeatureConfig
import misk.db.protos.feature.FeatureRule
import misk.db.protos.feature.GetFeaturesResponse

fun Features.toProto() = Feature(
  name = name,
  config = metadata.config,
  created_at = created_at,
  updated_at = updated_at,
)

fun Features.toCreateOrUpdateResponse() = CreateOrUpdateFeatureResponse(
  updated_feature = toProto()
)

fun List<Features>.toGetFeaturesResponse(count: Long) = GetFeaturesResponse(
  total_features_count = count,
  features = map { it.toProto() }
)

fun FeatureConfig.getFeatureClazz(): Class<out Any> {
  check(rules.isNotEmpty()) {
    "Config must have at least 1 rule."
  }
  val type = rules.map { it.toClazz() }.first()
  check(rules.all { it.toClazz() == type }) {
    "All rules must have the same type."
  }
  return type
}

private fun FeatureRule.toClazz() = when {
  value_boolean != null -> java.lang.Boolean::class.java
  value_double != null -> java.lang.Double::class.java
  value_int != null -> java.lang.Integer::class.java
  value_string != null -> java.lang.String::class.java
  value_enum != null -> Class.forName(type_java_class_name!!)
  value_json != null -> Class.forName(type_java_class_name!!)
  else -> throw IllegalArgumentException("Rule does not have a corresponding clazz [rule=$this].")
}

enum class FeatureType {
  BOOLEAN, DOUBLE, INT, STRING, ENUM, JSON;
}

fun Class<out Any>.toFeatureType() = when {
    this == java.lang.Boolean::class.java -> FeatureType.BOOLEAN
    this == java.lang.Double::class.java -> FeatureType.DOUBLE
    this == java.lang.Integer::class.java -> FeatureType.INT
    this == java.lang.String::class.java -> FeatureType.STRING
    this.superclass == java.lang.Enum::class.java -> FeatureType.ENUM
    this.kotlin.isData -> FeatureType.JSON
    else -> throw IllegalArgumentException("No matching FeatureType for [clazz=${this.name}].")
}

