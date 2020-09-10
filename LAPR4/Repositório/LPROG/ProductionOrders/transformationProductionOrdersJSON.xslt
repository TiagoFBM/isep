<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/fabrica/producao/ProductionOrders">
        <xsl:text>{ "Product": [</xsl:text>
        <xsl:apply-templates select="ProductionOrder"/>
        <xsl:text>] }</xsl:text>
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
        <xsl:text>", "Quantity": "</xsl:text>
        <xsl:apply-templates select="Quantity"/>
        <xsl:text>", "unit": "</xsl:text>
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
        <xsl:text>", "Quantity": "</xsl:text>
        <xsl:apply-templates select="Quantity"/>
        <xsl:text>", "depositInternalCode": "</xsl:text>
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
        <xsl:text>", "Quantity": "</xsl:text>
        <xsl:apply-templates select="Quantity"/>
        <xsl:text>", "depositInternalCode": "</xsl:text>
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
        <xsl:text>", "Quantity": "</xsl:text>
        <xsl:apply-templates select="Quantity"/>
        <xsl:text>", "allotment": "</xsl:text>
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
        <xsl:text>", "Quantity": "</xsl:text>
        <xsl:apply-templates select="Quantity"/>
        <xsl:text>", "depositInternalCode": "</xsl:text>
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

    <xsl:template match="Quantity">
        <xsl:value-of select="valor"/>
        <xsl:text> (</xsl:text>
        <xsl:value-of select="unity"/>
        <xsl:text>)</xsl:text>
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