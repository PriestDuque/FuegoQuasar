package com.aws.quasar.descifrador;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for Descifrador")
class DescifradorTest {

    @Test
    @DisplayName("Mensaje correcto tres satelites con informacion")
    void testGetMessage_messageylocationOK(){
        String[] mensajeKenobi=new String[]{"este", "", "", "mensaje", ""};
        String[] mensajeSkywalker=new String[]{"", "es", "", "", "secreto"};
        String[] mensajeSato=new String[]{"este", "", "un", "", ""};

        String[][] mensajes=new String[][]{mensajeKenobi,mensajeSkywalker,mensajeSato};

        Descifrador descifrador=new Descifrador();
        String res=descifrador.getMessage(mensajes);

        Assert.assertEquals("este es un mensaje secreto",res);
    }


    @Test
    @DisplayName("Mensaje incorrecto solo primer satelite")
    void testGetMessage_soloPrimerSatelite(){
        String[] mensajeKenobi=new String[]{"este", "es", "un", "mensaje", "secreto"};

        String[][] mensajes=new String[][]{mensajeKenobi,null,null};

        Descifrador descifrador=new Descifrador();

        Assert.assertNull(descifrador.getMessage(mensajes));
    }

    @Test
    @DisplayName("Mensaje incorrecto solo dos satelites")
    void testGetMessage_soloDosSatelite(){
        String[] mensajeKenobi=new String[]{"este", "", "un", "mensaje", ""};
        String[] mensajeSkywalker=new String[]{"", "es", "", "", "secreto"};

        String[][] mensajes=new String[][]{mensajeKenobi,mensajeSkywalker,null};

        Descifrador descifrador=new Descifrador();

        Assert.assertNull(descifrador.getMessage(mensajes));
    }

    @Test
    @DisplayName("Mensaje incorrecto solo dos satelites el tercero llega sin mensaje")
    void testGetMessage_soloDosSateliteTerceroVacio(){
        String[] mensajeKenobi=new String[]{"este", "", "un", "mensaje", ""};
        String[] mensajeSkywalker=new String[]{"", "es", "", "", "secreto"};
        String[] mensajeSato=new String[0];

        String[][] mensajes=new String[][]{mensajeKenobi,mensajeSkywalker,mensajeSato};

        Descifrador descifrador=new Descifrador();

        Assert.assertNull(descifrador.getMessage(mensajes));
    }

    @Test
    @DisplayName("Mensaje incompleto aun con tres satelites")
    void testGetMessage_messageIncompleto(){
        String[] mensajeKenobi=new String[]{"este", "", "", "mensaje", ""};
        String[] mensajeSkywalker=new String[]{"", "es", "", "","secreto"};
        String[] mensajeSato=new String[]{"este", "", "", "", ""};

        String[][] mensajes=new String[][]{mensajeKenobi,mensajeSkywalker,mensajeSato};

        Descifrador descifrador=new Descifrador();
        Assert.assertNull(descifrador.getMessage(mensajes));
    }


    @Test
    @DisplayName("Mensaje incorrecto mensajeSkywalker con mas datos")
    void testGetMessage_messageFormatoInvalido(){
        String[] mensajeKenobi=new String[]{"este", "", "", "mensaje", ""};
        String[] mensajeSkywalker=new String[]{"", "es", "", "", "","secreto"};
        String[] mensajeSato=new String[]{"este", "", "", "", ""};

        String[][] mensajes=new String[][]{mensajeKenobi,mensajeSkywalker,mensajeSato};

        Descifrador descifrador=new Descifrador();

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,() ->descifrador.getMessage(mensajes));
    }


}
