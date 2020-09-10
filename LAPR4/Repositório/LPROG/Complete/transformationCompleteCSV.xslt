<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="separator" select="';'"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/fabrica">
        <xsl:text>=============================</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:text>           Factory           </xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:text>=============================</xsl:text>
        <xsl:if test="count(chaoDeFabrica/*) &gt; 0" ><xsl:apply-templates select="chaoDeFabrica"/></xsl:if>
        <xsl:value-of select="$newline"/>
        <xsl:if test="count(producao/*) &gt; 0" ><xsl:apply-templates select="producao"/></xsl:if>
    </xsl:template>

    <xsl:template match="producao">
        <xsl:value-of select="$newline"/>
        <xsl:value-of select="$newline"/>
        <xsl:text>========</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:text>Produção</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:text>========</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="Products"/>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="RawMaterials"/>
        <xsl:apply-templates select="RawMaterialCategorys"/>
        <xsl:apply-templates select="ProductionOrders"/>
    </xsl:template>

    <xsl:template match="chaoDeFabrica">
        <xsl:value-of select="$newline"/>
        <xsl:value-of select="$newline"/>
        <xsl:text>===============</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:text>Chão de Fábrica</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:text>===============</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="ProductionLines"/>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="Deposits"/>
    </xsl:template>

    <xsl:template match="ProductionLines">
        <xsl:text>Internal Code;Description</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="ProductionLine"/>
        <xsl:value-of select="$newline"/>
        <xsl:text>Production Line Internal Code;Serial Number;Identification Number;Manufacturer;Model; Operation; Installation Date</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="ProductionLine/Machines/Machine"/>
    </xsl:template>

    <xsl:template match="Deposits">
        <xsl:text>Internal Code;Description</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="Deposit"/>
    </xsl:template>

    <xsl:template match="Deposit">
        <xsl:value-of select="@internalCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="desc"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="ProductionLine">
        <xsl:value-of select="@internalCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="desc"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="Machines/Machine">
        <xsl:value-of select="../../@internalCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="@serialNumber"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="identificationNumber"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="manufacturer"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="model"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="operation"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="installationDate"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="Products">
        <xsl:text>Fabrication Code;Comercial Code;Brief Description;Full Description;Product Category;Unity</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="Product"/>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="Product/ProductSheet"/>
    </xsl:template>

    <xsl:template match="Product">
        <xsl:value-of select="@fabricationCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="comercialCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="briefDescription"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="fullDescription"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="productCategory"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="unit"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="Product/ProductSheet">
        <xsl:text>Product Fabrication Code;Quantity;Unity;Raw Material Code;Product Code;</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:value-of select="../@fabricationCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="Quantity/valor"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="Quantity/unity"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="ProductionSheetLine/RawMaterial/@code"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="ProductionSheetLine/Product/@fabricationCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="RawMaterials">
        <xsl:text>Raw Material Code;Description;Raw Material Category ID;Raw Material Category Description;</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="RawMaterial"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="RawMaterial">
        <xsl:value-of select="@code"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="description"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="RawMaterialCategory/@categoryID"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="RawMaterialCategory/briefDescription"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="RawMaterialCategorys">
        <xsl:text>Raw Material Category Code;Raw Material Category Description;</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="RawMaterialCategory"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="RawMaterialCategory">
        <xsl:value-of select="@categoryID"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="briefDescription"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="ProductionOrders">
        <xsl:text>Production Order Identifier;Emission Date;Prevision Date;Product Fabrication Code;Amount (unit);Standard Unit;[Requests];State;Production Line Internal Code</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="ProductionOrder"/>

    </xsl:template>

    <xsl:template match="ProductionOrder">
        <xsl:value-of select="@POCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="emissionDate"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="previsionDate"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="Product/@fabricationCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:apply-templates select="Quantity"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="unit"/>
        <xsl:value-of select="$separator"/>
        <xsl:text>[</xsl:text>
        <xsl:apply-templates select="Requests/request"/>
        <xsl:text>]</xsl:text>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="state"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="ProductionLine/@internalCode"/>
        <xsl:value-of select="$newline"/>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="ProductionOrderReport"/>
    </xsl:template>

    <xsl:template match="Requests/request">
        <xsl:value-of select="code"/>
        <xsl:if test="not (position() = last())"><xsl:value-of select="$separator"/></xsl:if>
    </xsl:template>

    <xsl:template match="ProductionOrderReport">
        <xsl:text>ProductionOrderReport</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="Consumptions"/>
        <xsl:apply-templates select="Chargebacks"/>
        <xsl:apply-templates select="Productions"/>
        <xsl:apply-templates select="ProductionDeliverys"/>
        <xsl:apply-templates select="MachineTimes"/>
    </xsl:template>

    <xsl:template match="Consumptions">
        <xsl:text>Consumptions</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:text>Production Order Identifier;Amount (unit);Movement Date;Deposit identifier;Product Fabrication Code;Raw Material Code</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="consumption"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="Chargebacks">
        <xsl:text>Chargebacks</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:text>Production Order Identifier;Amount (unit);Deposit identifier;Product Fabrication Code;Raw Material Code</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="chargeback"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="Productions">
        <xsl:text>Productions</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:text>Production Order Identifier;Product Fabrication Code;Amount (unit);Allotment</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="production"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="ProductionDeliverys">
        <xsl:text>ProductionDeliverys</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:text>Production Order Identifier;Product Fabrication Code;Amount (unit);Deposit Identifier;Allotment</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="productionDelivery"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="MachineTimes">
        <xsl:text>MachineTimes</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:text>Production Order Identifier;Machine Serial Number;Date and Time;New Machine State</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="machineTime"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="consumption">
        <xsl:value-of select="../../../@POCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:apply-templates select="Quantity"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="movementDate"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="Deposit/@internalCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="Product/@fabricationCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="RawMaterial/@code"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="chargeback">
        <xsl:value-of select="../../../@POCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:apply-templates select="Quantity"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="Deposit/@internalCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="Product/@fabricationCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="RawMaterial/@code"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="production">
        <xsl:value-of select="../../../@POCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="Product/@fabricationCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:apply-templates select="Quantity"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="allotment"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="productionDelivery">
        <xsl:value-of select="../../../@POCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="Product/@fabricationCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:apply-templates select="Quantity"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="Deposit/@internalCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="allotment"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="machineTime">
        <xsl:value-of select="../../../@POCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="Machine/@serialNumber"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="date"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="newStatus"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="Quantity">
        <xsl:value-of select="valor"/>
        <xsl:text> (</xsl:text>
        <xsl:value-of select="unity"/>
        <xsl:text>)</xsl:text>
    </xsl:template>


</xsl:stylesheet>
