package me.saltyy.abilityclasses;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;

import java.util.ArrayList;
import java.util.Collection;

public class Playerclass {

    protected String name;
    protected String colouredName;
    protected String description;
    protected String abilityInfo;
    protected Multimap<Skill, SkillData> skillToData = HashMultimap.create();

    public Playerclass(String name, String colouredName, String description, String abilityInfo) {
        this.name = name;
        this.colouredName = colouredName;
        this.description = description;
        this.abilityInfo = abilityInfo;
    }

    public void addSkill(SkillData skill) {
        skillToData.put(skill.getSkill(), skill);
    }

    public void addSkills(SkillData... skills) {
        for (SkillData skill : skills) {
            addSkill(skill);
        }
    }

    public boolean hasSkill(Skill skill) {
        return skillToData.containsKey(skill);
    }

    public Collection<SkillData> getSkillData(Skill skill) {
        Collection<SkillData> skillData = skillToData.get(skill);
        return skillData == null ? new ArrayList<>() : skillData;
    }

    public String getColouredName() {
        return colouredName;
    }

    public String getDescription() {
        return description;
    }

    public String getAbilityInfo() {
        return abilityInfo;
    }

    public String getName() {
        return name;
    }

    public String getPermission() { return "playerclasses.ability." + getName().toLowerCase();}

}
