package ro.iss.trainingexercises

import com.google.gson.annotations.SerializedName

class Question(@SerializedName("questionId") var questionId : Int?,
               @SerializedName("question")var questionText:String? = null,
               @SerializedName("answers")var answers : ArrayList<String>? = null) {




}