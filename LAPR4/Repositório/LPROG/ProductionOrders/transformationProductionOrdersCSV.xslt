<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="separator" select="';'"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/">
        <xsl:apply-templates select="/fabrica/producao/ProductionOrders"/>
    </xsl:template>

    <xsl:template match="/fabrica/producao/ProductionOrders">
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