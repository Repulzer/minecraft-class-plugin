package me.xemor.superheroes2;

import me.xemor.superheroes2.commands.HeroCMD;
import me.xemor.superheroes2.commands.Reload;
import me.xemor.superheroes2.commands.Reroll;
import me.xemor.superheroes2.commands.Info;
import me.xemor.superheroes2.skills.implementations.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class Superheroes2 extends JavaPlugin {

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
        this.getCommand("heroreload").setExecutor(reload);
        this.getServer().getPluginManager().registerEvents(reroll, this);
        this.getServer().getPluginManager().registerEvents(powersHandler, this);
        HeroCMD heroCMD = new HeroCMD(powersHandler);
        PluginCommand command = this.getCommand("hero");
        command.setExecutor(heroCMD);
        command.setTabCompleter(heroCMD);
        PluginCommand infocommand = this.getCommand("heroinfo");
        Info Info = new Info(powersHandler);
        infocommand.setExecutor(Info);
        this.getServer().getPluginManager().registerEvents(Info, this);
        this.getCommand("reroll").setExecutor(reroll);
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
            Bukkit.getLogger().log(Level.WARNING, "[Superheroes] You have disabled bstats, this is very sad :(");
        }
        metrics.addCustomChart(new Metrics.AdvancedPie("players_using_each_superhero", () -> {
            Map<String, Integer> valueMap = new HashMap<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                String powerName = powersHandler.getSuperhero(player).getName();
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
