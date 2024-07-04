package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.skills.Skill;
import org.bukkit.configuration.ConfigurationSection;

public class PhaseData extends SkillData {

    private double minimumPhaseYAxis;

    protected PhaseData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection);
        minimumPhaseYAxis = configurationSection.getDouble("minimumPhaseYAxis", 5);
    }

    public double getMinimumPhaseYAxis() {
        return minimumPhaseYAxis;
    }
}
