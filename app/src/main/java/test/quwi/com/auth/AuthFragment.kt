package test.quwi.com.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.koin.androidx.viewmodel.ext.android.viewModel
import test.quwi.com.R
import test.quwi.com.base.FragmentRoute
import test.quwi.com.base.RequestResult
import test.quwi.com.databinding.AuthFragmentBinding
import test.quwi.com.main.MainViewModel

class AuthFragment : Fragment() {

    private lateinit var binding: AuthFragmentBinding

    private val authViewModel: AuthViewModel by viewModel()
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()

    }

    private fun init() {
        binding.signUpButton.setOnClickListener {
            if (binding.motionLayout.currentState == R.id.start)
                binding.motionLayout.transitionToState(R.id.sign_up)
            else signupClicked()
        }

        binding.logInButton.setOnClickListener {
            if (binding.motionLayout.currentState == R.id.start)
                binding.motionLayout.transitionToState(R.id.log_in)
            else loginClicked()
        }
    }

    private fun observe() {
        authViewModel.validateUserInfoLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ValidateError.NoFirstName -> {
                    if (it.hasError)
                        binding.firstNameLayout.error = getString(R.string.first_name_error)
                    else binding.firstNameLayout.error = null
                }

                is ValidateError.NoLastName -> {
                    if(it.hasError)
                        binding.lastNameLayout.error = getString(R.string.last_name_error)
                    else binding.lastNameLayout.error = null
                }

                is ValidateError.NoEmail -> {
                    if(it.hasError)
                        binding.emailLayout.error = getString(R.string.email_error)
                    else binding.emailLayout.error = null
                }

                is ValidateError.NoPassword -> {
                    if (it.hasError)
                        binding.passwordLayout.error = getString(R.string.password_error)
                    else binding.passwordLayout.error = null
                }
                ValidateError.EmailAlreadyInUse -> {
                    binding.emailLayout.error = getString(R.string.email_in_use_error)
                }
            }
        })

        authViewModel.loginLiveData.observe(viewLifecycleOwner, {
            mainViewModel.openFragment(FragmentRoute.ChatFragment)
        })
    }

    private fun loginClicked() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordNameEditText.text.toString().trim()

        authViewModel.login(email, password)
    }



    private fun signupClicked() {
        val firstName = binding.firstNameEditText.text.toString().trim()
        val lastName = binding.lastNameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordNameEditText.text.toString().trim()

        authViewModel.signUp(
            UserInfo(
                firstName,
                lastName,
                email,
                password
            )
        )
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

data class UserInfo(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)