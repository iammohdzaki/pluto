package com.mocklets.pluto.modules.preferences.ui

import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.mocklets.pluto.R
import com.mocklets.pluto.core.extensions.color
import com.mocklets.pluto.core.extensions.inflate
import com.mocklets.pluto.core.ui.list.DiffAwareAdapter
import com.mocklets.pluto.core.ui.list.DiffAwareHolder
import com.mocklets.pluto.core.ui.list.ListItem
import com.mocklets.pluto.core.ui.setDebounceClickListener
import com.mocklets.pluto.core.ui.spannable.createSpan
import com.mocklets.pluto.databinding.PlutoItemSharedPrefKeyValueBinding

internal class SharedPrefKeyValueItemHolder(
    parent: ViewGroup,
    actionListener: DiffAwareAdapter.OnActionListener
) : DiffAwareHolder(parent.inflate(R.layout.pluto___item_shared_pref_key_value), actionListener) {

    private val binding = PlutoItemSharedPrefKeyValueBinding.bind(itemView)
    private val key = binding.key
    private val value = binding.value
    private val file = binding.file

    override fun onBind(item: ListItem) {
        if (item is SharedPrefKeyValuePair) {
            key.text = item.key
            file.visibility = if (item.isDefault) GONE else VISIBLE
            val fileName = item.prefLabel
            file.text = if (fileName != null) {
                if (fileName.length > MAX_FILENAME_LENGTH) {
                    "${fileName.substring(0, MAX_FILENAME_LENGTH - 2)}..."
                } else {
                    fileName
                }
            } else {
                itemView.context.createSpan {
                    append(fontColor(light(italic("null")), context.color(R.color.pluto___text_dark_40)))
                }
            }
            item.value?.let { value.text = it.toString() }

            itemView.setDebounceClickListener {
                onAction("click")
            }
        }
    }

    companion object {
        const val MAX_FILENAME_LENGTH = 18
    }
}
