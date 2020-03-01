package dreamcatcher.zerowasteideas.network

import com.google.gson.annotations.SerializedName

// ApiResponse object used for de-serializing data coming from API endpoint
data class ItemPojo(

    @SerializedName("ID")
    val id: String,

    @SerializedName("Author Name")
    val authorName: String,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Description")
    val description: String,

    @SerializedName("Image Link")
    val imageLink: String?)