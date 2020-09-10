<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="newline" select="'&#10;'"/>

    <xsl:template match="/fabrica/chaoDeFabrica/Deposits">
        <xsl:text>{ "Deposit": [</xsl:text>
        <xsl:apply-templates select="Deposit"/>
        <xsl:text>] }</xsl:text>
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

</xsl:stylesheet>
