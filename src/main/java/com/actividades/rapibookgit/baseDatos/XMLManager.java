package com.actividades.rapibookgit.baseDatos;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class XMLManager {

    /**
     * Escribe un objeto en un archivo XML.
     * @param <T> Tipo de objeto a guardar.
     * @param c Objeto que se desea guardar en formato XML.
     * @param filename Nombre del archivo donde se guardará el objeto.
     * @return True si el objeto se guardó correctamente, false en caso contrario.
     */
    public static <T> boolean writeXML(T c,String filename){
        boolean result=false;
        JAXBContext context;
        try{
            context = JAXBContext.newInstance(c.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            m.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
            m.marshal(c,new File(filename));
            result=true;
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lee un objeto desde un archivo XML.
     * @param <T> Tipo de objeto a leer.
     * @param c Objeto que se usará como referencia para el tipo de datos.
     * @param filename Nombre del archivo XML desde donde se leerá el objeto.
     * @return El objeto leído desde el archivo XML.
     */
    public static<T> T readXML(T c,String filename){
        T result = c;
        JAXBContext context;

        try{
            context = JAXBContext.newInstance(c.getClass());
            Unmarshaller um = context.createUnmarshaller();
            result = (T) um.unmarshal(new File(filename));
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return result;
    }


}
