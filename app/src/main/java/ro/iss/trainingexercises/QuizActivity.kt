package ro.iss.trainingexercises

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity(), FirstFragment.CommunicateWithActivity {
    override fun setSomething(title : String) {
        quiz_toolbar.title = title
    }

    private lateinit var quizAdapter : QuizAdaptor
    private lateinit var observableGetQuestions : Observable<ArrayList<Question>>
    private lateinit var apiCallDisposableGetQuestions : Disposable
    private lateinit var loadingDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        createLoadingDialog()
        configurationToolbar()
//        setQuestionsList()
        getQuestionsList()


    }

    private fun createLoadingDialog(){
        loadingDialog = ProgressDialog(this)
        loadingDialog.setTitle("Loading Questions")
//            AlertDialog.Builder(this).setMessage("Loading Questions").setCancelable(false).create()
    }
    private fun configurationToolbar()
    {
        quiz_toolbar.title = "Submit answers"
        setSupportActionBar(quiz_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun getQuestionsList() {
//        val questionsList = ArrayList<Question>()
//        for (i in 0 until 10)
//        {
//            val answers = arrayListOf("Answer 1", "Answer 2", "Answer 3", "Answer 4")
//            questionsList.add(Question(i, "Question "+(i+1), answers ))
//        }
//        return questionsList

        showLoadingDialog()
        observableGetQuestions = RetrofitCall.getQuestions()
        apiCallDisposableGetQuestions = observableGetQuestions.subscribe(
            {
                    response ->
                setQuestionsList(response)
            },
            {
                setErrorMessage()
            },
            {
                dismissLoadingDialog()
                apiCallDisposableGetQuestions.dispose()
            }
        )

    }

    private fun setQuestionsList(questions : ArrayList<Question>){
        view_list.visibility = View.VISIBLE
        error_text_message.visibility = View.GONE
        quizAdapter = QuizAdaptor(questions, this)
        view_list.adapter = quizAdapter
        view_list.layoutManager = LinearLayoutManager(this)
    }

    private fun setErrorMessage()
    {
        view_list.visibility = View.GONE
        error_text_message.visibility = View.VISIBLE
    }

    private fun showLoadingDialog(){
        loadingDialog.show()
    }

    private  fun dismissLoadingDialog(){
        loadingDialog.dismiss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           android.R.id.home->onBackPressed()
       }
        return super.onOptionsItemSelected(item)
    }


}
