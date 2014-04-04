package Skill.Spell.TiposSpell;
// @author Ivan Delgado Huerta

import Geo.GeoBook;
import Interfaces.Caster;
import Skill.SkillStat;
import Skill.Spell.Data.TipoSpellsData;
import Skill.Spell.Spell;
import Skill.Spell.TipoSpell;
import com.badlogic.gdx.math.Vector2;

public class EditarMuro extends TipoSpell
{
    public EditarMuro (String id)                   { super(id); }
    public EditarMuro ()                            { } 
    
    @Override public void inicializarSkillStats() 
    {
        setIcono(TipoSpellsData.EDITARMURO_Icono);

        skillStats = new SkillStat [1]; SkillStat stat;
        stat = new SkillStat  (TipoSpellsData.EDITARMURO_CastingTime_String, TipoSpellsData.EDITARMURO_CastingTime_Valor); skillStats[STAT_Cast]=stat;//CAST
    }

    @Override public void inicializarSkillPixies()  {}

    @Override public void ejecutarCasteo(Spell skill, Caster caster, float targetX, float targetY)
    {
        //Vector2 destino = convertirCoordenadasDestino(caster, targetX, targetY);
        Vector2 destino = new Vector2(targetX, targetY);
        destino = convertirCoordenadasANumeroDeTile(destino);
        
        int x = (int)destino.x;
        int y = (int)destino.y;

        GeoBook.get().getMapaControlador().crearMuro(x, y, "Muro Standard");
    }
}
