package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.itesm.bcr.plantifybcr.viewmodels.TutorialesVM

class TutorialesFrag : Fragment() {

    companion object {
        fun newInstance() = TutorialesFrag()
    }

    private lateinit var viewModel: TutorialesVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tutoriales_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TutorialesVM::class.java)
        // TODO: Use the ViewModel
    }

}