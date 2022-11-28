package misk.db.feature

import com.squareup.sqldelight.ColumnAdapter
import misk.db.Features
import misk.db.protos.feature.FeatureMetadata
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatterBuilder
import javax.inject.Qualifier

@Qualifier
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class FeatureDb

/** Typed class to be used instead of raw Longs wherever row Id is used */
data class FeatureId(internal val id: Long) {
  object Adapter : ColumnAdapter<FeatureId, Long> {
    override fun decode(databaseValue: Long) = FeatureId(databaseValue)
    override fun encode(value: FeatureId) = value.id
  }
}

val featureMetadataAdapter = object : ColumnAdapter<FeatureMetadata, ByteArray> {
  override fun decode(databaseValue: ByteArray): FeatureMetadata =
    FeatureMetadata.ADAPTER.decode(databaseValue)
  override fun encode(value: FeatureMetadata): ByteArray = value.encode()
}

val timestampInstantAdapter = object : ColumnAdapter<Instant, String> {
  private val dateTimeFormat = DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd HH:mm:ss")
    .toFormatter()
    .withZone(ZoneId.of("UTC"))

  override fun decode(databaseValue: String) = dateTimeFormat.parse(databaseValue, Instant::from)
  override fun encode(value: Instant) = dateTimeFormat.format(value)
}

val featuresAdapter = Features.Adapter(
  idAdapter = FeatureId.Adapter,
  created_atAdapter = timestampInstantAdapter,
  updated_atAdapter = timestampInstantAdapter,
  metadataAdapter = featureMetadataAdapter,
)

// TODO use more robust sanitzation library
/** Escape and replace characters that could be used in SQL injection or other attacks */
fun String.sanitize() = this.replace(";", "").replace("*", "")
