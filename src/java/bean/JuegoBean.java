package bean;
import model.Pregunta;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Miguel Silva
 */
@ManagedBean(name = "bean")
@SessionScoped
public class JuegoBean {

    private int numero, cifra;
    private String pregunta, alt1, alt2, alt3, alt4, respuesta, respuestaJugador;

    private ArrayList<Pregunta> preguntas = new ArrayList<>();
    private ArrayList<Pregunta> listaEliminados = new ArrayList<>();
    private ArrayList<Pregunta> filtroPreguntas = new ArrayList<>();

    private ArrayList<Integer> preguntasUtilizadas = new ArrayList<>();
    private ArrayList<Pregunta> posiAzar = new ArrayList<>();

    /**
     * Creates a new instance of JuegoBean
     */
    public JuegoBean() {
        posiAzar.add(new Pregunta(1, "¿Cual no es un lenguaje de programación?", "Java", "C#", "Python", "HTML", "HTML"));
        preguntas.add(new Pregunta(2,"Una persona famelica esta:","Irritable","Hambrienta","Furiosa","Asustada","Hambrienta"));
        preguntas.add(new Pregunta(3,"Son las membranas movibles cubiertas de piel que resguardan los ojos:","Cejas","Pupilas","Parpados","Anteojos","Parpados"));
        preguntas.add(new Pregunta(4,"Según el refran, quien es ciego?","La esperanza","El amor","El odio","La envidia","El amor"));
        preguntas.add(new Pregunta(5,"Tecnica que describe y representa detalladamente la superficie de un terreno:","Topografía","Serigrafía","Epigrafía","Holografía","Topografía"));
        preguntas.add(new Pregunta(6,"Uno de los libros de J. K. Rowling es Harry Potter y:","El arca perdida","El prisionero de Azkaban","La magia negra","Su varita","El prisionero de Azkaban"));
        preguntas.add(new Pregunta(7,"Con que material esculpió Miguel Angel \"La Piedad\" ubicada en el Vaticano?","Bronce","Marmol","Madera","Yeso","Marmol"));
        preguntas.add(new Pregunta(8,"¿Qué tipo de unidad es el droide R2-D2?","Reparaciones","Utilitario","Astromecánico","Compañía","Astromecánico"));
        preguntas.add(new Pregunta(9,"Cual es el idioma oficial de Israel?","Japones","Griego","Hebreo","Chino","Hebreo"));
        preguntas.add(new Pregunta(10,"El timbal es un instrumento de","Percusion","Viento metal","Viento madera","Cuerda","Percusion"));
        cifra = 25000;
    }
    
    public String jugar() throws IOException {
        if (preguntasUtilizadas.size() == preguntas.size()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("TERMINADO, HAS GANADO"));
            nuevoJuego();
            FacesContext.getCurrentInstance().getExternalContext().redirect("Ganaste.xhtml");
        }
        else{
            if (posiAzar.get(0).getRespuesta().equals(respuestaJugador)) {
                cifra *= 2;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Respuesta correcta, has ganado $" + cifra + "pesos"));
                posiAzar.clear();
                Pregunta p = null;
                while(p == null){
                    p = obtenerNoRepetida();
                }
                preguntasUtilizadas.add(p.getNumero());
                posiAzar.add(p);
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Respuesta incorrecta"));
                nuevoJuego();
                FacesContext.getCurrentInstance().getExternalContext().redirect("Perdiste.xhtml");
            }

        }
    return "";
    }

    public Pregunta obtenerNoRepetida(){
        Random rnd = new Random();
        Pregunta p = preguntas.get(rnd.nextInt(preguntas.size()));
        if (preguntasUtilizadas.contains(p.getNumero())) {
            return null;
        }
        else{
            return p; 
        }
    }
    
    public String crearPregunta(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String pregunta, alt1, alt2, alt3, alt4, respuesta;
        int numero = 0;
        
        numero = preguntas.size() + 2;
        pregunta = request.getParameter("form:txtPregunta");
        alt1 = request.getParameter("form:txtAlt1");
        alt2 = request.getParameter("form:txtAlt2");
        alt3 = request.getParameter("form:txtAlt3");
        alt4 = request.getParameter("form:txtAlt4");
        respuesta = request.getParameter("form:txtRespuesta");

        Pregunta pre = new Pregunta(numero, pregunta, alt1, alt2, alt3, alt4, respuesta);
        preguntas.add(pre);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pregunta ingresada!!"));
        return "";
    }
    
    public int encuentra(int num) {
        int cont = 0;
        for (int i = 0; i < preguntasUtilizadas.size(); i++) {
            if (num == (int) preguntasUtilizadas.get(i)) {
                cont++;
            }
        }
        return cont;
    }
    
    public String nuevoJuego() {
        preguntasUtilizadas.clear();
        posiAzar.clear();
        posiAzar.add(new Pregunta(1, "¿Cual no es un lenguaje de programación?", "Java", "C#", "Python", "HTML", "HTML"));
        cifra = 10000;
        return null;
    }

    public String terminarJuego() {
        return null;
    }


//---------------------------------------------------------------------------------------------------------------
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCifra() {
        return cifra;
    }

    public void setCifra(int cifra) {
        this.cifra = cifra;
    }
    
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getAlt1() {
        return alt1;
    }

    public void setAlt1(String alt1) {
        this.alt1 = alt1;
    }

    public String getAlt2() {
        return alt2;
    }

    public void setAlt2(String alt2) {
        this.alt2 = alt2;
    }

    public String getAlt3() {
        return alt3;
    }

    public void setAlt3(String alt3) {
        this.alt3 = alt3;
    }

    public String getAlt4() {
        return alt4;
    }

    public void setAlt4(String alt4) {
        this.alt4 = alt4;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getRespuestaJugador() {
        return respuestaJugador;
    }

    public void setRespuestaJugador(String respuestaJugador) {
        this.respuestaJugador = respuestaJugador;
    }

    public ArrayList<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public ArrayList<Pregunta> getPosiAzar() {
        return posiAzar;
    }

    public void setPosiAzar(ArrayList<Pregunta> posiAzar) {
        this.posiAzar = posiAzar;
    }
    
    public ArrayList getPreguntasUtilizadas() {
        return preguntasUtilizadas;
    }

    public void setPreguntasUtilizadas(ArrayList preguntasUtilizadas) {
        this.preguntasUtilizadas = preguntasUtilizadas;
    }
    
    public ArrayList<Pregunta> getListaEliminados() {
        return listaEliminados;
    }

    public void setListaEliminados(ArrayList<Pregunta> listaEliminados) {
        this.listaEliminados = listaEliminados;
    }

    public ArrayList<Pregunta> getFiltroPreguntas() {
        return filtroPreguntas;
    }

    public void setFiltroPreguntas(ArrayList<Pregunta> filtroPreguntas) {
        this.filtroPreguntas = filtroPreguntas;
    }
}
