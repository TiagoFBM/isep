<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="separator" select="';'"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/">
        <xsl:apply-templates select="/fabrica/producao/RawMaterials"/>
        <xsl:apply-templates select="/fabrica/producao/RawMaterialCategorys"/>
    </xsl:template>

    <xsl:template match="/fabrica/producao/RawMaterials">
        <xsl:text>Raw Material Code;Description;Raw Material Category ID;Raw Material Category Description;</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="RawMaterial"/>
        <xsl:value-of select="$newline"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="/fabrica/producao/RawMaterialCategorys">
        <xsl:text>Raw Material Category Code;Raw Material Category Description;</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="RawMaterialCategory"/>
        <xsl:value-of select="$newline"/>
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

    <xsl:template match="RawMaterialCategory">
        <xsl:value-of select="@categoryID"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="briefDescription"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

</xsl:stylesheet>
