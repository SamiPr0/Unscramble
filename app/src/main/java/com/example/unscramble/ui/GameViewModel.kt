package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow() // StateFlow is the read-only view of a MutableStateFlow

    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()
    private fun pickRandomWordAndShuffle() : String {
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }
    private fun shuffleCurrentWord(word: String) : String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while(String(tempWord) == word){
            tempWord.shuffle()
        }
        return String(tempWord)
    }
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }
    data class GameUiState(
        val currentScrambledWord: String = ""
    )

    init {
        resetGame()
    }
}