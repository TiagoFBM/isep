package fabrica.production.application;

import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.*;
import fabrica.production.dto.ProductionSheetLineDTO;
import fabrica.production.repositories.ProductRepository;
import fabrica.production.repositories.RawMaterialRepository;
import fabrica.production.services.ListProductService;
import fabrica.production.services.ListRawMaterialService;
import java.util.List;

public class SpecifyProductionSheetController {

    private final ProductRepository productRepository = PersistenceContext.repositories().products();
    private final RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawMaterials();
    private final ListProductService listProductService = new ListProductService();
    private final ListRawMaterialService listRawMaterialService = new ListRawMaterialService();

    public List<Product> listProductsWithoutProductionSheet(){
        return listProductService.allProductsWithoutProductionSheet();
    }

    public List<Product> allProducts(){
        return listProductService.allProducts();
    }

    public List<RawMaterial> allRawMaterials(){
        return listRawMaterialService.allRawMaterials();
    }

    public Product getProductWithCode(AlfanumericCode fabricationCode){
         if(productRepository.ofIdentity(fabricationCode).isPresent()){
             return productRepository.ofIdentity(fabricationCode).get();
         }
         return null;
    }


    public RawMaterial getRawMaterialWithCode(AlfanumericCode fabricationCode){
        if(rawMaterialRepository.ofIdentity(fabricationCode).isPresent()){
            return rawMaterialRepository.ofIdentity(fabricationCode).get();
        }
        return null;
    }

    public boolean specifyProductionSheet(Product p, List<ProductionSheetLineDTO> linesDTO, int quantidade, String unidade){
        boolean flag=true;
        p.createProductionSheet(quantidade,unidade);
        for(ProductionSheetLineDTO lineDTO : linesDTO){
            if(lineDTO.product!=null){
                if(!lineDTO.product.hasProductionSheet()){
                    flag=false;
                }
                p.addLineToProductionSheet(lineDTO.product, quantidade, unidade);
            } else {
                p.addLineToProductionSheet(lineDTO.rawMaterial, quantidade, unidade);
            }
        }
        productRepository.save(p);
        return flag;
    }

    public boolean verifyProductIsUnique(Product p, List<ProductionSheetLineDTO> linesDTO){
        for(ProductionSheetLineDTO dto : linesDTO){
            if(dto.product!=null && dto.product.equals(p)){
                return false;
            }
        }
        return true;
    }

    public boolean verifyRawMaterialIsUnique(RawMaterial rm, List<ProductionSheetLineDTO> linesDTO){
        for(ProductionSheetLineDTO dto : linesDTO){
            if(dto.rawMaterial!=null && dto.rawMaterial.equals(rm)){
                return false;
            }
        }
        return true;
    }
}
