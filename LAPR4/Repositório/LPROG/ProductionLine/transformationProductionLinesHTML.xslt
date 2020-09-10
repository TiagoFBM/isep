<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/fabrica/chaoDeFabrica/ProductionLines">
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
                <h1 style="font-family:Helvetica;font-size:32px;text-align:center" align="center">Production Lines List</h1>
                <table align="center" cellpadding="2" cellspacing="2" border="0" >
                    <tr bgcolor="#ffffff">

                    <th style="font-family:Helvetica;font-size:16px;text-align:center">Internal Code</th>
                        <th style="font-family:Helvetica;font-size:16px;text-align:center">Description</th>
                    </tr>
                    <xsl:apply-templates select="ProductionLine">
                        <xsl:sort select="@internalCode"/>
                    </xsl:apply-templates>
                </table>
                <h2/>
                <table align="center" border="1">
                    <tr bgcolor="#ffffff">
                        <th style="text-align:center">Production Line Internal Code</th>
                        <th style="text-align:center">Serial Number</th>
                        <th style="text-align:center">Identification Number</th>
                        <th style="text-align:center">Manufacturer</th>
                        <th style="text-align:center">Model</th>
                        <th style="text-align:center">Operation</th>
                        <th style="text-align:center">Installation Date</th>
                    </tr>
                    <xsl:apply-templates select="ProductionLine/Machines/Machine"/>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="ProductionLine">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="@internalCode"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="desc"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="ProductionLine/Machines/Machine">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="../../@internalCode"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="@serialNumber"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="identificationNumber"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="manufacturer"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="model"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="operation"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="installationDate"/>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
