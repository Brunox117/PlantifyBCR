package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AgregarGrupoFrag : Fragment() {

    companion object {
        fun newInstance() = AgregarGrupoFrag()
    }

    private lateinit var viewModel: AgregarGrupoVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.agregar_grupo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AgregarGrupoVM::class.java)
        // TODO: Use the ViewModel
    }

}