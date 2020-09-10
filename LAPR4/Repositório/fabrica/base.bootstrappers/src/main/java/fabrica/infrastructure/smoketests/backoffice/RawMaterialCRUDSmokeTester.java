package fabrica.infrastructure.smoketests.backoffice;


import eapli.framework.validations.Invariants;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.*;
import fabrica.production.repositories.RawMaterialCategoryRepository;
import fabrica.production.repositories.RawMaterialRepository;
import fabrica.production.services.ImportFileToBytesService;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class RawMaterialCRUDSmokeTester {

    private static final Logger LOGGER = Logger.getLogger(RawMaterialCRUDSmokeTester.class.getName());
    private final RawMaterialCategoryRepository repoCategories = PersistenceContext.repositories().rawMaterialCategories();
    private final RawMaterialRepository repo = PersistenceContext.repositories().rawMaterials();
    private final ImportFileToBytesService svc = new ImportFileToBytesService();

    public void testRawMaterials() throws IOException {

        //save
        DataSheet ds1 = new DataSheet(svc.transformToBytes("SmokeTesterFile1.pdf", "Files"), svc.obtainFileFormat("SmokeTesterFile.pdf"));
        DataSheet ds2 = new DataSheet(svc.transformToBytes("SmokeTesterFile2.pdf", "Files"), svc.obtainFileFormat("SmokeTesterFile.pdf"));
        repoCategories.save(new RawMaterialCategory("1000", "Lã"));
        final RawMaterialCategory category1 =repoCategories.ofIdentity(ShortAlfanumericCode.valueOf("1000")).orElseThrow(IllegalStateException::new);

        repo.save(new RawMaterial("RM1", "Produção de gorros", category1, ds1));
        repo.save(new RawMaterial("RM2", "Produção de camisolas", category1, ds2));
        LOGGER.info("»»» created raw materials");

        //findAll
        final Iterable<RawMaterial> l= repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all raw materials");

        //count
        final long n = repo.count();
        LOGGER.info("»»» # raw material = " + n);

        //ofIdentity
        final RawMaterial rm1 = repo.ofIdentity(AlfanumericCode.valueOf("RM1")).orElseThrow(IllegalStateException::new);
        final RawMaterial rm2 = repo.ofIdentity(AlfanumericCode.valueOf("RM2")).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found raw materials with identity");

        // containsOfIdentity
        final boolean hasId1 = repo.containsOfIdentity(rm1.identity());
        final boolean hasId2 = repo.containsOfIdentity(rm2.identity());
        Invariants.ensure(hasId1);
        Invariants.ensure(hasId2);
        LOGGER.info("»»» contains raw materials of identity");

        // contains
        final boolean has1 = repo.contains(rm1);
        final boolean has2 = repo.contains(rm2);
        Invariants.ensure(has1);
        Invariants.ensure(has2);
        LOGGER.info("»»» contains raw material");

        // delete
        repo.delete(rm1);
        LOGGER.info("»»» delete raw material");

        // deleteOfIdentity
        repo.deleteOfIdentity(rm2.identity());
        LOGGER.info("»»» delete raw material of identity");

        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # raw material = " + n2);
    }

}
