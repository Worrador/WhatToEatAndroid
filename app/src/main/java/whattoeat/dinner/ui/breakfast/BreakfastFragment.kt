package whattoeat.dinner.ui.breakfast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import whattoeat.dinner.databinding.FragmentHomeBinding
import whattoeat.dinner.ui.MainViewModel


class BreakfastFragment : Fragment(whattoeat.dinner.R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val mainViewModel = activity?.run {
            ViewModelProvider(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView: ListView = binding.breakfastListView
        val listOfItem: ArrayList<String> = mainViewModel.setMultipleListView()

        getContext()?.let { val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(it, android.R.layout.simple_list_item_multiple_choice, listOfItem)
            listView.adapter = arrayAdapter
        }

        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        listView.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                if(mainViewModel.positionClickedArrayList.contains(position))
                    mainViewModel.positionClickedArrayList.remove(position)
                else
                    mainViewModel.positionClickedArrayList.add(position)
            }

        for (pos in mainViewModel.positionClickedArrayList)
            listView.setItemChecked(pos, true)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}