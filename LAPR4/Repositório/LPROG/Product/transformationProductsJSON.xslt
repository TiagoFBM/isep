<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/fabrica/producao/Products">
        <xsl:text>{ "Product": [</xsl:text>
        <xsl:apply-templates select="Product"/>
        <xsl:text>] }</xsl:text>
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
        <xsl:apply-templates select="ProductionSheetLine"/>
        <xsl:text>}</xsl:text>
    </xsl:template>

    <xsl:template match="ProductionSheetLine">
        <xsl:text>"ProductionSheetLine": [</xsl:text>
        <xsl:apply-templates select="RawMaterial"/>
        <xsl:apply-templates select="ProductionSheetLine/SimpleProduct"/>
         <xsl:text>]</xsl:text>
    </xsl:template>

    <xsl:template match="ProductionSheetLine/RawMaterial">
        <xsl:text>{ "RawMaterial": {</xsl:text>
        <xsl:text>"code": "</xsl:text>
        <xsl:value-of select="code"/>
        <xsl:text>", "description": "</xsl:text>
        <xsl:value-of select="description"/>
        <xsl:text>", "RawMaterialCategory": {</xsl:text>
        <xsl:text>"categoryID": "</xsl:text>
        <xsl:value-of select="RawMaterialCategory/categoryID"/>
        <xsl:text>", "briefDescription": "</xsl:text>
        <xsl:value-of select="RawMaterialCategory/briefDescription"/>
        <xsl:text>" },</xsl:text>
        <xsl:text>"DataSheet": {</xsl:text>
        <xsl:text>"Referency": "</xsl:text>
        <xsl:value-of select="DataSheet/Referency"/>
        <xsl:text>" }</xsl:text>
        <xsl:text>},</xsl:text>
        <xsl:text>"Quantity": {</xsl:text>
        <xsl:text>"valor": "</xsl:text>
        <xsl:value-of select="../Quantity/valor"/>
        <xsl:text>", "unit": "</xsl:text>
        <xsl:value-of select="../Quantity/unity"/>
        <xsl:text>" }}</xsl:text>
    </xsl:template>

    <xsl:template match="SimpleProduct">
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

</xsl:stylesheet>
