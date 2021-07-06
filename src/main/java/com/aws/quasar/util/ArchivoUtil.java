package com.aws.quasar.util;

import com.aws.quasar.descifrador.DescifradorException;

import java.io.*;

/**
 * Utilidad encargada de leer y escribir en los archivos
 */
public class ArchivoUtil {

    /**
     * Escribe la data enviada en un archivo con el nombre del satelite
     * @param archivoName nombre del satelite
     * @param data data a escribir
     */
    public void escribir(String archivoName, String data)throws DescifradorException{
        File file=new File("/tmp/"+archivoName+".txt");
        file.getParentFile().mkdirs();
        try(FileWriter fw = new FileWriter("/tmp/"+archivoName+".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.write(data);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DescifradorException("Error al escribir el satelite "+archivoName+e.getMessage());
        }
    }

    /**
     * Lee el archivo para el satelite enviado
     * @param archivoName nombre del satelite a leer
     * @return data almacenada del satelite
     * @throws DescifradorException error de lectura
     */
    public String leer(String archivoName) throws DescifradorException {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("/tmp/"+archivoName+".txt"));
            String res= bf.readLine();
            bf.close();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
           throw new DescifradorException("Error al leer el satelite"+archivoName,e);
        }
    }
}
