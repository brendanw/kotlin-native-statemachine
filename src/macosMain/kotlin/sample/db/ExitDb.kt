package sample.db

class ExitDb {

    fun getItems(): List<Exit> {
        return listOf(
            Exit("Half dome"),
            Exit("Forbidden Wall"),
            Exit("El Capitan")
        )
    }

}

class Exit(val name: String)
