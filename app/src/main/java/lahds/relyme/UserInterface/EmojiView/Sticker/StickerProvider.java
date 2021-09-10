package lahds.relyme.UserInterface.EmojiView.Sticker;

import androidx.annotation.NonNull;


public interface StickerProvider {
    /**
     * @return The Array of categories.
     */
    @SuppressWarnings("rawtypes")
    @NonNull
    StickerCategory[] getCategories();

    @NonNull
    StickerLoader getLoader();

    /**
     * Returns true if there is a recent tab
     */
    boolean isRecentEnabled();
}
