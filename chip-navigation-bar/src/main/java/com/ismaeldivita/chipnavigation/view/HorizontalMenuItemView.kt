package com.ismaeldivita.chipnavigation.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import com.ismaeldivita.chipnavigation.R
import com.ismaeldivita.chipnavigation.model.MenuItem
import com.ismaeldivita.chipnavigation.util.*
import com.ismaeldivita.chipnavigation.util.setColorStateListAnimator

internal class HorizontalMenuItemView(context: Context) : MenuItemView(context) {

    private val title by lazy { findViewById<TextView>(R.id.cbn_item_title) }
    private val icon by lazy { findViewById<ImageView>(R.id.cnb_item_icon) }
    private val container by lazy { findViewById<View>(R.id.cbn_item_internal_container) }

    init {
        View.inflate(getContext(), R.layout.cnb_horizontal_menu_item, this)
        layoutParams = LayoutParams(0, WRAP_CONTENT, 1F)
    }

    override fun bind(item: MenuItem) {
        id = item.id
        isEnabled = item.enabled

        title.text = item.title
        title.setTextColor(item.textColor)

        icon.setImageResource(item.icon)
        icon.setColorStateListAnimator(
            color = item.iconColor,
            unselectedColor = item.unselectedColor,
            disabledColor = item.disabledColor,
            mode = item.tintMode
        )
        val containerBackground = GradientDrawable().apply {
            cornerRadius = 1000f
            setTint(item.backgroundColor)
        }
        val containerForeground = GradientDrawable().apply {
            cornerRadius = 1000f
            setTint(Color.BLACK)
        }
        container.setCustomRipple(containerBackground, containerForeground)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        icon.jumpDrawablesToCurrentState()

        if (!enabled && isSelected) {
            beginDelayedTransitionOnParent()
            isSelected = false
        }
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)

        if (selected) {
            beginDelayedTransitionOnParent()
            updateLayoutParams<LinearLayout.LayoutParams> {
                width = WRAP_CONTENT
                weight = 0F
            }
            title.visibility = View.VISIBLE

        } else {
            updateLayoutParams<LinearLayout.LayoutParams> {
                width = 0
                weight = 1F
            }
            title.visibility = View.GONE
        }
    }

}