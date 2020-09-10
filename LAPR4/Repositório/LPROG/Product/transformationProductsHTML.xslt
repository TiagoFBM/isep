<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/fabrica/producao/Products">
        <html>
            <head>
                <style>
                    table {
                    font-family: arial, sans-serif;
                    border-collapse: collapse;
                    width: 100%;
                    }

                    td, th {
                    border: 1px solid #dddddd;
                    text-align: left;
                    padding: 8px;
                    }

                    tr:nth-child(even) {
                    background-color: #dddddd;
                    }
                </style>
            </head>
            <body>
                <h1 style="font-family:Helvetica;font-size:32px;text-align:center" align="center">Products List</h1>
                <table align="center" cellpadding="2" cellspacing="2" border="0" >
                    <tr bgcolor="#ffffff">

                    <th style="font-family:Helvetica;font-size:16px;text-align:center">Fabrication Code</th>
                        <th style="font-family:Helvetica;font-size:16px;text-align:center">Comercial Code</th>
                        <th style="font-family:Helvetica;font-size:16px;text-align:center">Brief Description</th>
                        <th style="font-family:Helvetica;font-size:16px;text-align:center">Full Description</th>
                        <th style="font-family:Helvetica;font-size:16px;text-align:center">Product Category</th>
                        <th style="font-family:Helvetica;font-size:16px;text-align:center">Unity</th>
                    </tr>
                    <xsl:apply-templates select="Product">
                        <xsl:sort select="@fabricationCode"/>
                    </xsl:apply-templates>
                </table>
            <h2/>
                <table align="center" border="1">
                    <tr bgcolor="#ffffff">
                    <th style="text-align:center">Product Fabrication Code</th>
                        <th style="text-align:center">Quantity</th>
                        <th style="text-align:center">Unity</th>
                        <th style="text-align:center">Raw Material Code</th>
                        <th style="text-align:center">Product Code</th>
                    </tr>
                    <xsl:apply-templates select="Product/ProductSheet"/>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="Product">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="@fabricationCode"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="comercialCode"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="briefDescription"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="fullDescription"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="productCategory"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="unit"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="Product/ProductSheet">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="../@fabricationCode"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="Quantity/valor"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="Quantity/unity"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="ProductionSheetLine/RawMaterial/@code"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="ProductionSheetLine/Product/@fabricationCode"/>
            </td>

        </tr>
    </xsl:template>

</xsl:stylesheet>
