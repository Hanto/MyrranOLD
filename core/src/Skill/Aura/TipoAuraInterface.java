package Skill.Aura;// Created by Hanto on 26/03/2014.

import Skill.Aura.BDebuff;

public interface TipoAuraInterface
{
    public void inicializarSkillStats();
    public void inicializarSkillPixies();
    public void actualizarTick(BDebuff debuff);

}
