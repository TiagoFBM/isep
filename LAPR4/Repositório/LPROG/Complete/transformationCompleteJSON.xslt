<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/">
        <xsl:text>{</xsl:text>
        <xsl:apply-templates select="fabrica"/>
        <xsl:text>}</xsl:text>
    </xsl:template>

    <xsl:template match="fabrica">
        <xsl:text>"fabrica": {</xsl:text>
        <xsl:if test="count(producao/*) &gt; 0" ><xsl:apply-templates select="producao"/></xsl:if>
        <xsl:if test="count(chaoDeFabrica/*) &gt; 0" ><xsl:apply-templates select="chaoDeFabrica"/></xsl:if>
        <xsl:text>}</xsl:text>
    </xsl:template>

    <xsl:template match="chaoDeFabrica">
        <xsl:text>, "chaoDeFabrica": {</xsl:text>
        <xsl:apply-templates select="ProductionLines"/>
        <xsl:apply-templates select="Deposits"/>
        <xsl:text>}</xsl:text>
    </xsl:template>

    <xsl:template match="producao">
        <xsl:text>"producao": {</xsl:text>
        <xsl:apply-templates select="Products"/>
        <xsl:apply-templates select="RawMaterials"/>
        <xsl:apply-templates select="RawMaterialCategorys"/>
        <xsl:apply-templates select="ProductionOrders"/>
        <xsl:text>}</xsl:text>
    </xsl:template>

    <xsl:template match="Products">
        <xsl:text>"Product": [</xsl:text>
        <xsl:apply-templates select="Product"/>
        <xsl:text>]</xsl:text>
        <xsl:if test="position() = last()">,</xsl:if>
    </xsl:template>

    <xsl:template match="Product">
        <xsl:text>{ "fabricationCode": "</xsl:text>
        <xsl:value-of select="@fabricationCode"/>
        <xsl:text>", "comercialCode": "</xsl:text>
        <xsl:value-of select="comercialCode"/>
        <xsl:text>", "briefDescription": "</xsl:text>
        <xsl:value-of select="briefDescription"/>
        <xsl:text>", "fullDescription": "</xsl:text>
        <xsl:value-of select="fullDescription"/>
        <xsl:text>", "productCategory": "</xsl:text>
        <xsl:value-of select="productCategory"/>
        <xsl:text>", "unit": "</xsl:text>
        <xsl:value-of select="unit"/>
        <xsl:text>"</xsl:text>
        <xsl:apply-templates select="ProductSheet"/>
        <xsl:text>}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductSheet">
        <xsl:text>, "ProductSheet": {</xsl:text>
        <xsl:text>"Quantity": {</xsl:text>
        <xsl:text>"valor": "</xsl:text>
        <xsl:value-of select="Quantity/valor"/>
        <xsl:text>", "unit": "</xsl:text>
        <xsl:value-of select="Quantity/unity"/>
        <xsl:text>" },</xsl:text>
        <xsl:text>"ProductionSheetLine": [</xsl:text>
        <xsl:apply-templates select="ProductionSheetLine"/>
        <xsl:text>]</xsl:text>
        <xsl:text>}</xsl:text>
    </xsl:template>

    <xsl:template match="ProductionSheetLine">
        <xsl:text>{</xsl:text>
        <xsl:if test="RawMaterial">"RawMaterial":</xsl:if>
        <xsl:apply-templates select="RawMaterial"/>
        <xsl:if test="Product">"Product":</xsl:if>
        <xsl:apply-templates select="Product"/>
        <xsl:text>,</xsl:text>
        <xsl:apply-templates select="Quantity"/>
        <xsl:text>}</xsl:text>
    </xsl:template>

    <xsl:template match="RawMaterial">
        <xsl:text>{</xsl:text>
        <xsl:text>"code": "</xsl:text>
        <xsl:value-of select="@code"/>
        <xsl:text>", "description": "</xsl:text>
        <xsl:value-of select="description"/>
        <xsl:text>", "RawMaterialCategory": {</xsl:text>
        <xsl:text>"categoryID": "</xsl:text>
        <xsl:value-of select="RawMaterialCategory/@categoryID"/>
        <xsl:text>", "briefDescription": "</xsl:text>
        <xsl:value-of select="RawMaterialCategory/briefDescription"/>
        <xsl:text>" },</xsl:text>
        <xsl:text>"DataSheet": {</xsl:text>
        <xsl:text>"Referency": "</xsl:text>
        <xsl:value-of select="DataSheet/Referency"/>
        <xsl:text>" }</xsl:text>
        <xsl:if test="parent::ProductionSheetLine" >}</xsl:if>
        <xsl:if test="parent::RawMaterials" >}</xsl:if>
        <xsl:if test="(parent::RawMaterials and not (position() = last()))">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionSheetLine/Product">
        <xsl:text>{ "fabricationCode": "</xsl:text>
        <xsl:value-of select="@fabricationCode"/>
        <xsl:text>", "comercialCode": "</xsl:text>
        <xsl:value-of select="comercialCode"/>
        <xsl:text>", "briefDescription": "</xsl:text>
        <xsl:value-of select="briefDescription"/>
        <xsl:text>", "fullDescription": "</xsl:text>
        <xsl:value-of select="fullDescription"/>
        <xsl:text>", "productCategory": "</xsl:text>
        <xsl:value-of select="productCategory"/>
        <xsl:text>", "unit": "</xsl:text>
        <xsl:value-of select="unit"/>
        <xsl:text>"</xsl:text>
        <xsl:text>}</xsl:text>
    </xsl:template>

    <xsl:template match="Quantity">
        <xsl:text>"Quantity": {</xsl:text>
        <xsl:text>"valor": "</xsl:text>
        <xsl:value-of select="valor"/>
        <xsl:text>", "unit": "</xsl:text>
        <xsl:value-of select="unity"/>
        <xsl:text>" }</xsl:text>
    </xsl:template>

    <xsl:template match="Deposits">
        <xsl:text>"Deposit": [</xsl:text>
        <xsl:apply-templates select="Deposit"/>
        <xsl:text>]</xsl:text>
        <xsl:if test="position() = last()">,</xsl:if>
    </xsl:template>

    <xsl:template match="Deposit">
        <xsl:text>{ "internalCode": "</xsl:text>
        <xsl:value-of select="@internalCode"/>
        <xsl:text>", "desc": "</xsl:text>
        <xsl:value-of select="desc"/>
        <xsl:text>"</xsl:text>
        <xsl:text>}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionLines">
        <xsl:text>"ProductionLine": [</xsl:text>
        <xsl:apply-templates select="ProductionLine"/>
        <xsl:text>]</xsl:text>
        <xsl:if test="position() = last()">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionLine">
        <xsl:text>{ "internalCode": "</xsl:text>
        <xsl:value-of select="@internalCode"/>
        <xsl:text>", "desc": "</xsl:text>
        <xsl:value-of select="desc"/>
        <xsl:text>"</xsl:text>
        <xsl:apply-templates select="Machines"/>
        <xsl:text>}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionLine/Machines">
        <xsl:text>, "Machines": {</xsl:text>
        <xsl:text> "Machine": [</xsl:text>
        <xsl:apply-templates select="Machine"/>
        <xsl:text>]</xsl:text>
        <xsl:text>}</xsl:text>
    </xsl:template>

    <xsl:template match="Machine">
        <xsl:text>{ "serialNumber": "</xsl:text>
        <xsl:value-of select="@serialNumber"/>
        <xsl:text>", "identificationNumber": "</xsl:text>
        <xsl:value-of select="identificationNumber"/>
        <xsl:text>", "manufacturer": "</xsl:text>
        <xsl:value-of select="manufacturer"/>
        <xsl:text>", "model": "</xsl:text>
        <xsl:value-of select="model"/>
        <xsl:text>", "operation": "</xsl:text>
        <xsl:value-of select="operation"/>
        <xsl:text>", "installationDate": "</xsl:text>
        <xsl:value-of select="installationDate"/>
        <xsl:text>"</xsl:text>
        <xsl:text>}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="RawMaterials">
        <xsl:text>"RawMaterial": [</xsl:text>
        <xsl:apply-templates select="RawMaterial"/>
        <xsl:text>]</xsl:text>
        <xsl:if test="position() = last()">,</xsl:if>
    </xsl:template>

    <xsl:template match="RawMaterialCategorys">
        <xsl:text>"RawMaterialCategory": [</xsl:text>
        <xsl:apply-templates select="RawMaterialCategory"/>
        <xsl:text>]</xsl:text>
        <xsl:if test="position() = last()">,</xsl:if>
    </xsl:template>

    <xsl:template match="RawMaterialCategory">
        <xsl:text>{ "categoryID": "</xsl:text>
        <xsl:value-of select="@categoryID"/>
        <xsl:text>", "briefDescription": "</xsl:text>
        <xsl:value-of select="briefDescription"/>
        <xsl:text>" }</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionOrders">
        <xsl:text>"ProductionOrders": [</xsl:text>
        <xsl:apply-templates select="ProductionOrder"/>
        <xsl:text>] </xsl:text>
        <xsl:if test="position() = last()">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionOrder">
        <xsl:text>{ "POCode": "</xsl:text>
        <xsl:value-of select="@POCde"/>
        <xsl:text>", "emissionDate": "</xsl:text>
        <xsl:value-of select="emissionDate"/>
        <xsl:text>", "previsionDate": "</xsl:text>
        <xsl:value-of select="previsionDate"/>
        <xsl:text>", "productFabricationCode": "</xsl:text>
        <xsl:value-of select="Product/@fabricationCode"/>
        <xsl:text>",</xsl:text>
        <xsl:apply-templates select="Quantity"/>
        <xsl:text>,"unit": "</xsl:text>
        <xsl:value-of select="unit"/>
        <xsl:text>",</xsl:text>
        <xsl:apply-templates select="Requests"/>
        <xsl:text>, "state": "</xsl:text>
        <xsl:value-of select="state"/>
        <xsl:text>", "productionLineInternalCode": "</xsl:text>
        <xsl:value-of select="ProductionLine/@internalCode"/>
        <xsl:text>"</xsl:text>
        <xsl:apply-templates select="ProductionOrderReport"/>
        <xsl:text>}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionOrderReport">
        <xsl:text>, "ProductionOrderReport": {</xsl:text>
        <xsl:apply-templates select="Consumptions"/>
        <xsl:text>,</xsl:text>
        <xsl:apply-templates select="Chargebacks"/>
        <xsl:text>,</xsl:text>
        <xsl:apply-templates select="Productions"/>
        <xsl:text>,</xsl:text>
        <xsl:apply-templates select="ProductionDeliverys"/>
        <xsl:text>,</xsl:text>
        <xsl:apply-templates select="MachineTimes"/>
        <xsl:text>}</xsl:text>
    </xsl:template>

    <xsl:template match="Consumptions">
        <xsl:text>"Consumptions": [</xsl:text>
        <xsl:apply-templates select="consumption"/>
        <xsl:text>]</xsl:text>
    </xsl:template>

    <xsl:template match="consumption">
        <xsl:text>{"ProductFabricationCode": "</xsl:text>
        <xsl:value-of select="Product/@fabricationCode"/>
        <xsl:text>", "RawMaterialCode": "</xsl:text>
        <xsl:value-of select="RawMaterial/@code"/>
        <xsl:text>",</xsl:text>
        <xsl:apply-templates select="Quantity"/>
        <xsl:text>, "depositInternalCode": "</xsl:text>
        <xsl:value-of select="Deposit/@internalCode"/>
        <xsl:text>", "movementDate": "</xsl:text>
        <xsl:value-of select="movementDate"/>
        <xsl:text>"}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="Chargebacks">
        <xsl:text>"Chargebacks": [</xsl:text>
        <xsl:apply-templates select="chargeback"/>
        <xsl:text>]</xsl:text>
    </xsl:template>

    <xsl:template match="chargeback">
        <xsl:text>{"ProductFabricationCode": "</xsl:text>
        <xsl:value-of select="Product/@fabricationCode"/>
        <xsl:text>", "RawMaterialCode": "</xsl:text>
        <xsl:value-of select="RawMaterial/@code"/>
        <xsl:text>",</xsl:text>
        <xsl:apply-templates select="Quantity"/>
        <xsl:text>, "depositInternalCode": "</xsl:text>
        <xsl:value-of select="Deposit/@internalCode"/>
        <xsl:text>"}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="Productions">
        <xsl:text>"Productions": [</xsl:text>
        <xsl:apply-templates select="production"/>
        <xsl:text>]</xsl:text>
    </xsl:template>

    <xsl:template match="production">
        <xsl:text>{"ProductFabricationCode": "</xsl:text>
        <xsl:value-of select="Product/@fabricationCode"/>
        <xsl:text>",</xsl:text>
        <xsl:apply-templates select="Quantity"/>
        <xsl:text>, "allotment": "</xsl:text>
        <xsl:value-of select="allotment"/>
        <xsl:text>"}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionDeliverys">
        <xsl:text>"ProductionDeliverys": [</xsl:text>
        <xsl:apply-templates select="productionDelivery"/>
        <xsl:text>]</xsl:text>
    </xsl:template>

    <xsl:template match="productionDelivery">
        <xsl:text>{"ProductFabricationCode": "</xsl:text>
        <xsl:value-of select="Product/@fabricationCode"/>
        <xsl:text>",</xsl:text>
        <xsl:apply-templates select="Quantity"/>
        <xsl:text>, "depositInternalCode": "</xsl:text>
        <xsl:value-of select="Deposit/@internalCode"/>
        <xsl:text>", "allotment": "</xsl:text>
        <xsl:value-of select="allotment"/>
        <xsl:text>"}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="MachineTimes">
        <xsl:text>"MachineTimes": [</xsl:text>
        <xsl:apply-templates select="machineTime"/>
        <xsl:text>]</xsl:text>
    </xsl:template>

    <xsl:template match="machineTime">
        <xsl:text>{"MachineSerialNumber": "</xsl:text>
        <xsl:value-of select="Machine/@serialNumber"/>
        <xsl:text>", "date": "</xsl:text>
        <xsl:value-of select="date"/>
        <xsl:text>", "newStatus": "</xsl:text>
        <xsl:value-of select="newStatus"/>
        <xsl:text>"}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="Requests">
        <xsl:text>"Requests": [</xsl:text>
        <xsl:apply-templates select="request"/>
        <xsl:text>]</xsl:text>
    </xsl:template>

    <xsl:template match="request">
        <xsl:text>"</xsl:text>
        <xsl:value-of select="code"/>
        <xsl:text>"</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>
</xsl:stylesheet>
