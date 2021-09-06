/*
 * Copyright (C) 2020 - Amir Hossein Aghajari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package lahds.relyme.UserInterface.EmojiView.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;

import androidx.annotation.CallSuper;
import androidx.annotation.DimenRes;
import androidx.annotation.Px;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;

import android.util.AttributeSet;

import lahds.relyme.UserInterface.EmojiView.EmojiManager;
import lahds.relyme.UserInterface.EmojiView.EmojiUtils;
import lahds.relyme.R;
import lahds.relyme.UserInterface.EmojiView.Emoji.Emoji;

/**
 * Reference implementation for an EmojiAutoCompleteTextView with emoji support.
 */
public class EmojiMultiAutoCompleteTextView extends AppCompatMultiAutoCompleteTextView {
    private float emojiSize;

    public EmojiMultiAutoCompleteTextView(final Context context) {
        this(context, null);
    }

    public EmojiMultiAutoCompleteTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);


        final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;

        if (attrs == null) {
            emojiSize = defaultEmojiSize;
        } else {
            final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmojiMultiAutoCompleteTextView);

            try {
                emojiSize = a.getDimension(R.styleable.EmojiMultiAutoCompleteTextView_emojiSize, defaultEmojiSize);
            } finally {
                a.recycle();
            }
        }

        setText(getText());
    }

    @Override
    @CallSuper
    protected void onTextChanged(final CharSequence text, final int start, final int lengthBefore, final int lengthAfter) {
        if (!EmojiManager.isInstalled()) return;
        final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        //final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;
        EmojiManager.getInstance().replaceWithImages(getContext(), this, getText(), emojiSize, fontMetrics);
    }

    @CallSuper
    public void backspace() {
        EmojiUtils.backspace(this);
    }

    @CallSuper
    public void input(final Emoji emoji) {
        EmojiUtils.input(this, emoji);
    }

    /**
     * returns the emoji size
     */
    public final float getEmojiSize() {
        return emojiSize;
    }

    /**
     * sets the emoji size in pixels and automatically invalidates the text and renders it with the new size
     */
    public final void setEmojiSize(@Px final int pixels) {
        setEmojiSize(pixels, true);
    }

    /**
     * sets the emoji size in pixels and automatically invalidates the text and renders it with the new size when {@code shouldInvalidate} is true
     */
    public final void setEmojiSize(@Px final int pixels, final boolean shouldInvalidate) {
        emojiSize = pixels;

        if (shouldInvalidate) {
            if (getText()!=null)
                setText(getText().toString());
        }
    }

    /**
     * sets the emoji size in pixels with the provided resource and automatically invalidates the text and renders it with the new size
     */
    public final void setEmojiSizeRes(@DimenRes final int res) {
        setEmojiSizeRes(res, true);
    }

    /**
     * sets the emoji size in pixels with the provided resource and invalidates the text and renders it with the new size when {@code shouldInvalidate} is true
     */
    public final void setEmojiSizeRes(@DimenRes final int res, final boolean shouldInvalidate) {
        setEmojiSize(getResources().getDimensionPixelSize(res), shouldInvalidate);
    }
}
