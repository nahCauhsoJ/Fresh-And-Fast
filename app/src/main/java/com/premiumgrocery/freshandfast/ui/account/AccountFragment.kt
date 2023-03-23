package com.premiumgrocery.freshandfast.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.premiumgrocery.freshandfast.LoginActivity
import com.premiumgrocery.freshandfast.R
import com.premiumgrocery.freshandfast.databinding.CardSettingsBinding
import com.premiumgrocery.freshandfast.databinding.FragmentAccountBinding
import com.premiumgrocery.freshandfast.utils.RVAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private val vm by activityViewModels<AccountViewModel>()
    private val settingsList by lazy{
        resources.getStringArray(R.array.settingsList)
            .mapIndexed { index, s -> Pair(index, s) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentAccountBinding>(
        inflater, R.layout.fragment_account, container, false
    ).apply {
        settingsListRv.apply {
            settingsListRv.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = RVAdapter(
                    settingsList,
                    {p0,p1,p2 -> CardSettingsBinding.inflate(p0,p1,p2)}
                ) { it,v,_ ->
                    val binding = v as CardSettingsBinding
                    binding.settingsTitle.text = it.second
                    binding.root.setOnClickListener { _-> vm.activateSettings(it.first) }
                }
                addItemDecoration(MaterialDividerItemDecoration(context, RecyclerView.VERTICAL))
            }
        }

        vm.isLoggedOut.observe(viewLifecycleOwner) {
            if (it) Intent(requireActivity(), LoginActivity::class.java).apply {
                startActivity(this)
                requireActivity().finish()
            }
        }
    }.root
}