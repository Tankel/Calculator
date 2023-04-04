
package com.mycompany.calculadora1;

public class PilaLineal{
    
    private static int TAMPILA;
    private int cima;
    private Object[] listaPila;
    
    public PilaLineal(int x)
    {
        cima = -1;
        TAMPILA = x;
        listaPila = new Object[TAMPILA];
    }
   
    public void insertar (Object dato) throws Exception
    {
        if(pilaLlena())
            throw new Exception();
        
        cima++;
        listaPila[cima] = dato;
    }
    
    public Object quitar() throws Exception
    {
        Object aux;
        if(pilaVacia())
            throw new Exception();
        aux = listaPila[cima];
        cima--;
        return aux;
    }
    
    public Object cimaPila() throws Exception
    {
        if(pilaVacia())
                throw new Exception ();
        
        return listaPila[cima];
    }
    
    public int cimav()
    {
        return cima;
    }
    
    public boolean pilaVacia()
    {
        return cima == -1;
    }
    
    public boolean pilaLlena()
    {
        return cima == TAMPILA-1;
    }
    
    public void limpiarPila()
    {
        cima = -1;
    }  
}