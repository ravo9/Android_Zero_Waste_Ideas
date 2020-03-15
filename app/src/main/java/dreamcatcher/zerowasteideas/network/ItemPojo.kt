package dreamcatcher.zerowasteideas.network

import com.google.gson.annotations.SerializedName

// ApiResponse object used for de-serializing data coming from API endpoint
data class ItemPojo(

    @SerializedName("ID")
    val id: String?,

    @SerializedName("Author name")
    val authorName: String?,

    @SerializedName("Title")
    val title: String?,

    @SerializedName("Description")
    val description: String?,

    @SerializedName("Tags")
    val tags: List<String>?,

    @SerializedName("Image")
    val imageLink: String?,

    @SerializedName("Current grade")
    val currentGrade: Int?,

    @SerializedName("Amount of grades")
    val amountOfGrades: Int?)