package xyz.adrw.hotwire

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.buffer
import okio.source

data class Row(
  val title: String,
  val author: String,
  val views: String
) {
  fun components() = listOf(title, author, views)
}

val tableData = listOf(
  Row(
    "Title", "Author", "Views"
  ),
  Row(
    "Intro to CSS", "Adam", "858"
  ),
  Row(
    "Gulag Archapelago", "Joe", "21"
  ),
  Row(
    "Yeet", "Gary", "242"
  ),
  Row(
    "Another", "Fred", "42069"
  ),
).map { it.components() }

data class Car(
  val id: String,
  val make: String,
  val model: String,
  val model_year: Int,
  val quantity: Int,
  val price: String,
  val slogan: String,
  val latitude: Float,
  val longitude: Float,
  val manufactured_date: String,
  val created_at: String,
  val updated_at: String,
) {
  fun components() = listOf(
    id,
    make,
    model,
    model_year,
    quantity,
    price,
    slogan,
    latitude,
    longitude,
    manufactured_date,
    created_at,
    updated_at
  ).map { it.toString() }
}

object FakeDatastore {
  val carsData by lazy {
    val moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory()) // Added last for lowest precedence.
      .build()
    val adapter = moshi.adapter<List<Car>>(
      Types.newParameterizedType(MutableList::class.java, Car::class.java)
    )
    val jsonFilePath = "data/cars.json"
    val stream = this::class.java.classLoader.getResourceAsStream(jsonFilePath)
      ?: throw IllegalArgumentException("unable to open [filePath=$jsonFilePath]")
    val cars = adapter.fromJson(stream.source().buffer())
      ?: throw IllegalArgumentException("unable to read json")
    listOf(
      listOf(
        "ID",
        "Make",
        "Model",
        "Model Year",
        "Quantity",
        "Price",
        "Slogan",
        "Latitude",
        "Longitude",
        "Manufactured At",
        "Created At",
        "Updated At",
      )
    ).plus(cars.map { it.components() })
  }

}
