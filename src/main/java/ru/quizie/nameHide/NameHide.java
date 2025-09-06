package ru.quizie.nameHide;

import org.bukkit.plugin.java.JavaPlugin;
import ru.quizie.nameHide.displays.DisplayManager;
import ru.quizie.nameHide.displays.data.DisplayData;
import ru.quizie.nameHide.services.RayTraceService;
import ru.quizie.nameHide.services.UpdateDisplayService;

public final class NameHide extends JavaPlugin {

    private final long TICKS_DISPLAY_UPDATE;
    private final long TICKS_RAY_TRACE_SERVICE = Config.repeated;

    public NameHide() {
        TICKS_DISPLAY_UPDATE = 30 * 20;
    }

    @Override
    public void onEnable() {
        super.saveDefaultConfig();
        Config.load(super.getConfig());

        this.startServices();

        super.getServer().getPluginManager().registerEvents(new BukkitListener(this), this);
    }

    private void startServices() {
        new RayTraceService(this).runTaskTimer(this, TICKS_RAY_TRACE_SERVICE, TICKS_RAY_TRACE_SERVICE);
        new UpdateDisplayService().runTaskTimer(this, TICKS_DISPLAY_UPDATE, TICKS_DISPLAY_UPDATE);
    }

    @Override
    public void onDisable() {
        DisplayManager.removeAllDisplays();
    }
}
