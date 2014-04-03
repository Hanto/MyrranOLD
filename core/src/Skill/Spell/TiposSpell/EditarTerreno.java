package Skill.Spell.TiposSpell;
// @author Ivan Delgado Huerta

import Geo.GeoBook;
import Interfaces.Caster;
import Main.Mundo;
import Skill.SkillStat;
import Skill.Spell.Data.TipoSpellsData;
import Skill.Spell.Spell;
import Skill.Spell.TipoSpell;
import com.badlogic.gdx.math.Vector2;

public class EditarTerreno extends TipoSpell
{
    public EditarTerreno (String id)                { super(id); }
    public EditarTerreno ()                         { }
    
    @Override public void inicializarSkillStats() 
    {
        setIcono(TipoSpellsData.EDITARTERRENO_Icono);

        skillStats = new SkillStat [1]; SkillStat stat;
        stat = new SkillStat  (TipoSpellsData.EDITARTERRENO_CastingTime_String, TipoSpellsData.EDITARTERRENO_CastingTime_Valor); skillStats[STAT_Cast]=stat;//CAST
    }

    @Override public void inicializarSkillPixies()  {}

    @Override public void ejecutarCasteo(Spell skill, Caster caster, float targetX, float targetY)
    {
        Vector2 destino = convertirCoordenadasDestino(caster, targetX, targetY);
        destino = convertirCoordenadasANumeroDeTile(destino);
        
        int x = (int)destino.x;
        int y = (int)destino.y;

        int numCapa = caster.getCapaTerrenoSeleccionada();
        String iDTerreno = Mundo.get().player.getTerrenoSeleccionado();

        GeoBook.get().getMapaControlador().setTerreno(x, y, numCapa, iDTerreno);
    }
}
