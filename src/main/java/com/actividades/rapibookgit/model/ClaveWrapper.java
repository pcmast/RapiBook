package com.actividades.rapibookgit.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

import java.io.Serializable;

@XmlRootElement(name = "clave")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClaveWrapper implements Serializable {

    @XmlValue
    private String clave;

    public String getValue() {
        return clave;
    }

    public void setValue(String value) {
        this.clave = value;
    }

}
