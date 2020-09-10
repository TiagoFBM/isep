package lapr.project.spapp.model;

public class Availability {
    private Date oDateDisponibilidade;
    private Time oHoraInicio;
    private Time oHoraFinal;

    public Availability(Date oDateDisponibilidade, Time oHoraInicio, Time oHoraFinal) {
        this.oDateDisponibilidade = oDateDisponibilidade;
        this.oHoraInicio = oHoraInicio;
        this.oHoraFinal = oHoraFinal;
    }

    @Override
    public String toString() {
        return String.format( "Date: %s\n"
                            + "Horário: %s até %s", oDateDisponibilidade.toString(), oHoraInicio.toString(), oHoraFinal.toString());
    }

    public Date getoDataDisponibilidade() {
        return oDateDisponibilidade;
    }

    public Time getoHoraInicio() {
        return oHoraInicio;
    }

    public Time getoHoraFinal() {
        return oHoraFinal;
    }

    public void setHoraInicio(Time t) {
        oHoraInicio = t;
    }
}
