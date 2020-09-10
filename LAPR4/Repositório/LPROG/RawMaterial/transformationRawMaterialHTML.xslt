<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/fabrica/producao/RawMaterials">
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
                <h1 style="font-family:Helvetica;font-size:32px;text-align:center" align="center">Raw Material List</h1>
                <table align="center" cellpadding="2" cellspacing="2" border="0" >
                    <tr bgcolor="#ffffff">

                    <th style="font-family:Helvetica;font-size:16px;text-align:center">Raw Material Code</th>
                        <th style="font-family:Helvetica;font-size:16px;text-align:center">Description</th>
                        <th style="font-family:Helvetica;font-size:16px;text-align:center">Raw Material Category ID</th>
                        <th style="font-family:Helvetica;font-size:16px;text-align:center">Raw Material Category Description</th>
                    </tr>
                    <xsl:apply-templates select="RawMaterial">
                        <xsl:sort select="@code"/>
                    </xsl:apply-templates>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="/fabrica/producao/RawMaterialCategorys">
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
                <h1 style="font-family:Helvetica;font-size:32px;text-align:center" align="center">Raw Material Category List</h1>
                <table align="center" cellpadding="2" cellspacing="2" border="0" >
                    <tr bgcolor="#ffffff">

                        <th style="font-family:Helvetica;font-size:16px;text-align:center">Raw Material Category Code</th>
                        <th style="font-family:Helvetica;font-size:16px;text-align:center">Raw Material Category Description</th>
                    </tr>
                    <xsl:apply-templates select="RawMaterialCategory">
                        <xsl:sort select="@categoryID"/>
                    </xsl:apply-templates>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="RawMaterial">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="@code"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="description"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="RawMaterialCategory/@categoryID"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="RawMaterialCategory/briefDescription"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="RawMaterialCategory">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="@categoryID"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="briefDescription"/>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
