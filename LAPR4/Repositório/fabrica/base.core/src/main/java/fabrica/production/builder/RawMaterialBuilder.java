package fabrica.production.builder;


import eapli.framework.domain.model.DomainFactory;
import eapli.framework.validations.Preconditions;
import fabrica.production.domain.*;
import fabrica.production.services.ImportFileToBytesService;

import java.io.File;

public class RawMaterialBuilder implements DomainFactory<RawMaterial> {

    public String code;
    public String description;
    private RawMaterialCategory category;
    private DataSheet dataSheet;



    public RawMaterialBuilder withCode(final String code){
        this.code = code;
        return this;
    }

    public RawMaterialBuilder withDescription(final String description){
        this.description = description;
        return this;
    }

    public RawMaterialBuilder withCategory(RawMaterialCategory category){

        this.category = category;
        return this;
    }

    public RawMaterialBuilder withDataSheet(DataSheet ds){
        Preconditions.nonNull(ds);
        this.dataSheet = ds;
        return this;
    }

    @Override
    public RawMaterial build() {
        return new RawMaterial(this.code, this.description, this.category, this.dataSheet);
    }
}
