package lapr.project.Utils;

import javafx.scene.control.Alert;
import lapr.project.spapp.model.Date;
import lapr.project.spapp.model.Time;

public class Utils {
    public static Alert createAlert(Alert.AlertType alertType, String title, String header,
                                    String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle(title);
        alerta.setHeaderText(header);
        alerta.setContentText(message);

        return alerta;
    }

    public static Time novoTempo(String strTempo) {
        String[] vecTempo = strTempo.split(":");
        int horas = Integer.parseInt(vecTempo[0]);
        int minutos = Integer.parseInt(vecTempo[1]);
        int segundos = Integer.parseInt(vecTempo[2]);
        return new Time(horas, minutos, segundos);
    }

    public static Date novaData(String strData, String separador) {
        String[] vecData = strData.split(separador);
        int ano = Integer.parseInt(vecData[0]);
        int mes = Integer.parseInt(vecData[1]);
        int dia = Integer.parseInt(vecData[2]);
        return new Date(ano, mes, dia);
    }
}
