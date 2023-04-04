package com.mycompany.calculadora1;

import java.util.HashMap;

public class EvaluadorDeExpresiones {
    public PilaLineal listac;
    public PilaLineal listai;
    public HashMap<Character, Integer> jerarquia = new HashMap();
    public String e;
    EvaluadorDeExpresiones(String e) {
        this.e = e;
        listac = new PilaLineal(e.length());
        listai = new PilaLineal(e.length());
    }
    
    public String evaluar() throws Exception {
        llenarJerarquia();
        expresion(e);
        while(!listac.pilaVacia()) {
            if(listai.cimav()>=1)
                operacion();
            else
                listac.quitar();
        }
        
        return (String)listai.cimaPila(); 
    }
    
    public void expresion(String s) throws Exception {
        String num = "";
        char c;
        boolean numNegativo = false;
        for(int i=0; i<s.length(); i++) {
            c = s.charAt(i);
            if(Character.isDigit(c) || c == '.') {
                num += c;
                while(i+1 < s.length() && (Character.isDigit(s.charAt(i+1)) || s.charAt(i+1) == '.')) {
                    i++;
                    c = s.charAt(i);
                    num += c;
                    if(i + 1 >= s.length())
                        break;
                }
                if(numNegativo) {
                    num = "-" + num;
                    numNegativo = false;
                }
                listai.insertar(num);
                num = "";
            } else if(c == '(') {
                listac.insertar(c);
                numNegativo = false;
            } else if(c == ')') {
                if(listai.cimav()>=1)
                    operacion();
                if((char)listac.cimaPila() == '(')
                    listac.quitar();
                else if(listai.cimav()>=1)
                    operacion();
                numNegativo = false;
            } else if(c == '~') {
                if (i+1 < s.length() && Character.isDigit(s.charAt(i+1))) {
                    numNegativo = true;
                } else {
                    listac.insertar('-');
                }
            } else if(listac.pilaVacia()) {
                listac.insertar(c);
                numNegativo = false;
            } else if(!listac.pilaVacia()) {
                if(jerarquia.get(listac.cimaPila()) < jerarquia.get(c)) {
                    listac.insertar(c);
                    numNegativo = false;
                } else {
                    operacion();
                    i--;
                    numNegativo = false;
                }
            }
        }
    }
    
    public void llenarJerarquia() {
        jerarquia.put('^', 4);
        jerarquia.put('x', 3);
        jerarquia.put('/', 3);
        jerarquia.put('+', 2);
        jerarquia.put('-', 2);
        jerarquia.put('(', 1);
    }
    
    public void operacion() throws Exception {
        double b = Double.parseDouble((String)listai.quitar());
        double a = 0;
        double res = 0;
        char operador = (char)listac.quitar();
        boolean operadorNegativo = false;

        if (listai.pilaVacia()) {
            if (operador == '-') {  // operador unario negativo
                operadorNegativo = true;
            } else {
                throw new Exception("Error: expresión mal formada.");
            }
        } else {
            a = Double.parseDouble((String)listai.quitar());
        }

        switch (operador) {
            case '^':
                res = Math.pow(a, b);
                break;
            case 'x':
                res = a * b;
                break;
            case '/':
                if (b == 0) {
                    throw new Exception("Error: división entre cero.");
                }
                res = a / b;
                break;
            case '+':
                res = a + b;
                break;
            case '-':
                if (operadorNegativo) {  // operador unario negativo
                    res = -b;
                } else {
                    res = a - b;
                }
                break;
            case '~':
                res = -b;
                break;
            default:
                throw new Exception("Error: operador inválido.");
        }

        listai.insertar(String.format("%.5f", res));
    }
}
