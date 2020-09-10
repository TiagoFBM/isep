package lapr.project.spapp.model;

import java.util.Objects;

public class PostalCode {

    private String m_oCodigoPostal;


    public PostalCode(String outroCodPostal){
        this.m_oCodigoPostal = outroCodPostal;
    }

    public String getCodPostal() {
        return m_oCodigoPostal;
    }

    public void setCodPostal(String m_oCodigoPostal) {
        this.m_oCodigoPostal = m_oCodigoPostal;
    }
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        PostalCode otherPostalCode = (PostalCode) otherObject;
        return this.m_oCodigoPostal.equalsIgnoreCase(otherPostalCode.m_oCodigoPostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_oCodigoPostal);
    }

}
