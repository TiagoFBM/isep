package lapr.project.spapp.model;

public class DescriptionOrderService {

    private String strCategory;
    private String m_strDescricao;
    private String serviceType;
    private Time m_tmpDuracao;
    private int rate;

    public DescriptionOrderService(String strCategory, String m_novaDescricao, String serviceType, Time m_novoTime) {
        this.strCategory = strCategory;
        m_strDescricao = m_novaDescricao;
        this.serviceType = serviceType;
        m_tmpDuracao = m_novoTime;
        this.rate = -1;
    }


    public boolean validaDescricao(DescriptionOrderService m_oDescricao) {
        // Código de validação
        return true;
    }


    @Override
    public String toString() {
        return String.format("Descrição: %s \nDuração: %s", this.m_strDescricao, this.m_tmpDuracao);
    }

    public String getDescricao() {
        return this.m_strDescricao;
    }


    public Time getDuracao() {
        return m_tmpDuracao;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRate(){
        return this.rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DescriptionOrderService that = (DescriptionOrderService) o;
        return  m_tmpDuracao.equals(((DescriptionOrderService) o).getDuracao()) &&
                m_strDescricao.equalsIgnoreCase(((DescriptionOrderService) o).getDescricao()) &&
                this.rate==((DescriptionOrderService) o).getRate();
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getServiceType() {
        return serviceType;
    }
}
