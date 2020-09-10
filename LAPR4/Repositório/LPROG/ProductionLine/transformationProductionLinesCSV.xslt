<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="separator" select="';'"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/">
        <xsl:apply-templates select="/fabrica/chaoDeFabrica/ProductionLines"/>
    </xsl:template>

    <xsl:template match="/fabrica/chaoDeFabrica/ProductionLines">
        <xsl:text>Internal Code;Description</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="ProductionLine"/>
        <xsl:value-of select="$newline"/>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="ProductionLine/Machines/Machine"/>
    </xsl:template>

    <xsl:template match="ProductionLine">
        <xsl:value-of select="@internalCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="desc"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="ProductionLine/Machines/Machine">
        <xsl:text>Production Line Internal Code;Serial Number;Identification Number;Manufacturer;Model; Operation; Installation Date</xsl:text>
        <xsl:value-of select="$newline"/>
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

</xsl:stylesheet>
