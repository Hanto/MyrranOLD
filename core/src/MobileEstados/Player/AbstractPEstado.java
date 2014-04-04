package MobileEstados.Player;
// @author Ivan Delgado Huerta

import Actores.Mobs.Personajes.PCs.Player;
import MobileEstados.Player.PlayerEstado.Estado;
import Skill.SkillBook;

public abstract class AbstractPEstado implements Estado
{
    public void castear (PlayerEstado playerE)
    {
        Player player = playerE.player;
        if (player.castear && !player.isCasteando && player.getSpellSeleccionado().length() > 0)
        {
            SkillBook.get().listaDeSpells.get(player.getSpellSeleccionado()).
                    castear(player, (int)playerE.clickPantalla.x, (int)playerE.clickPantalla.y);
        }
    }
    
    public void aplicarMovimiento (PlayerEstado playerE)
    {
        Player player = playerE.player;
        player.rumboNorte = player.irArriba;
        player.rumboSur = player.irAbajo;
        player.rumboEste = player.irDerecha;
        player.rumboOeste = player.irIzquierda;
    }
    
    public boolean estaQuieto (PlayerEstado playerE)
    {
        Player player = playerE.player;
        return (!player.irArriba && !player.irAbajo && !player.irDerecha && !player.irIzquierda);
    }
}
