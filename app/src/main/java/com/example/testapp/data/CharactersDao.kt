package com.example.testapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.data.entity.ItemCharacterDB
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {
    @Query("SELECT * FROM characters")
    fun getCharacters(): Flow<List<ItemCharacterDB>>

    @Query("SELECT * FROM characters WHERE id = :characterId")
    fun getCharacter(characterId: Int): Flow<ItemCharacterDB>

    @Query("SELECT EXISTS(SELECT * FROM characters WHERE id = :id)")
    suspend fun isRowIsExist(id : Int) : Boolean

    @Insert
    suspend fun insertCharacter(character: ItemCharacterDB) : Long

    @Query("DELETE FROM characters WHERE id = :characterId")
    suspend fun deleteCharacter(characterId: Int)
}