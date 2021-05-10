package com.mobline.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.mobline.currencyconverter.presentation.databinding.FragmentBottomSheetDialogBaseBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mobline.currencyconverter.presentation.R
import com.mobline.presentation.delegate.viewBinding
import com.mobline.presentation.tools.Utils.updateBoldSpanWithBoldFont

open class BaseBottomSheetDialog : BottomSheetDialogFragment() {

    val binding by viewBinding(FragmentBottomSheetDialogBaseBinding::bind)

    private var title: Int = R.string.unknown_error_title
    private var text: Int = R.string.unknown_error_text
    private var image: Int = R.drawable.ic_alert_general

    private var scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER
    private var canCancel: Boolean = true
    private var buttonText: Int = R.string.close
    private var action: (BaseBottomSheetDialog) -> Unit = {}
    private var imageBg: Int = R.color.white
    private var buttonTextColor: Int = R.color.mirage500


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_bottom_sheet_dialog_base, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.title.setText(title)
        binding.text.text = updateBoldSpanWithBoldFont(
            getText(text),
            ContextCompat.getColor(requireContext(), R.color.mirage500)
        )
        binding.image.setBackgroundResource(imageBg)
        binding.image.setImageResource(image)
        binding.image.scaleType = scaleType

        binding.button.setText(buttonText)
        binding.button.setTextColor(ContextCompat.getColor(requireContext(), buttonTextColor))
        binding.button.setOnClickListener {
            action(this)
        }
        isCancelable = canCancel
    }

    companion object {
        fun build(
            title: Int = R.string.unknown_error_title,
            text: Int = R.string.unknown_error_text,
            image: Int = R.drawable.ic_alert_general,
            scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER,
            cancelable: Boolean = true,
            buttonText: Int = R.string.close,
            buttonTextColor: Int = R.color.mirage500,
            action: (BaseBottomSheetDialog) -> Unit = {},
            imageBg: Int = R.color.white
        ): BaseBottomSheetDialog {
            return BaseBottomSheetDialog().apply {
                this.title = title
                this.text = text
                this.image = image
                this.scaleType = scaleType
                this.canCancel = cancelable
                this.buttonText = buttonText
                this.buttonTextColor = buttonTextColor
                this.action = action
                this.imageBg = imageBg
            }
        }
    }
}