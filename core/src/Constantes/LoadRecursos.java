/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Constantes;
// @author Ivan Delgado Huerta

import Resources.Recursos;

public class LoadRecursos 
{
    public static void cargarRecursos ()
    {
        //RAZAS:
        //Golem:
        Recursos.añadirRaza();
        Recursos.salvarCuerpo(0, "Golem");
        
        Recursos.añadirRaza();
        Recursos.salvarCuerpo(0, "Golem");
        
        Recursos.salvarYelmo(0, "Desnudo");
        Recursos.salvarBotas(0, "Desnudo");
        Recursos.salvarGuantes(0, "Desnudo");
        Recursos.salvarHombreras(0, "Desnudo");
        Recursos.salvarPantalones(0, "Desnudo");
        Recursos.salvarPeto(0, "Desnudo");
        Recursos.salvarCapasTraseras(0, "Desnudo");
        Recursos.salvarCapasFrontales(0, "Desnudo");
        
        Recursos.salvarCabeza(0, "Cabeza1");
        
        Recursos.salvarBotas(0, "BotasGolem01");
        Recursos.salvarGuantes(0, "GuantesGolem01");
        Recursos.salvarPeto(0, "PetoGolem01");
        Recursos.salvarHombreras(0, "HombrerasGolem01");
        Recursos.salvarPantalones(0, "PantalonesGolem01");
        
        //ARBOLES:
        Recursos.salvarTronco("Tronco2", -50, 50, -45, 65, 40, 65);
        Recursos.salvarCopa("BolaGrande2");
        Recursos.salvarCopa("BolaMediana2");
        Recursos.salvarCopa("Bolapequenya2");
    }
}
