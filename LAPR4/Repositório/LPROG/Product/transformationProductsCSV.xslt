<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="separator" select="';'"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/">
        <xsl:apply-templates select="/fabrica/producao/Products"/>
    </xsl:template>

    <xsl:template match="/fabrica/producao/Products">
        <xsl:text>Fabrication Code;Comercial Code;Brief Description;Full Description;Product Category;Unity</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="Product"/>
        <xsl:value-of select="$newline"/>
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

</xsl:stylesheet>
