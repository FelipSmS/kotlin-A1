import kotlinx.serialization.Serializable

@Serializable
data class GeolocalizacaoIP(
   val ip: String,
   val hostname: String? = null,
   val continent_code: String? = null,
   val continent_name: String? = null,
   val country_code2: String? = null,
   val state_prov: String? = null,
   val city: String? = null,
   val latitude: Double? = null,
   val longitude: Double? = null,
   val fusoHorario: String? = null,
   val isp: String? = null,
   val country_flag: String? = null, // URL da bandeira
   val country_emoji: String? = null, // Emoji do país
   val organization: String? = null,
)
