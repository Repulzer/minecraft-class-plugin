package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.events.PlayerLostPlayerclassEvent;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.PotionEffectData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class SneakingPotionSkill extends SkillImplementation {
    public SneakingPotionSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        Playerclass playerclass = getPowersHandler().getPlayerclass(player);
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.SNEAKINGPOTION);
        for (SkillData skillData : skillDatas) {
            PotionEffectData potionEffectData = (PotionEffectData) skillData;
            if (e.isSneaking()) {
                e.getPlayer().addPotionEffect(potionEffectData.getPotionEffect());
            }
            else {
                e.getPlayer().removePotionEffect(potionEffectData.getPotionEffect().getType());
            }
        }
    }

    @EventHandler
    public void onPowerLost(PlayerLostPlayerclassEvent e) {
        Collection<SkillData> skillDatas = e.getHero().getSkillData(Skill.SNEAKINGPOTION);
        if (skillDatas != null) {
            for (SkillData skillData : skillDatas) {
                PotionEffectData sneakingPotionSkill = (PotionEffectData) skillData;
                PotionEffectType type = sneakingPotionSkill.getPotionEffect().getType();
                e.getPlayer().removePotionEffect(type);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Playerclass playerclass = getPowersHandler().getPlayerclass(player);
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.SNEAKINGPOTION);
        for (SkillData skillData : skillDatas) {
            PotionEffectData potionEffectData = (PotionEffectData) skillData;
            e.getPlayer().removePotionEffect(potionEffectData.getPotionEffect().getType());
        }
    }
}
