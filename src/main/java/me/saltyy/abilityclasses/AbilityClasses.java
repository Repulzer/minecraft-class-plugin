package me.saltyy.abilityclasses;

import me.saltyy.abilityclasses.commands.HeroCMD;
import me.saltyy.abilityclasses.commands.Reload;
import me.saltyy.abilityclasses.commands.Reroll;
import me.saltyy.abilityclasses.commands.Info;
import me.saltyy.abilityclasses.skills.implementations.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class AbilityClasses extends JavaPlugin {

    ConfigHandler configHandler;
    PowersHandler powersHandler;
    SkillImplementation[] skills;

    @Override
    public void onEnable() {
        // Plugin startup logic
        powersHandler = new PowersHandler(this);
        configHandler = new ConfigHandler(this, powersHandler);
        powersHandler.setConfigHandler(configHandler);
        registerSkills();
        Reroll reroll = new Reroll(powersHandler, configHandler);
        Reload reload = new Reload(configHandler);
        this.getCommand("abilityconfigreload").setExecutor(reload);
        this.getServer().getPluginManager().registerEvents(reroll, this);
        this.getServer().getPluginManager().registerEvents(powersHandler, this);
        PluginCommand infocommand = this.getCommand("abilityinfo");
        Info Info = new Info(powersHandler);
        infocommand.setExecutor(Info);
        this.getServer().getPluginManager().registerEvents(Info, this);
        HeroCMD heroCMD = new HeroCMD(powersHandler);
        PluginCommand command = this.getCommand("ability");
        command.setExecutor(heroCMD);
        command.setTabCompleter(heroCMD);
        this.getCommand("abilityreroll").setExecutor(reroll);
        handleMetrics();
    }

    public void registerSkills() {
        skills = new SkillImplementation[]{
                new PotionEffectSkill(powersHandler),
                new InstantBreak(powersHandler),
                new LightSkill(powersHandler),
                new NoHungerSkill(powersHandler),
                new DamageResistanceSkill(powersHandler),
                new SlimeSkill(powersHandler),
                new SneakingPotionSkill(powersHandler),
                new EggLayerSkill(powersHandler),
                new WalkerSkill(powersHandler),
                new AuraSkill(powersHandler),
                new PickpocketSkill(powersHandler),
                new StrongmanSkill(powersHandler),
                new PhaseSkill(powersHandler),
                new SlamSkill(powersHandler),
                new EraserSkill(powersHandler),
                new CraftingSkill(powersHandler),
                new TeleportSkill(powersHandler),
                new SummonSkill(powersHandler),
                new DecoySkill(powersHandler),
                new PotionGifterSkill(powersHandler),
                new ConsumeSkill(powersHandler),
                new BlockRaySkill(powersHandler),
                new OHKOSkill(powersHandler),
                new RepulsionSkill(powersHandler),
                new CreeperSkill(powersHandler),
                new GiveItemSkill(powersHandler),
                new GunSkill(powersHandler),
                new SneakSkill(powersHandler),
                new ShieldSkill(powersHandler),
                new SpellSkill(powersHandler),
                new ThrowerSkill(powersHandler),
                new ConvertItemSkill(powersHandler),
                new ConvertBlockSkill(powersHandler),
                new RemoteDetonationSkill(powersHandler),
                new ConvertDropsSkill(powersHandler)
        };
        for (SkillImplementation skill : skills) {
            this.getServer().getPluginManager().registerEvents(skill, this);
        }
    }

    public void handleMetrics() {
        Metrics metrics = new Metrics(this, 8671);
        if (!metrics.isEnabled()) {
            Bukkit.getLogger().log(Level.WARNING, "[Playerclasses] You have disabled bstats, this is very sad :(");
        }
        metrics.addCustomChart(new Metrics.AdvancedPie("players_using_each_playerclass", () -> {
            Map<String, Integer> valueMap = new HashMap<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                String powerName = powersHandler.getPlayerclass(player).getName();
                int currentCount = valueMap.getOrDefault(powerName, 0);
                currentCount++;
                valueMap.put(powerName, currentCount);
            }
            return valueMap;
        }));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
