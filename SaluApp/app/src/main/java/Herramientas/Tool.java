package Herramientas;


public class Tool {

    private String titulo;
    private String subtitulo;
    private int imagen;

    public Tool(String titulo, String subtitulo, int imagen) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public int getImagen() {
        return imagen;
    }
}
