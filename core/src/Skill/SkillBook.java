package Skill;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Graficos.Pixie;
import Skill.Aura.Aura;
import Skill.Aura.TipoAura;
import Skill.Spell.Spell;
import Skill.Spell.TipoSpell;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class SkillBook 
{
    private volatile static SkillBook skillBook; //Singleton

    //Spells y Auras:
    public HashMap<String, TipoSpell>listaDeTiposSpell = new HashMap<>();
    public HashMap<String, Spell>listaDeSpells = new HashMap<>();
    public HashMap<String, TipoAura>listaDeTiposAura = new HashMap<>();
    public HashMap<String, Aura>listaDeAuras = new HashMap<>();

    //Resources:
    public HashMap<String, Pixie> listaDePixieProyectiles = new HashMap<>();
    public HashMap<String, Pixie> listaDePixieCasteos = new HashMap<>();
    public HashMap<String, TextureRegion> listaDeSpellIconos = new HashMap<>();
    public HashMap<String, TextureRegion> listaDeAuraIconos = new HashMap<>();

    public void añadirTipoSpell (TipoSpell tipoSkill)   { listaDeTiposSpell.put(tipoSkill.getID(), tipoSkill); }
    public void añadirSpell (Spell skill)               { listaDeSpells.put(skill.getId(), skill); }
    public void añadirTipoAura (TipoAura tipoAura)      { listaDeTiposAura.put(tipoAura.getID(), tipoAura); }
    public void añadirAura (Aura aura)                  { listaDeAuras.put(aura.getID(), aura); }

    //CONSTRUCTOR:
    private SkillBook () {}
    public static SkillBook get()
    {
        if (skillBook == null)
            synchronized (SkillBook.class)
            {
                if (skillBook == null)
                    skillBook = new SkillBook();
            }
        return skillBook;
    }

    public void inicializar (TextureAtlas atlas)        { LoadSkills.inicializar(atlas); }

    public void salvarPixieCasteo(String nombreEfecto, TextureAtlas atlas)
    {
        try
        {
            TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_SpellSprites_LOC + nombreEfecto));
            Pixie spellCasteo = new Pixie(texture, MiscData.PIXIE_SpellEffect_numFilas, MiscData.PIXIE_SpellEffect_numColumnas);
            spellCasteo.añadirAnimacion("casteo", new int[]{0, 1, 2}, 0.15F, false);
            spellCasteo.animaciones().get(0).animarYEliminar = true;
            listaDePixieCasteos.put(nombreEfecto, spellCasteo);
        }
        catch (Exception e) {System.out.println("ERROR Textura Pixie Casteo: ["+nombreEfecto+"] no encontrada."); }
    }

    public void salvarPixieProyectil(String nombreEfecto, TextureAtlas atlas)
    {
        try
        {
            TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_SpellSprites_LOC + nombreEfecto));
            Pixie spellEffect = new Pixie(texture, MiscData.PIXIE_SpellEffect_numFilas, MiscData.PIXIE_SpellEffect_numColumnas);
            spellEffect.añadirAnimacion("casteo", new int[]{0, 1, 2}, 0.15F, false);
            listaDePixieProyectiles.put(nombreEfecto, spellEffect);
        }
        catch (Exception e) {System.out.println("ERROR Textura Pixie Proyectil: ["+nombreEfecto+"] no encontrada."); }
    }

    public void salvarIconoSpell (String nombreIcono, TextureAtlas atlas)
    {
        try
        {
            TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_SpellIcons_LOC + nombreIcono));
            listaDeSpellIconos.put(nombreIcono, texture);
        }
        catch (Exception e) {System.out.println("ERROR Textura Icono Spell: ["+nombreIcono+"] no encontrada."); }
    }

    public void salvarIconoAura (String nombreIcono, TextureAtlas atlas)
    {
        try
        {
            TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_AuraIcons_LOC + nombreIcono));
            listaDeAuraIconos.put(nombreIcono, texture);
        }
        catch (Exception e) {System.out.println("ERROR Textura Icono Aura: ["+nombreIcono+"] no encontrada."); }
    }
}
