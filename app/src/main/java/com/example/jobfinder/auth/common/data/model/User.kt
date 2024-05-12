data class User(
    val userId:String? = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val email: String? = "",
    val imagePath: String? = ""
) {
    constructor() : this(
        firstName ="", lastName = "",imagePath = "", email = "", userId = ""
    )
}