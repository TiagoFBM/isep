<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="separator" select="';'"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/">
        <xsl:apply-templates select="/fabrica/chaoDeFabrica/Deposits"/>
    </xsl:template>

    <xsl:template match="/fabrica/chaoDeFabrica/Deposits">
        <xsl:text>Internal Code;Description</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:apply-templates select="Deposit"/>
        <xsl:value-of select="$newline"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="Deposit">
        <xsl:value-of select="@internalCode"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="desc"/>
        <xsl:value-of select="$separator"/>
        <xsl:value-of select="$newline"/>
    </xsl:template>


</xsl:stylesheet>
