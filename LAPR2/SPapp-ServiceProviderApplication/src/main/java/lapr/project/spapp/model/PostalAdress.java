/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.spapp.model;

import lapr.project.spapp.controller.GPSDApplication;

import java.util.Objects;

/**
 * @author paulomaio
 */
public class PostalAdress {
    /**
     * Instance variables.
     */
    private String m_strLocal;
    private PostalCode m_oPostalCode;
    private String m_strLocalidade;

    /**
     * Constructor.
     * @param strLocal
     * @param strCodPostal
     * @param strLocalidade 
     */
    public PostalAdress(String strLocal, String strCodPostal, String strLocalidade) {
        if ((strLocal == null) || (strCodPostal == null) || (strLocalidade == null) ||
                (strLocal.isEmpty()) || (strCodPostal.isEmpty()) || (strLocalidade.isEmpty()))
            throw new IllegalArgumentException("Nenhum dos argumentos pode ser nulo ou vazio.");

        this.m_strLocal = strLocal;
        this.m_oPostalCode = createPostalCode(strCodPostal);
        this.m_strLocalidade = strLocalidade;
    }

    /**
     * Method hash code.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hash(this.m_strLocal, this.getCodPostal(), this.m_strLocalidade);
        return hash;
    }

    /**
     * Method equals.
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o) {
        // Inspirado em https://www.sitepoint.com/implement-javas-equals-method-correctly/

        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        // field comparison
        PostalAdress obj = (PostalAdress) o;
        return (Objects.equals(m_strLocal, obj.m_strLocal) &&
                Objects.equals(getCodPostal(), obj.getCodPostal()) &&
                Objects.equals(m_strLocalidade, obj.m_strLocalidade));
    }

    /**
     * Returns the postal address in a string.
     * @return 
     */
    @Override
    public String toString() {
        return String.format("%s \n %s - %s", this.m_strLocal, this.getCodPostalString(), this.m_strLocalidade);
    }

    /**
     * Returns the postal code in string format.
     * @return 
     */
    public String getCodPostalString() {
        return m_oPostalCode.getCodPostal();
    }

    /**
     * Returns the postal code in PostalCode format.
     * @return 
     */
    public PostalCode getCodPostal() {
        return m_oPostalCode;
    }

    /**
     * Modify postal code.
     * @param m_strCodPostal 
     */
    public void setCodPostal(String m_strCodPostal) {
        this.m_oPostalCode.setCodPostal(m_strCodPostal);
    }

    /**
     * Creates a new PostalCode.
     * @param strPostalCode
     * @return 
     */
    public PostalCode createPostalCode(String strPostalCode) {
        return new PostalCode(strPostalCode);
    }

    /**
     * Returns the local.
     * @return 
     */
    public String getM_strLocal() {
        return m_strLocal;
    }

    /**
     * Method compareTo.
     * @param endereco
     * @return 
     */
    public int compareTo(PostalAdress endereco) {
        return this.getM_strLocal().compareTo(endereco.getM_strLocal());
    }
}
