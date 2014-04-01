package Skill;

import Skill.Aura.Aura;
import Skill.Aura.Data.AurasData;
import Skill.Aura.Data.TipoAurasData;
import Skill.Aura.TipoAura;
import Skill.Aura.TiposAura.Bomba;
import Skill.Aura.TiposAura.Dot;
import Skill.Spell.Data.SpellsData;
import Skill.Spell.Data.TipoSpellsData;
import Skill.Spell.Spell;
import Skill.Spell.TipoSpell;
import Skill.Spell.TiposSpell.Bolt;
import Skill.Spell.TiposSpell.EditarMuro;
import Skill.Spell.TiposSpell.EditarTerreno;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//* @author Ivan Delgado Huerta

public class LoadSkills
{
    public static void inicializar(TextureAtlas atlas)
    {
        cargarRecursos(atlas);
        cargarListaDeTiposAura();
        cargarListaDeAuras();
        cargarListaDeTiposSpell();
        cargarListaDeSpells();
    }

    private static void cargarRecursos (TextureAtlas atlas)
    {
        //SpellPixies:
        SkillBook.get().salvarPixieCasteo(TipoSpellsData.BOLT_Pixie_Casteo_001, atlas);
        SkillBook.get().salvarPixieProyectil(TipoSpellsData.BOLT_Pixie_Proyectil_001, atlas);
        SkillBook.get().salvarPixieCasteo(TipoSpellsData.BOLT_Pixie_Casteo_002, atlas);
        SkillBook.get().salvarPixieProyectil(TipoSpellsData.BOLT_Pixie_Proyectil_002, atlas);
        //SpellIconos:
        SkillBook.get().salvarIconoSpell(SpellsData.FIREBOLT_Icono, atlas);
        SkillBook.get().salvarIconoSpell(SpellsData.FROSTBOLT_Icono, atlas);
        SkillBook.get().salvarIconoSpell(SpellsData.TERRAFORMAR_Icono, atlas);
        SkillBook.get().salvarIconoSpell(SpellsData.MUROFORMAR_Icono, atlas);
        //AuraIconos:
        SkillBook.get().salvarIconoAura(TipoAurasData.DOT_Icono, atlas);
        SkillBook.get().salvarIconoAura(TipoAurasData.BOMBA_Icono, atlas);
        SkillBook.get().salvarIconoAura(TipoAurasData.SNARE_Icono, atlas);
    }


    private static void cargarListaDeTiposAura ()
    {
        //DOT:
        TipoAura tipoAura = new Dot(TipoAurasData.DOT_ID);
        SkillBook.get().añadirTipoAura(tipoAura);
        //BOMBA:
        tipoAura = new Bomba(TipoAurasData.BOMBA_ID);
        SkillBook.get().añadirTipoAura(tipoAura);
    }
    
    private static void cargarListaDeAuras ()
    {
        //POISON DOT:
        Aura aura = new Aura(SkillBook.get().listaDeTiposAura.get(TipoAurasData.DOT_ID));
        aura.setId(AurasData.POISON_ID);
        aura.setNombre(AurasData.POISON_Nombre);
        aura.setDescripcion(AurasData.POISON_Descripcion);
        SkillBook.get().añadirAura(aura);
        
        //BOMBARETARDADA:
        aura = new Aura(SkillBook.get().listaDeTiposAura.get(TipoAurasData.BOMBA_ID));
        aura.setId(AurasData.DELAYBOMB_ID);
        aura.setNombre(AurasData.DELAYBOMB_Nombre);
        aura.setDescripcion(AurasData.DELAYBOMB_Descripcion);
        SkillBook.get().añadirAura(aura);
    }
    
    
    private static void cargarListaDeTiposSpell ()
    {   //BOLT:
        TipoSpell bolt = new Bolt(TipoSpellsData.BOLT_ID);
        SkillBook.get().añadirTipoSpell(bolt);
        //EDITAR TERRENO:
        TipoSpell editar = new EditarTerreno(TipoSpellsData.EDITARTERRENO_ID);
        SkillBook.get().añadirTipoSpell(editar);
        //EDITAR MURO:
        TipoSpell muro = new EditarMuro(TipoSpellsData.EDITARMURO_ID);
        SkillBook.get().añadirTipoSpell(muro);
        //HEAL:
        TipoSpell Heal = new Skill.Spell.TiposSpell.Heal(TipoSpellsData.HEAL_ID);
        SkillBook.get().añadirTipoSpell(Heal);
    }
    
    private static void cargarListaDeSpells ()
    {   //FIREBOLT: (Bolt)
        Spell skill = new Spell(SkillBook.get().listaDeTiposSpell.get(TipoSpellsData.BOLT_ID));
        skill.setId(SpellsData.FIREBOLT_ID);
        skill.setNombre(SpellsData.FIREBOLT_Nombre);
        skill.setDescripcion(SpellsData.FIREBOLT_Descripcion);
        skill.setIcono(SpellsData.FIREBOLT_Icono);
        skill.pixieSeleccionado()[1]=0;
        skill.pixieSeleccionado()[0]=0;
        SkillBook.get().añadirSpell(skill);
        
        //FROSBOLT: (Bolt)
        skill = new Spell(SkillBook.get().listaDeTiposSpell.get(TipoSpellsData.BOLT_ID));
        skill.setId(SpellsData.FROSTBOLT_ID);
        skill.setNombre(SpellsData.FROSTBOLT_Nombre);
        skill.setDescripcion(SpellsData.FROSTBOLT_Descripcion);
        skill.setIcono(SpellsData.FROSTBOLT_Icono);
        skill.pixieSeleccionado()[1]=1;
        skill.pixieSeleccionado()[0]=1;
        SkillBook.get().añadirSpell(skill);
        
        //TERRAFORMAR: (Editar Terreno)
        skill = new Spell(SkillBook.get().listaDeTiposSpell.get(TipoSpellsData.EDITARTERRENO_ID));
        skill.setId(SpellsData.TERRAFORMAR_ID);
        skill.setNombre(SpellsData.TERRAFORMAR_Nombre);
        skill.setDescripcion(SpellsData.TERRAFORMAR_Descripcion);
        skill.setIcono(SpellsData.TERRAFORMAR_Icono);
        SkillBook.get().añadirSpell(skill);
        
        //MUROFORMAR: (Editar Muro)
        skill = new Spell(SkillBook.get().listaDeTiposSpell.get(TipoSpellsData.EDITARMURO_ID));
        skill.setId(SpellsData.MUROFORMAR_ID);
        skill.setNombre(SpellsData.MUROFORMAR_Nombre);
        skill.setDescripcion(SpellsData.MUROFORMAR_Descripcion);
        skill.setIcono(SpellsData.MUROFORMAR_Icono);
        SkillBook.get().añadirSpell(skill);
        
        //INSTAHEAL:
        skill = new Spell(SkillBook.get().listaDeTiposSpell.get(TipoSpellsData.HEAL_ID));
        skill.setId(SpellsData.INSTAHEAL_ID);
        skill.setNombre(SpellsData.INSTAHEAL_Nombre);
        skill.setDescripcion(SpellsData.INSTAHEAL_Descripcion);
        skill.setIcono(SpellsData.INSTAHEAL_Icono);
        skill.añadirAura(SkillBook.get().listaDeAuras.get(AurasData.POISON_ID));
        skill.añadirAura(SkillBook.get().listaDeAuras.get(AurasData.DELAYBOMB_ID));
        SkillBook.get().añadirSpell(skill);
    }
    
    
}
