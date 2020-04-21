package com.naltynbekkz.nulife.ui.courses.answers.viewmodel

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.naltynbekkz.nulife.di.ViewModelAssistedFactory
import com.naltynbekkz.nulife.model.Answer
import com.naltynbekkz.nulife.model.Student
import com.naltynbekkz.nulife.repository.AnswersRepository
import com.naltynbekkz.nulife.repository.UserRepository
import com.naltynbekkz.nulife.util.Constant
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject


class NewAnswerViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val answersRepository: AnswersRepository,
    userRepository: UserRepository
) : ViewModel() {

    val answer: Answer = if (savedStateHandle.contains(Constant.QUESTION)) {
        Answer(question = savedStateHandle[Constant.QUESTION]!!)
    } else {
        savedStateHandle[Constant.ANSWER]!!
    }


    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<NewAnswerViewModel>

    val user = userRepository.user

    fun answer(
        anonymous: Boolean,
        failure: () -> Unit,
        images: ArrayList<Uri>,
        success: () -> Unit,
        done: (Int) -> Unit
    ) {
        answer.apply {
            author = Student(
                user.value!!,
                anonymous
            )
            timestamp = System.currentTimeMillis() / 1000
        }

        answersRepository.post(answer, failure, images, success, done)
    }

    fun editAnswer(
        anonymous: Boolean,
        success: () -> Unit,
        failure: () -> Unit
    ) {
        answer.apply {
            author = Student(
                user.value!!,
                anonymous
            )
            timestamp = System.currentTimeMillis() / 1000
        }
        answersRepository.editAnswer(answer, success, failure)
    }

}