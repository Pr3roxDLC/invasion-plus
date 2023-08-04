package me.pr3.invasionplus.content.screens;

import me.pr3.invasionplus.content.screens.nexus.NexusScreen;
import me.pr3.invasionplus.content.screens.nexus.NexusScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlers {
    public static ScreenHandlerType<NexusScreenHandler> NEXUS_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        NEXUS_SCREEN_HANDLER = new ScreenHandlerType<>(NexusScreenHandler::new, FeatureSet.empty());
        HandledScreens.register(ScreenHandlers.NEXUS_SCREEN_HANDLER, NexusScreen::new);
    }
}
