package com.annaginagili.movieapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.annaginagili.movieapp.R
import com.annaginagili.movieapp.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var login: SignInButton
    lateinit var auth: FirebaseAuth
    lateinit var signInClient: GoogleSignInClient
    lateinit var gso: GoogleSignInOptions
    private val code = 40
//    lateinit var preferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater)
        login = binding.login

        auth = FirebaseAuth.getInstance()
//        preferences =
//            requireActivity().getSharedPreferences("Mov", AppCompatActivity.MODE_PRIVATE)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.api_key)).requestEmail().build()
        signInClient = GoogleSignIn.getClient(requireActivity(), gso)

        login.setOnClickListener { signIn() }

        return binding.root
    }

    private fun signIn() {
        val intent = signInClient.signInIntent
        startActivityForResult(intent, code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == code) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuth(account.idToken)
            } catch (e: ApiException) {
                Log.e("hello", e.toString())
            }
        }
    }

    private fun firebaseAuth(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            } else {
                Log.e("hello", it.exception.toString())
            }
        }
    }
}