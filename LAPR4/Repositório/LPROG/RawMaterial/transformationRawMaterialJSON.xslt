<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/fabrica/producao">
        <xsl:text>{</xsl:text>
        <xsl:apply-templates select="RawMaterials"/>
        <xsl:apply-templates select="RawMaterialCategorys"/>
        <xsl:text>}</xsl:text>
    </xsl:template>

    <xsl:template match="RawMaterials">
        <xsl:text>"RawMaterials": {</xsl:text>
        <xsl:text>"RawMaterial": [</xsl:text>
        <xsl:apply-templates select="RawMaterial"/>
        <xsl:text>]</xsl:text>
        <xsl:text>},</xsl:text>
    </xsl:template>

    <xsl:template match="RawMaterial">
        <xsl:text>{ "code": "</xsl:text>
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
        <xsl:text>}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="RawMaterialCategorys">
        <xsl:text>"RawMaterialCategorys": {</xsl:text>
        <xsl:text>"RawMaterialCategory": [</xsl:text>
        <xsl:apply-templates select="RawMaterialCategory"/>
        <xsl:text>]</xsl:text>
        <xsl:text>}</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

    <xsl:template match="RawMaterialCategory">
        <xsl:text>{ "categoryID": "</xsl:text>
        <xsl:value-of select="@categoryID"/>
        <xsl:text>", "briefDescription": "</xsl:text>
        <xsl:value-of select="briefDescription"/>
        <xsl:text>" }</xsl:text>
        <xsl:if test="not (position() = last())">,</xsl:if>
    </xsl:template>

</xsl:stylesheet>
