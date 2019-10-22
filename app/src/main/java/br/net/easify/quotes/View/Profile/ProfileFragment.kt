package br.net.easify.quotes.View.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import br.net.easify.quotes.R
import br.net.easify.quotes.ViewModel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    private val nameObserver = Observer<String> { res: String? ->
        res.let { it ->
            txtName.setText(it)
        }
    }

    private val emailObserver = Observer<String> { res: String? ->
        res.let {
            txtEmail.setText(it)
        }
    }

    private val savedObserver = Observer<Boolean> { res: Boolean ->
        res.let {
            if ( it ) {
                val action = ProfileFragmentDirections.actionReturnFromProfile()
                Navigation.findNavController(getView()!!).navigate(action)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.name.observe(this, nameObserver)
        viewModel.email.observe(this, emailObserver)
        viewModel.saved.observe(this, savedObserver)

        viewModel.getCurrentUser()

        btnSave.setOnClickListener {
            viewModel.save(txtName.text.toString(), txtEmail.text.toString())
        }
    }
}
