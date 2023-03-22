package com.example.testapp.data

import com.example.testapp.data.entity.ItemCharacter
import com.example.testapp.data.entity.ItemCharacterDB
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepository @Inject constructor(private val charactersDao: CharactersDao){

    fun getCharacters() = charactersDao.getCharacters()

    fun getCharacter(characterId: Int) = charactersDao.getCharacter(characterId)

    suspend fun createCharacter(character: ItemCharacter) {
        val characterDB = ItemCharacterDB(character.id, character.name, character.status, character.species, character.gender, character.origin?.name, character.location?.name, character.image, Calendar.getInstance().timeInMillis)
        charactersDao.insertCharacter(characterDB)
    }

    suspend fun removeCharacter(characterId: Int) {
        charactersDao.deleteCharacter(characterId)
    }

    suspend fun isRowIsExist(characterId: Int) = charactersDao.isRowIsExist(characterId)

    companion object {
        @Volatile private var instance: CharactersRepository? = null

        fun getInstance(charactersDao: CharactersDao) =
            instance ?: synchronized(this) {
                instance ?: CharactersRepository(charactersDao).also { instance = it }
            }
    }
}