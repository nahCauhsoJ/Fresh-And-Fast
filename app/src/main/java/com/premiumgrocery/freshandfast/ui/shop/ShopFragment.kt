package com.premiumgrocery.freshandfast.ui.shop

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.R
import com.premiumgrocery.freshandfast.databinding.CardCategoryBinding
import com.premiumgrocery.freshandfast.databinding.CardProductBinding
import com.premiumgrocery.freshandfast.databinding.FragmentShopBinding
import com.premiumgrocery.freshandfast.remote.model.CategoryData
import com.premiumgrocery.freshandfast.remote.model.SearchData
import com.premiumgrocery.freshandfast.utils.RVAdapter
import dagger.hilt.android.AndroidEntryPoint

// NOTE: NO NEED to use view pager. The difference in content is too little to make a difference.
//      Instead, make a custom tab with buttons inside linear layout.

@AndroidEntryPoint
class ShopFragment : Fragment(), MenuProvider, MenuItem.OnActionExpandListener {
    private val vm by viewModels<ShopViewModel>()
    private val categoryList = mutableListOf<CategoryData>()
    private val searchResultList = mutableListOf<SearchData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentShopBinding>(
        inflater, R.layout.fragment_shop, container, false
    ).apply {
        shopCategoriesGrid.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = RVAdapter(
                categoryList,
                { p0,p1,p2-> CardCategoryBinding.inflate(p0,p1,p2) }
            ) { it,v,_->
                val binding = v as CardCategoryBinding
                Glide.with(this@ShopFragment)
                    .load(Const.imageBaseUrl + it.catImage)
                    .into(binding.categoryImage)
                binding.categoryTitle.text = it.catName
            }
            addItemDecoration(RVAdapter.spacerDecoration(gridColumnCount = 2))
        }

        shopSearchResult.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RVAdapter(
                searchResultList,
                { p0,p1,p2-> CardProductBinding.inflate(p0,p1,p2) }
            ) { it,v,_->
                val binding = v as CardProductBinding
                Glide.with(this@ShopFragment)
                    .load(Const.imageBaseUrl + it.image)
                    .into(binding.productImage)
                binding.productName.text = it.productName
                binding.productPrice.text = "\$${it.price}"
            }
            addItemDecoration(RVAdapter.spacerDecoration(gridColumnCount = 1))
        }

        vm.isProcessing.observe(viewLifecycleOwner) {
            shopLoading.visibility = if(it) View.VISIBLE else View.GONE
        }

        vm.categories.observe(viewLifecycleOwner) {
            categoryList.size.apply {
                categoryList.clear()
                shopCategoriesGrid.adapter?.notifyItemRangeRemoved(0, this)
            }
            categoryList.addAll(it)
            shopCategoriesGrid.adapter?.notifyItemRangeInserted(0, categoryList.size)
            vm.finishedLoading()
        }

        vm.searchResult.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                shopCategoriesGrid.visibility = View.VISIBLE
                shopSearchResult.visibility = View.GONE
                Snackbar.make(root, "Can't find anything...", Snackbar.LENGTH_SHORT).show()
            } else {
                shopCategoriesGrid.visibility = View.GONE
                shopSearchResult.visibility = View.VISIBLE
            }

            searchResultList.size.apply {
                searchResultList.clear()
                shopSearchResult.adapter?.notifyItemRangeRemoved(0, this)
            }
            searchResultList.addAll(it)
            shopSearchResult.adapter?.notifyItemRangeInserted(0, searchResultList.size)
        }
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().removeMenuProvider(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.shop_bar_menu, menu)
        (menu.findItem(R.id.actionbar_shop_search).actionView as SearchView?)?.apply {
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String) = true.also {
                    vm.searchGroceryProduct(query)
                    clearFocus()
                }

                override fun onQueryTextChange(newText: String) = true.also {
                    vm.getSuggestedProduct(newText)
                }
            })
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem) = false

    override fun onMenuItemActionExpand(p0: MenuItem) = true.also {

    }

    override fun onMenuItemActionCollapse(p0: MenuItem) = true.also {

    }
}