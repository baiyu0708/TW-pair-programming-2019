package ui.load;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface OnSelectBoard {

    void onSelect(@NotNull BoardDesc board);
}
