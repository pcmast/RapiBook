package com.actividades.rapibookgit.baseDatos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@XmlRootElement(name = "connection")
@XmlAccessorType(XmlAccessType.FIELD)
public class H2ConnectionProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    private String server;
    private String port;       // H2 no usa puerto normalmente
    private String database;
    private String user;
    private String password;

    public H2ConnectionProperties() {}

    public String getServer() { return server; }
    public void setServer(String server) { this.server = server; }

    public String getPort() { return port; }
    public void setPort(String port) { this.port = port; }

    public String getDatabase() { return database; }
    public void setDatabase(String database) { this.database = database; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public static String getUrl() {
        try {
            // Carga el archivo SQL desde resources
            InputStream is = H2ConnectionDB.class.getResourceAsStream("/rapibook.sql");
            if (is == null) {
                throw new RuntimeException("No se pudo encontrar rapibook.sql en resources");
            }

            // Crea un archivo temporal en el directorio actual
            Path tempFile = Path.of("rapibook_temp.sql");
            Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);

            // Devuelve la URL de H2 con el RUNSCRIPT apuntando al archivo temporal
            String url = "jdbc:h2:./rapibook;MODE=MySQL;INIT=RUNSCRIPT FROM '" +
                    tempFile.toAbsolutePath().toString().replace("\\", "/") + "'";
            return url;

        } catch (IOException e) {
            throw new RuntimeException("Error al preparar el script SQL para H2", e);
        }
    }

    @Override
    public String toString() {
        return "H2ConnectionProperties{" +
                "server='" + server + '\'' +
                ", database='" + database + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}