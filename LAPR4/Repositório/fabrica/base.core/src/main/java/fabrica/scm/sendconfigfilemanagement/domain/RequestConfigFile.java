package fabrica.scm.sendconfigfilemanagement.aplication.domain;

import eapli.framework.domain.model.AggregateRoot;
import fabrica.factoryfloor.machinemanagement.domain.ConfigurationFile;
import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;

import javax.persistence.*;

@Entity
public class RequestConfigFile implements AggregateRoot<ProtocolIdentificationNumber> {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Embedded
    @Column(name = "IDENTIFICATIONNUMBER")
    private ProtocolIdentificationNumber protocolID;

    @OneToOne
    private ConfigurationFile configFile;

    private int state;


    protected RequestConfigFile(){

    }

    public RequestConfigFile(ProtocolIdentificationNumber protocolID, ConfigurationFile file){
        this.protocolID = protocolID;
        this.configFile = file;
        this.state = 0;
    }


    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public ProtocolIdentificationNumber identity() {
        return this.protocolID;
    }

    public ConfigurationFile obtainConfigFile() {
        return this.configFile;
    }

    public void updateState(){this.state = 1;}
}
