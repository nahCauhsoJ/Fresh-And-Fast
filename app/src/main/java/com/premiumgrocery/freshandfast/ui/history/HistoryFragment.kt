package com.premiumgrocery.freshandfast.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.R
import com.premiumgrocery.freshandfast.databinding.CardOrderBinding
import com.premiumgrocery.freshandfast.databinding.FragmentHistoryBinding
import com.premiumgrocery.freshandfast.remote.model.orderresponse.OrderResponseData
import com.premiumgrocery.freshandfast.utils.RVAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private val vm by viewModels<HistoryViewModel>()
    private val orderList = mutableListOf<OrderResponseData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentHistoryBinding>(
        inflater, R.layout.fragment_history, container, false
    ).apply {
        historyOrderRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RVAdapter(
                orderList,
                {p0,p1,p2 -> CardOrderBinding.inflate(p0,p1,p2)}
            ) { it,v,_->
                val binding = v as CardOrderBinding

            }
        }

        lifecycleScope.launch {
            vm.userOrders.collect{
                orderList.size.apply {
                    orderList.clear()
                    historyOrderRv.adapter?.notifyItemRangeRemoved(0, this)
                }
                orderList.addAll(it)
                historyOrderRv.adapter?.notifyItemRangeInserted(0, orderList.size)

            }
        }
        vm.endProcessTask(Const.processLabelStart)
    }.root

    override fun onStart() {
        super.onStart()
        vm.getOrders()
    }
}