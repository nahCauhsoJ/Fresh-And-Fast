package com.premiumgrocery.freshandfast.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.OrderStatus
import com.premiumgrocery.freshandfast.R
import com.premiumgrocery.freshandfast.databinding.CardProductCheckoutBinding
import com.premiumgrocery.freshandfast.databinding.FragmentOrderBinding
import com.premiumgrocery.freshandfast.remote.model.ProductData
import com.premiumgrocery.freshandfast.utils.ConfirmAlert
import com.premiumgrocery.freshandfast.utils.RVAdapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderFragment : Fragment() {
    private val vm by activityViewModels<OrderViewModel>()
    private val currentOrderList = mutableListOf<ProductData>()

    @Inject lateinit var ioDispatcher: CoroutineDispatcher

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentOrderBinding>(
    inflater, R.layout.fragment_order, container, false
    ).apply {
        viewModel = vm
        fragment = this@OrderFragment
        lifecycleOwner = viewLifecycleOwner
        orderList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RVAdapter(
                currentOrderList,
                { p0,p1,p2 -> CardProductCheckoutBinding.inflate(p0,p1,p2) }
            ) { it,v,_ ->
                val binding = v as CardProductCheckoutBinding
                val orderAmount = vm.currentOrdersStatic[it.id]?:0
                Glide.with(this@OrderFragment)
                    .load(Const.imageBaseUrl + it.image)
                    .into(binding.checkoutProductImage)
                binding.checkoutProductTitle.text = it.productName
                binding.checkoutProductUnitPrice.text =
                    getString(R.string.product_price, it.price)
                binding.checkoutProductQuantity.text =
                    getString(R.string.product_quantity, orderAmount)
                binding.checkoutProductTotalPrice.text =
                    getString(R.string.total_cost, vm.calculateTotalCost( it.price, orderAmount ))
                binding.checkoutProductDelete.setOnClickListener {  _->
                    ConfirmAlert(
                        context,
                        titleText = "Deleting item",
                        messageText = "Are you sure you don't want [${it.productName}]?"
                    ) { vm.removeProductOrder(it.id) }
                }
            }
        }

        // Note that if the order is empty, it'll be observed
        //      once as usual. But if it's not, somehow it'll
        //      emit an empty list, then the real list. Be
        //      careful when you use it.
        vm.orderDetails.observe(viewLifecycleOwner) {
            currentOrderList.size.apply {
                currentOrderList.clear()
                orderList.adapter?.notifyItemRangeRemoved(0, this)
            }
            currentOrderList.addAll(it)
            orderList.adapter?.notifyItemRangeInserted(0, currentOrderList.size)

            if (it.isEmpty()) {
                orderSubmit.isEnabled = false
                orderPageTitle.visibility = View.INVISIBLE
                orderListEmpty.visibility = View.VISIBLE
            } else {
                orderSubmit.isEnabled = true
                orderPageTitle.visibility = View.VISIBLE
                orderListEmpty.visibility = View.INVISIBLE
            }

            vm.endProcessTask(Const.processLabelStart)
        }

        vm.placeOrderStatus.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            Snackbar.make(this@OrderFragment.requireView(),
                when (it) {
                    OrderStatus.SUCCESS -> "Order placed! Thank you for using our service."
                    OrderStatus.FAIL -> "Something bad happened on our side."
                },
                Snackbar.LENGTH_LONG
            ).show()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.processTasks.collect {
                    orderLoading.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }.root

    fun submitOrderPrompt() {
        ConfirmAlert(requireContext()) { vm.submitOrder() }
    }
}