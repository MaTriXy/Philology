package com.jcminarro.philology.transformer

import android.view.View
import android.widget.TextView
import com.jcminarro.philology.HardcodedAttribute
import com.jcminarro.philology.ResourceIdAttribute
import com.jcminarro.philology.createAttributeSet
import org.amshove.kluent.Verify
import org.amshove.kluent.`Verify no further interactions`
import org.amshove.kluent.`should be`
import org.amshove.kluent.called
import org.amshove.kluent.mock
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Test

class TextViewTransformerTest {

    private val view: View = mock()
    private val textView: TextView = mock()

    @Test
    fun `View should be the same`() {
        TextViewTransformer.reword(view, createAttributeSet()) `should be` view
    }

    @Test
    fun `Inflated textView should be the same`() {
        TextViewTransformer.reword(textView, createAttributeSet()) `should be` textView
    }

    @Test
    fun `View shouldn't be modified`() {
        TextViewTransformer.reword(view, createAttributeSet())

        `Verify no further interactions` on view
    }

    @Test
    fun `Should reword only the text`() {
        val viewResult = TextViewTransformer.reword(textView, createAttributeSet(
                HardcodedAttribute("hint"),
                ResourceIdAttribute("text")))

        viewResult `should be` textView
        Verify on textView that textView.setText(1) was called
        `Verify no further interactions` on textView
    }

    @Test
    fun `Should reword only the hint`() {
        val viewResult = TextViewTransformer.reword(textView, createAttributeSet(
                HardcodedAttribute("text"),
                ResourceIdAttribute("hint")))

        viewResult `should be` textView
        Verify on textView that textView.setHint(1) was called
        `Verify no further interactions` on textView
    }

    @Test
    fun `Should reword text and hint`() {
        val viewResult = TextViewTransformer.reword(textView, createAttributeSet(
                ResourceIdAttribute("text"),
                ResourceIdAttribute("hint")))

        viewResult `should be` textView
        Verify on textView that textView.setText(0) was called
        Verify on textView that textView.setHint(1) was called
        `Verify no further interactions` on textView
    }
}