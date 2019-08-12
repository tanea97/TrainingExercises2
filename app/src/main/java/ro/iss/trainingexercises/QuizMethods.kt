package ro.iss.trainingexercises

import io.reactivex.Observable
import retrofit2.http.GET
import kotlin.collections.ArrayList

interface QuizMethods {

    @GET("training-feedback/v1/questions")
    fun getQuestions(): Observable<ArrayList<Question>>
}