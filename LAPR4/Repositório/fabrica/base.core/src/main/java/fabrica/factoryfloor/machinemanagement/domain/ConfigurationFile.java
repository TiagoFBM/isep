package fabrica.factoryfloor.machinemanagement.domain;

import fabrica.production.domain.BriefDescription;

import javax.persistence.*;

@Entity //Só para JPA
public class ConfigurationFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Lob
    @Column(name = "configFileContent", columnDefinition="BLOB")
    private byte[] configFileContent;

    @Embedded
    @AttributeOverride(name = "description", column = @Column(name = "briefDescription"))
    private BriefDescription briefDescription;

    protected ConfigurationFile() {}

    private ConfigurationFile(byte[] content, String briefDescription) {
        if (!validateConfigurationFile(content)) {
            throw new IllegalArgumentException("Invalid Configuration File.");
        }
        this.configFileContent = content;
        this.briefDescription = BriefDescription.valueOf(briefDescription);
    }

    public byte[] obtainConfigurationFile(){return this.configFileContent;}

    public static ConfigurationFile valueOf(byte[] content, String briefDescription) {
        return new ConfigurationFile(content, briefDescription);
    }

    public boolean validateConfigurationFile(byte[] content){
        return content != null;
    }

    @Override
    public String toString() {
        return briefDescription.toString();
    }
}
