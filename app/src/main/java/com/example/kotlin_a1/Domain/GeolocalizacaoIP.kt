import kotlinx.serialization.Serializable

@Serializable
data class GeolocalizacaoIP(
   val ip: String = "",
   val hostname: String = "",
   val continent_code: String = "",
   val continent_name: String = "",
   val country_code2: String = "",
   val state_prov: String = "",
   val city: String = "",
   val latitude: Double = 0.0,
   val longitude: Double = 0.0,
   val fusoHorario: String = "",
   val isp: String = "",
   val country_flag: String = "",
   val country_emoji: String = "",
   val organization: String = ""
) {
   fun toMap(): Map<String, Any?> {
      return mapOf(
         "ip" to ip,
         "hostname" to hostname,
         "continent_code" to continent_code,
         "continent_name" to continent_name,
         "country_code2" to country_code2,
         "state_prov" to state_prov,
         "city" to city,
         "latitude" to latitude,
         "longitude" to longitude,
         "fusoHorario" to fusoHorario,
         "isp" to isp,
         "country_flag" to country_flag,
         "country_emoji" to country_emoji,
         "organization" to organization
      )
   }
}
