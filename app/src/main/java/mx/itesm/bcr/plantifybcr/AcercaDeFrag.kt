package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.itesm.bcr.plantifybcr.viewmodels.AcercaDeVM

class AcercaDeFrag : Fragment() {

    companion object {
        fun newInstance() = AcercaDeFrag()
    }

    private lateinit var viewModel: AcercaDeVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.acerca_de_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AcercaDeVM::class.java)
        // TODO: Use the ViewModel
    }

}