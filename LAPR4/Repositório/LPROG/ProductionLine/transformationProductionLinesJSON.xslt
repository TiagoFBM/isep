<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/fabrica/chaoDeFabrica">
        <xsl:text>{ "chaoDeFabrica": {</xsl:text>
        <xsl:apply-templates select="ProductionLines"/>
        <xsl:if test="not (position() = last())">,</xsl:if>
        <xsl:text>} }</xsl:text>
    </xsl:template>

    <xsl:template match="/fabrica/chaoDeFabrica/ProductionLines">
        <xsl:text> "ProductionLines": {</xsl:text>
        <xsl:text> "ProductionLine": [</xsl:text>
        <xsl:apply-templates select="ProductionLine"/>
        <xsl:if test="not (position() = last())">,</xsl:if>
        <xsl:text>] }</xsl:text>
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

</xsl:stylesheet>
