package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.itesm.bcr.plantifybcr.viewmodels.InfoWikiPlantaVM

class InfoWikiPlantaFrag : Fragment() {

    companion object {
        fun newInstance() = InfoWikiPlantaFrag()
    }

    private lateinit var viewModel: InfoWikiPlantaVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_wiki_planta_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InfoWikiPlantaVM::class.java)
        // TODO: Use the ViewModel
    }

}